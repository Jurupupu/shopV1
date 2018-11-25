package com.shop.controller;


import com.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller("/product")
public class ProductInfoController {
    @Autowired
    ProductService pS;

    @GetMapping("product/{id}")
    public String productInfoPage(@PathVariable Integer id, Model model){
        model.addAttribute("product", pS.findProductById(id));
        return "product/productInfoPage";
    }
}
