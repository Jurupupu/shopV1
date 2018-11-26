package com.shop.service;

import com.shop.dao.productDao.ImgProductDAO;
import com.shop.dao.productDao.ProductDAO;
import com.shop.entity.product.ImgProduct;
import com.shop.entity.product.Product;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private static final Log log = LogFactory.getLog(ProductService.class);

    @Autowired
    ProductDAO productDAO;

    @Autowired
    ImgProductDAO imgProductDAO;

//================================================================================
//// METHODS PRODUCT_DAO
////=====================================================

    public List<Product> findAllProducts(){
        List<Product> products = productDAO.findAllProducts();
        log.info("Find all products");
        return products;
    }

    public Product findProductById(Integer id){
        Product product = productDAO.findProductById(id);
        log.info("Find product by ID: " + id);
        return product;
    }

    public Product findProductByName(String productName){
        Product product = productDAO.findProductByName(productName);
        log.info("Find product by product name: " + productName);
        return product;
    }

    public boolean saveProduct(Product product){
        try {
            productDAO.saveProduct(product);
            log.info("Save product ( " + product.toString() + " )");
            return true;
        }catch (Exception e){
            log.error("Error save product: " + product.toString());
            return false;
        }

    }

    public List<Product> findAllProductsLimit4(){
        log.info("Find 8 last products");
        return productDAO.findAllProductsLimit4();
    }

    public boolean deleteProductById(Integer id){
        try {
            productDAO.deleteProductsById(id);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

//================================================================================
// METHODS IMG_PRODUCT_DAO
//=====================================================

    public boolean saveProductImg(String name, Integer id){
        try {
            imgProductDAO.saveProductImg(name, id);
            log.info("Save img: " + name + ". In product with id: " + id);
            return true;
        }catch (Exception e){
            log.error("Error save img with name: " + name);
            return false;
        }
    }

    public boolean deleteImgById(Long id){
        try {
            imgProductDAO.deleteImgById(id);
            log.info("Successful delete img with id: " + id);
            return true;
        }catch (Exception e){
            log.error("Error delete img with id: " + id);
            return false;
        }
    }

    public List<ImgProduct> findImgById(Product product){
        List<ImgProduct> imgProducts = imgProductDAO.findImgById(product);
        log.info("Find img by id" + product.getId());
        return imgProducts;
    }
}
