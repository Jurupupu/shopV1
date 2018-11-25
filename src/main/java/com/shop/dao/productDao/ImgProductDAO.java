package com.shop.dao.productDao;

import com.shop.entity.product.ImgProduct;
import com.shop.entity.product.Product;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ImgProductDAO {
    private static final Log LOG = LogFactory.getLog(ImgProductDAO.class);

    private static final String SQL_SAVE_IMG =
            "insert into img_product (name, product_id) values(:name, :id)";
    private static final String SQL_FIND_IMG_BY_ID =
            "from " + ImgProduct.class.getName() + " ip where ip.product = :product";
    @Autowired
    EntityManager em;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;


    @Transactional
    public void saveProductImg(String name, Integer id){
        Map<String, Object> param = new HashMap<>();
        param.put("name", name);
        param.put("id", id);
        LOG.info(
                jdbcTemplate.update(SQL_SAVE_IMG, param));
    }

    @Transactional(readOnly = true)
    public List<ImgProduct> findImgById(Product product){
        return em.createQuery(SQL_FIND_IMG_BY_ID, ImgProduct.class)
                .setParameter("product", product)
                .getResultList();
    }

    @Transactional
    public void deleteImgById(Long id){
        Map<String, Object> param= new HashMap<>();
        param.put("id", id);
        jdbcTemplate.update("delete from shop.img_product where id = :id", param);
    }
}
