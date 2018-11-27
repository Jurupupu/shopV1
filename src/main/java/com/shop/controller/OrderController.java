package com.shop.controller;

import com.shop.dao.order.BasketProductDAO;
import com.shop.dao.order.OrderDAO;
import com.shop.dao.userDao.UserDAO;
import com.shop.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller("/order")
public class OrderController {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private BasketProductDAO basketProductDAO;

    @GetMapping("/basket")
    public String basketPage(Model model, Principal principal){
        model.addAttribute("productsInBasket",
                basketProductDAO.findAllProductsInBusketByUserId(userDAO.findUserByName(principal.getName()).getId()));
        return "order/basketPage";
    }

    @GetMapping("/orders")
    public String ordersPage(Model model , Principal principal){
        model.addAttribute("orders", orderDAO.findOrdersByUserId(userDAO.findUserByName(principal.getName()).getId()));
        return "order/ordersPage";
    }

    @PostMapping("/basket/add_product_in_basket")
    public String addProductInBasket(Model model, Principal principal,
                                     @RequestParam Integer id,
                                     @RequestParam Integer quantity){
        if (principal != null){
            User user = userDAO.findUserByName(principal.getName());
            basketProductDAO.addProductInBasket(user.getId(), id, quantity);
        }else System.err.println("principal is null!");
        return "redirect:/product/" + id;
    }

    @PostMapping("/basket/delete_product")
    public String deleteProductInBasket(Model model, Principal principal,
                                        @RequestParam Long id){
        basketProductDAO.deleteById(id);
        return basketPage(model, principal);
    }

    @PostMapping("/basket/create_order")
    public String createOrder(Model model, Principal principal){
        orderDAO.createOrder(userDAO.findUserByName(principal.getName()).getId());
        return ordersPage(model, principal);
    }
}
