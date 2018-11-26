package com.shop.controller;

import com.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/main")
public class MainController {
    @Autowired
    ProductService pS;


    @GetMapping("/")
    public String mainPage(Model model){
        model.addAttribute("products", pS.findAllProductsLimit4());
        return "main/mainPage";
    }
}
