package com.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/login")
public class LoginController {

    @GetMapping("/login")
    public String loginPage(Model model){

        return "user/login";
    }
}
