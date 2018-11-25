package com.shop.entity.product;

import com.shop.entity.order.OrderProduct;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 128, unique = true)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private Float price;

    @Column(nullable = false)
    private Integer discount;

    @Column(nullable = false)
    private Integer quantity;

    @OneToMany
    private List<Category> category;

    @OneToMany
    private List<Tag> tag;

    @OneToMany(mappedBy = "product")
    private List<ImgProduct> imgProducts;

    @OneToMany
    private List<OrderProduct> orderProduct;

    public Product() {
    }

    public Product(String name, String description, Float price, Integer discount, Integer quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<Category> getCategories() {
        return category;
    }

    public void setCategories(List<Category> categories) {
        this.category = categories;
    }

    public List<Tag> getTags() {
        return tag;
    }

    public void setTags(List<Tag> tags) {
        this.tag = tags;
    }

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

    public List<Tag> getTag() {
        return tag;
    }

    public void setTag(List<Tag> tag) {
        this.tag = tag;
    }

    public List<ImgProduct> getImgProducts() {
        return imgProducts;
    }

    public void setImgProducts(List<ImgProduct> imgProducts) {
        this.imgProducts = imgProducts;
    }

    public List<OrderProduct> getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(List<OrderProduct> orderProduct) {
        this.orderProduct = orderProduct;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", price: " + price + ", quantity: " + quantity;
    }
}
