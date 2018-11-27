package com.shop.dao.productDao;

import com.shop.entity.product.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class TagDAO {
    @Autowired
    EntityManager em;



    @Transactional(readOnly = true)
    public List<Tag> findAllTags(){
        try {
            return em.createQuery("from "+Tag.class.getName(), Tag.class).getResultList();
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
