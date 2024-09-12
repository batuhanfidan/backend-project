package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="users", schema = "public")
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="user_name")
    private String userName;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="full_name")
    private String fullName;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column(name="adress")
    private String adress;

    @Column(name="role_id")
    private long roleId;


    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "products", schema = "public",
            joinColumns = @JoinColumn(name = "user_id" ),
            inverseJoinColumns = @JoinColumn(name="products_id"))
    private List<Products> favoriteProducts ;



    public void addFavoriteProduct(Products product) {
        if (favoriteProducts == null) {
            favoriteProducts = new ArrayList<>();
        }
        favoriteProducts.add(product);
    }


    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "products", schema = "public",
            joinColumns = @JoinColumn(name = "user_id" ),
            inverseJoinColumns = @JoinColumn(name="products_id"))
    private List<Products> basket ;

    public void addToBasket(Products product) {
        if (basket == null) {
            basket = new ArrayList<>();
        }
        basket.add(product);
    }

    private List<Role> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return "";
    }
}
