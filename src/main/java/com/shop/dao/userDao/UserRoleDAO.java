package com.shop.dao.userDao;

import com.shop.entity.user.User;
import com.shop.entity.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRoleDAO {
    private static final String SQL_JDBC_ADD_ROLE_IN_USR =
            "insert into user_role (user_id, role_id) values (:user_id ,:role_id);";

    private static final String SQL_GET_ROLES_BY_USER_ID =
            "select ur.role.name from " + UserRole.class.getName() + " ur where ur.user.id= :id";

    @Autowired
    EntityManager em;
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Transactional
    public void addRoleUser(User user, Long role_id){
        Map<String, Object> param = new HashMap<>();
        param.put("user_id", user.getId());
        param.put("role_id", role_id);
        jdbcTemplate.update(SQL_JDBC_ADD_ROLE_IN_USR, param);
    }

    @Transactional(readOnly = true)
    public List<String> getRolesByUserId(Long id){
        return em.createQuery(SQL_GET_ROLES_BY_USER_ID, String.class)
                .setParameter("id", id)
                .getResultList();
    }
}
