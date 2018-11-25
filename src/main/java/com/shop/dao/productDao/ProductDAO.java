package com.shop.dao.productDao;

import com.shop.entity.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Transactional
@Repository
public class ProductDAO {
    private final static String SQL_FIND_ALL_PRODUCTS =
            "from " + Product.class.getName() + " p order by p.id desc";
    private final static String SQL_FIND_PRODUCT_BY_ID =
            "from " + Product.class.getName() + " p where p.id = :id";
    private final static String SQL_FIND_PRODUCT_BY_NAME =
            "from " + Product.class.getName() + " p where p.name = :name";

    private final static String SQL_DELETE_PRODUCT_BY_ID =
            "delete " + Product.class.getName() + " p where p.id = :id";

    @Autowired
    EntityManager em;

    @Transactional(readOnly = true)
    public List<Product> findAllProducts(){
        try {
            return em.createQuery(SQL_FIND_ALL_PRODUCTS, Product.class)
                    .getResultList();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(readOnly = true)
    public List<Product> findAllProductsLimit8(){
        try {
            return em.createQuery(SQL_FIND_ALL_PRODUCTS, Product.class)
                    .setMaxResults(8)
                    .getResultList();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Transactional(readOnly = true)
    public Product findProductById(Integer id){
        try {
            return em.createQuery(SQL_FIND_PRODUCT_BY_ID, Product.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    @Transactional(readOnly = true)
    public Product findProductByName(String name){
        try {
            return em.createQuery(SQL_FIND_PRODUCT_BY_NAME, Product.class)
                    .setParameter("name", name)
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    @Transactional
    public void saveProduct(Product product){
        em.persist(product);
    }

    @Transactional
    public void deleteProductsById(Integer id){
        em.createQuery(SQL_DELETE_PRODUCT_BY_ID)
                .setParameter("id", id)
                .executeUpdate();
    }
}
