package com.shop.controller;

import com.shop.dao.productDao.ImgProductDAO;
import com.shop.dao.productDao.ProductDAO;
import com.shop.entity.product.ImgProduct;
import com.shop.entity.product.Product;
import com.shop.service.ProductService;
import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.WebResult;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller("/admin")
public class AdminController {
    @Autowired
    ProductService pS;

    @Autowired
    UserService uS;

    @Value("${upload.path}")
    private String path;

    @GetMapping("/admin")
    public String adminPage(Model model){

        return "admin/adminPage";
    }

    @GetMapping("/admin/product")
    public String adminProductsPage(Model model){
        model.addAttribute("products", pS.findAllProducts());
        return "admin/admin_productPage";
    }

    @GetMapping("/admin/product/{id}")
    public String adminProductPage(Model model,
                                   @PathVariable Integer id){
        Product product = pS.findProductById(id);
        model.addAttribute("productInfo", product);
        model.addAttribute("product_image", pS.findImgById(product));
        return "admin/admin_product_infoPage";
    }

    @GetMapping("/admin/add_product")
    public String adminAddProductPage(Model model){

        return "admin/admin_add_productPage";
    }

    @PostMapping("/add_new_product")
    public String addNewProduct(Model model,
                                @RequestParam(required = false) String name,
                                @RequestParam String description,
                                @RequestParam Float price,
                                @RequestParam Integer discount,
                                @RequestParam Integer quantity){
        if (pS.findProductByName(name) != null){
            model.addAttribute("productExist", "Product with the same name already exists");
            return adminAddProductPage(model);
        }
        if (discount == null || discount == 0) { discount = null;}
        pS.saveProduct(new Product(name,description,price,discount,quantity));
        return adminProductsPage(model);
    }

    @PostMapping("/add_image")
    public String addImage(Model model,
                           @RequestParam("id") Integer id,
                           @RequestParam("file") MultipartFile file){
//        IF IMG > 3
        if (pS.findImgById(pS.findProductById(id)).size() > 3) {
            model.addAttribute("manyImg", "Too many img");
            model.addAttribute("advice", "Delete the picture before uploading a new one");
            return adminProductPage(model, id);
        }
        if (!file.isEmpty()) {

            File uploadDir = new File(path);
            if (!uploadDir.exists()){
                uploadDir.mkdir();
            }

            String uuid = UUID.randomUUID().toString();
            String name = uuid + "_" + file.getOriginalFilename();

            try {
                file.transferTo(new File(path + "/" + name));
                pS.saveProductImg(name, id);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return adminProductPage(model, id);
        }
         return adminAddProductPage(model);
    }

    @PostMapping("/delete_img")
    public String deleteImg(Model model,
                            @RequestParam Integer product_id,
                            @RequestParam Long img_id){
        pS.deleteImgById(img_id);
        return adminProductPage(model, product_id);
    }

    @PostMapping("/admin/delete_product")
    public String deleteProductById(Model model,
                                    @RequestParam Integer id){
        pS.deleteProductById(id);
        return adminProductsPage(model);
    }

//=====================================================================================
//USERS
//==================================================================

    @GetMapping("/admin/users")
    public String adminFindAllUsers(Model model){
        model.addAttribute("users", uS.findAllUsers());
        return "admin/admin_usersPage";
    }

    @GetMapping("/admin/user/{id}")
    public String adminUserInfo(Model model,
                                @PathVariable Long id){
        model.addAttribute(uS.findUserById(id));
        return "admin/admin_user_infoPage";
    }
}
