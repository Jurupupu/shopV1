package com.shop.dao.productDao;

import com.shop.entity.product.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;


@Repository
public class CategoryDAO {
    @Autowired
    EntityManager em;

    @Transactional(readOnly = true)
    public List<Category> findAllCategories(){
        try {
            return em.createQuery("from " + Category.class.getName(), Category.class).getResultList();
        }
        catch (NoResultException e){
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
