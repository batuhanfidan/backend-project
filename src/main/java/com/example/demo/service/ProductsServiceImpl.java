package com.example.demo.service;

import com.example.demo.Repository.ProductsRepository;
import com.example.demo.dto.ProductsResponse;
import com.example.demo.entity.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsServiceImpl implements ProductsService {

    private final ProductsRepository productsRepository;

    @Autowired
    public ProductsServiceImpl(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Override
    public List<Products> findAll() {
        return productsRepository.findAll();
    }

    @Override
    public Products findById(Long id) {
        return productsRepository.findById(id).orElse(null);
    }

    @Override
    public ProductsResponse save(Products products) {

        Products savedProduct = productsRepository.save(products);

        return new ProductsResponse(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getPrice(),
                savedProduct.getStock(),
                savedProduct.getCategories()
        );
    }

    @Override
    public ProductsResponse update(Long id, Products products) {

        Optional<Products> existingProductOpt = productsRepository.findById(id);
        if (existingProductOpt.isEmpty()) {
            throw new RuntimeException("Product not found");
        }

        Products existingProduct = existingProductOpt.get();
        existingProduct.setName(products.getName());
        existingProduct.setPrice(products.getPrice());
        existingProduct.setStock(products.getStock());
        existingProduct.setCategories(products.getCategories());

        Products updatedProduct = productsRepository.save(existingProduct);

        return new ProductsResponse(
                updatedProduct.getId(),
                updatedProduct.getName(),
                updatedProduct.getPrice(),
                updatedProduct.getStock(),
                updatedProduct.getCategories()
        );
    }

    @Override
    public void delete(Products products) {
        productsRepository.delete(products);
    }
}
