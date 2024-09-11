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
@Table(name="products", schema = "public")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

    @Column(name="price")
    private int price;

    @Column(name="stock")
    private long stock;


    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "categories_products", schema = "public",
            joinColumns = @JoinColumn(name = "products_id" ),
            inverseJoinColumns = @JoinColumn(name="categories_id"))

    private List<Categories> categories;

    public void addMovie(Categories category) {
        if (categories == null){}
            categories = new ArrayList<>();
        categories.add(category);
    }
}
