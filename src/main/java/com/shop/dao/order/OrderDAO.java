package com.shop.dao.order;

import com.shop.entity.order.BasketProduct;
import com.shop.entity.order.Order;
import com.shop.entity.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class OrderDAO {
    @Autowired
    private EntityManager em;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Transactional(readOnly = true)
    public List<Order> findOrdersByUserId(Long id){
        try {
            return em.createQuery("from "+Order.class.getName()+" o where o.user.id = :id", Order.class)
                    .setParameter("id", id)
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

    @Transactional(readOnly = true)
    public List<Order> findAllOrders(){
        return em.createQuery("from " + Order.class.getName(), Order.class)
                .getResultList();
    }

    @Transactional
    public void createOrder(Long user_id){
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("user_id" , user_id);
            //Создание экземпляра заказа
            jdbcTemplate.update("insert into shop.ord (user_id) values (:user_id);", param);

            //Поиск последнего созданного экземпляра
            List<Order> orders = em.createQuery("from " + Order.class.getName()
                    + " o where o.user.id = :user_id order by o.id desc", Order.class)
                    .setParameter("user_id", user_id)
                    .getResultList();
            Order order = orders.get(0);

            //Поиск всех продуктов в корзине юзера
            List<Product> productsInBasket = em.createQuery("select bp.product from " + BasketProduct.class.getName()
                    + " bp where bp.user.id = :user_id", Product.class)
                    .setParameter("user_id", user_id)
                    .getResultList();

            //Напонение заказа продуктами из корзины
            Iterator<Product> iterator = productsInBasket.iterator();
            Product product;
            Float fullPrice = 0F;
            param.put("ord_id", order.getId());
            while (iterator.hasNext()){
                product = iterator.next();
                fullPrice += product.getPrice();
                param.put("product_id", product.getId());
                jdbcTemplate.update("insert into shop.ord_product (ord_id, product_id, quantity)" +
                        " values (:ord_id, :product_id, 1)", param);
            }

            //Добавление общей стоймости в заказ
            param.put("price", fullPrice);
            jdbcTemplate.update("update shop.ord o set o.price = :price where o.id = :ord_id", param);

            //Очищение корзины
            em.createQuery("delete from "+BasketProduct.class.getName()+" bp where bp.user.id = :user_id")
                    .setParameter("user_id", user_id)
                    .executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
