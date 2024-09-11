package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="categoires", schema = "public")
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(value = EnumType.STRING)
    private Categorie name;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "categories_products", schema = "public",
            joinColumns = @JoinColumn(name = "categories_id" ),
            inverseJoinColumns = @JoinColumn(name="products_id"))

    private List<Products> products;

    public void addProduct(Products product) {
        if (products == null) {
            products = new ArrayList<>();
        }
        products.add(product);
    }

}
