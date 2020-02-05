package com.udacity.course3.reviews.entity;

import javax.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Integer id;

    @Column(name = "review_text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Review() {}

    public Review(Integer id) {
        this.id = id;
    }

    public Review(String text, Product product) {
        this.text = text;
        this.product = product;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
