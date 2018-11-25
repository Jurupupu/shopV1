package com.shop.service;

import com.shop.dao.userDao.UserDAO;
import com.shop.dao.userDao.UserRoleDAO;
import com.shop.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    UserDAO userDAO;

    @Autowired
    UserRoleDAO userRoleDAO;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDAO.findUserByName(s);
        if (user == null){
            throw new UsernameNotFoundException("User with this username is not found!");
        }
        List<String> roles = userRoleDAO.getRolesByUserId(user.getId());

        List<GrantedAuthority> authorities = new ArrayList<>();

        for (String str : roles){
            authorities.add(new SimpleGrantedAuthority(str));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), authorities);
    }
}
