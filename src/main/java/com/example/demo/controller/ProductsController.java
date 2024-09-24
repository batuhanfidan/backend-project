package com.example.demo.controller;

import com.example.demo.dto.ProductsResponse;
import com.example.demo.entity.Products;
import com.example.demo.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private final ProductsService productsService;

    @Autowired
    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public ResponseEntity<List<Products>> getAllProducts() {
        return ResponseEntity.ok(productsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Products> getProductById(@PathVariable Long id) {
        Products product = productsService.findById(id);
        if (product == null) {
            throw new RuntimeException("product is not found with id:" + product);
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ProductsResponse> createProduct(@RequestBody Products products, @PathVariable long id) {
        ProductsResponse response = productsService.save(products, id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductsResponse> updateProduct(@PathVariable Long id, @RequestBody Products products) {
        ProductsResponse response = productsService.update(id, products);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Products product = productsService.findById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        productsService.delete(product);
        return ResponseEntity.noContent().build();
    }
}
