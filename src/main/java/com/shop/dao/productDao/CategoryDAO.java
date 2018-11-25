package com.shop.dao.productDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;


@Repository
public class CategoryDAO {
    @Autowired
    EntityManager em;
}
