package com.shop.controller;

import com.shop.dao.userDao.UserDAO;
import com.shop.dao.userDao.UserInfoDAO;
import com.shop.dao.userDao.UserRoleDAO;
import com.shop.entity.user.User;
import com.shop.entity.user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("/regist")
public class RegistrationController {
    @Autowired
    UserDAO userDAO;

    @Autowired
    UserRoleDAO userRoleDAO;

    @Autowired
    UserInfoDAO userInfoDAO;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @GetMapping("/registration")
    public String registrationPage(Model model){

        return "user/registration";
    }

    @PostMapping("/registration_user")
    public String registrationUser(Model model,
                                   @RequestParam String username,
                                   @RequestParam String password,
                                   @RequestParam String password2,
                                   @RequestParam String firstName,
                                   @RequestParam String lastName,
                                   @RequestParam String card,
                                   @RequestParam String cardPass){
        //Password valid
        if (password.equals(password2)){
            if (password.length() > 5){
                UserInfo userInfo = new UserInfo(firstName,lastName,card,cardPass);
                User user = new User(username, encoder.encode(password));
                userDAO.saveUser(user);
                user = userDAO.findUserByName(username);
                userRoleDAO.addRoleUser(user, 1L);
                userInfoDAO.saveUserInfo(userInfo, user);

            }else{
                model.addAttribute("passError", "Password must be more than 5 characters");
                return registrationPage(model);
            }
        }else {
            model.addAttribute("passError", "Password is incorrect");
            return registrationPage(model);
        }
        return "redirect:/login";
    }
}
