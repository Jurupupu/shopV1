package com.shop.dao.productDao;

import com.shop.entity.product.ProductCategory;
import com.shop.entity.product.ProductTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ProductTagDAO {
    @Autowired
    EntityManager em;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Transactional
    public void addProductTag(int product_id, int tag_id){
        Map<String, Object> param = new HashMap<>();
        param.put("product_id", product_id);
        param.put("tag_id", tag_id);
        jdbcTemplate.update("insert into shop.product_tag (product_id, tag_id)" +
                " values (:product_id, :tag_id)", param);
    }

    @Transactional(readOnly = true)
    public ProductTag findProductTag(Integer product_id, Integer tag_id){
        try {
            return em.createQuery("from "+ProductTag.class.getName()
                            +" pt where pt.product.id = :product_id and pt.tag.id = :tag_id"
                    , ProductTag.class)
                    .setParameter("product_id", product_id)
                    .setParameter("tag_id",tag_id)
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
    public void deleteProductTag(Integer product_id, Integer tag_id){
        try {
            em.createQuery("delete from "+ProductTag.class.getName()
                    +" pt where pt.product.id = :product_id and pt.tag.id = :tag_id")
                    .setParameter("product_id", product_id)
                    .setParameter("tag_id", tag_id)
                    .executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
