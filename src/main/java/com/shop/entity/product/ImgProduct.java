package com.shop.entity.product;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "img_product")
public class ImgProduct implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;


    public ImgProduct() {
    }

    public Long getId() {
        return id;
    }

    public ImgProduct(String name, byte[] img, Product product)  {
        this.name = name;
        this.product = product;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
