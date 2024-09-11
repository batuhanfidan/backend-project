package com.example.demo.service;

import com.example.demo.Repository.ProductsRepository;
import com.example.demo.dto.ProductsResponse;
import com.example.demo.entity.Products;
import org.springframework.http.HttpStatus;

import java.util.List;

public class ProductsServiceImpl implements ProductsService{

    private ProductsRepository productsRepository;

    @Override
    public List<Products> findAll() {
        return productsRepository.findAll();
    }

    @Override
    public Products findById(Long id) {
        return productsRepository.findById(id).orElse(null);
    }

    @Override
    public Products save(Products products) {
        return productsRepository.save(products);
    }

    @Override
    public ProductsResponse update(Long id, Products products) {
        return null;
    }

    @Override
    public void delete(Products products) {
        productsRepository.delete(products);
    }
}
