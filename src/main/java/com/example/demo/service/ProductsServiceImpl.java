package com.example.demo.service;

import com.example.demo.Repository.CategoriesRepository;
import com.example.demo.Repository.ProductsRepository;
import com.example.demo.dto.ProductsResponse;
import com.example.demo.entity.Categories;
import com.example.demo.entity.Products;
import com.example.demo.exceptions.ApiException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsServiceImpl implements ProductsService {

    private final ProductsRepository productsRepository;
    private final CategoriesService categoriesService;

    @Autowired
    public ProductsServiceImpl(ProductsRepository productsRepository,CategoriesService categoriesService) {
        this.productsRepository = productsRepository;
        this.categoriesService = categoriesService;
    }

    @Override
    public List<Products> findAll() {
        return productsRepository.findAll();
    }

    @Override
    public Products findById(Long id) {
        return productsRepository.findById(id).orElseThrow(() -> new ApiException("Product is not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public ProductsResponse save(Products products, long id) {

        Categories categorie = categoriesService.findById(id);

        products.addCategories(categorie);

        productsRepository.save(products);

        return new ProductsResponse(
                products.getId(),
                products.getName(),
                products.getPrice(),
                products.getStock(),
                products.getCategories()
        );
    }

    @Override
    public ProductsResponse update(Long id, Products products) {

        Optional<Products> existingProductOpt = productsRepository.findById(id);
        if (existingProductOpt.isEmpty()) {
            throw new ApiException("Product not found", HttpStatus.NOT_FOUND);
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
    @Transactional
    public void delete(Products product) {
        product.getCategories().forEach(category -> category.getProducts().remove(product));

        product.getCategories().clear();

        productsRepository.delete(product);
    }
}
