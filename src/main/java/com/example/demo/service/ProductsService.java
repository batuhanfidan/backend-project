package com.example.demo.service;

import com.example.demo.dto.ProductsResponse;
import com.example.demo.entity.Products;

import java.util.List;

public interface ProductsService {

    List<Products> findAll();

    Products findById(Long id);

    ProductsResponse save(Products products);

    ProductsResponse update(Long id, Products products);

    void delete(Products products);

}
