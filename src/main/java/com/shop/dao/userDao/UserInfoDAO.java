package com.shop.dao.userDao;

import com.shop.entity.user.User;
import com.shop.entity.user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

@Repository
@Transactional
public class UserInfoDAO {
    private static final String SQL_ADD_USER_INFO =
            "insert into shop.user_info (user_id, first_name, last_name, card, pass_card) values (:user_id, :first_name, :last_name, :card, :pass_card);";

    @Autowired
    private EntityManager em;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Transactional
    public void saveUserInfo(UserInfo userInfo, User user){
        Map<String, Object> param = new HashMap<>();
        param.put("user_id", user.getId());
        param.put("first_name", userInfo.getFirst_name());
        param.put("last_name", userInfo.getLast_name());
        param.put("card", userInfo.getCard());
        param.put("pass_card", userInfo.getPass_card());
        jdbcTemplate.update(SQL_ADD_USER_INFO, param);
    }
}
