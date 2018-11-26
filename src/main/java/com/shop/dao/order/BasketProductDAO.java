package com.shop.dao.order;

import com.shop.entity.order.BasketProduct;
import com.shop.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class BasketProductDAO {
    private static final String SQL_ADD_PRODUCT_IN_BASKET =
            "insert into shop.basket_product (user_id, product_id, quantity) values (:user_id, :product_id, :quantity);";

    private static final String SQL_FIND_ALL_PRODUCTS_IN_BASKET_BY_ID =
            "from " + BasketProduct.class.getName() + " bp where bp.user.id = :user_id";

    private static final String SQL_DELETE_BY_ID =
            "delete from " + BasketProduct.class.getName() + " bp where bp.id = :id";

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    EntityManager em;

    @Transactional
    public void addProductInBasket(Long user_id, Integer product_id, Integer quantity){
        Map<String, Object> param = new HashMap<>();
        param.put("user_id", user_id);
        param.put("product_id", product_id);
        param.put("quantity", quantity);
        try {
            jdbcTemplate.update(SQL_ADD_PRODUCT_IN_BASKET, param);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional(readOnly = true)
    public List<BasketProduct> findAllProductsInBusketByUserId(Long user_id){
        try {
            return em.createQuery(SQL_FIND_ALL_PRODUCTS_IN_BASKET_BY_ID, BasketProduct.class)
                    .setParameter("user_id", user_id)
                    .getResultList();
        }
        catch (NoResultException e){
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public void deleteById(Long id){
        try {
            em.createQuery(SQL_DELETE_BY_ID)
                    .setParameter("id", id)
                    .executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
