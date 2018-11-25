package com.shop.entity.user;

import javax.persistence.*;

@Entity
@Table(name = "user_info")
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @MapsId
    @OneToOne(mappedBy = "userInfo")
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String first_name;

    @Column
    private String last_name;

    @Column
    private String card;

    @Column
    private String pass_card;

    public UserInfo() {
    }

    public UserInfo(String first_name, String last_name, String card, String pass_card) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.card = card;
        this.pass_card = pass_card;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getPass_card() {
        return pass_card;
    }

    public void setPass_card(String pass_card) {
        this.pass_card = pass_card;
    }
}
