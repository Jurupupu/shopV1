package com.shop.dao.userDao;

import com.shop.entity.user.User;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Transactional
public class UserDAO {
    private static final String SQL_FIND_USER_BY_NAME =
            "from " + User.class.getName() + " u where u.username = :username";
    private static final String SQL_FIND_ALL_USERS =
            "from " + User.class.getName() + " u order by u.id desc";

    private static final String SQL_FIND_USER_BY_ID =
            "from " + User.class.getName() + " u where u.id = :id";


    @Autowired
    EntityManager em;

    @Transactional
    public void saveUser(User user){
        try {
            em.persist(user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional(readOnly = true)
    public User findUserByName(String username){
        try {
            return em.createQuery(SQL_FIND_USER_BY_NAME, User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<User> findAllUsers(){
        try {
            return em.createQuery(SQL_FIND_ALL_USERS, User.class).getResultList();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(readOnly = true)
    public User findUserById(Long id){
        try {
            return em.createQuery(SQL_FIND_USER_BY_ID, User.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
