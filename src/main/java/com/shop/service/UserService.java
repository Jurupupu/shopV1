package com.shop.service;

import com.shop.dao.userDao.UserDAO;
import com.shop.dao.userDao.UserRoleDAO;
import com.shop.entity.user.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private static final Log log = LogFactory.getLog(UserService.class);

    @Autowired
    UserDAO userDAO;
    @Autowired
    UserRoleDAO userRoleDAO;

    public List<User> findAllUsers(){
        List<User> users = userDAO.findAllUsers();
        log.info("Find all users");
        return users;
    }

    public User findUserById(Long id){
        return userDAO.findUserById(id);
    }
}
