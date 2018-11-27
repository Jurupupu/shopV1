package com.shop.dao.productDao;

import com.shop.entity.product.Category;
import com.shop.entity.product.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@Repository
@Transactional
public class ProductCategoryDAO {
    @Autowired
    EntityManager em;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Transactional
    public void addProductCategory(int product_id, int category_id){
        Map<String, Object> param = new HashMap<>();
        param.put("product_id", product_id);
        param.put("category_id", category_id);
        jdbcTemplate.update("insert into shop.product_category (product_id, category_id)" +
                " values (:product_id, :category_id)", param);
    }

    @Transactional(readOnly = true)
    public ProductCategory findProductCategory(Integer product_id, Integer category_id){
        try {
            return em.createQuery("from "+ProductCategory.class.getName()
                    +" pc where pc.product.id = :product_id and pc.category.id = :category_id"
                    , ProductCategory.class)
                    .setParameter("product_id", product_id)
                    .setParameter("category_id",category_id)
                    .getSingleResult();
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
    public void deleteProductCategory(Integer product_id, Integer category_id){
        try {
            em.createQuery("delete from "+ProductCategory.class.getName()
                    +" pc where pc.product.id = :product_id and pc.category.id = :category_id")
                    .setParameter("product_id", product_id)
                    .setParameter("category_id", category_id)
                    .executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
