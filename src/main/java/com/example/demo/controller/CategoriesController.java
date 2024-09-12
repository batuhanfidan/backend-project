package com.example.demo.controller;

import com.example.demo.dto.CategoriesResponse;
import com.example.demo.entity.Categories;
import com.example.demo.service.CategoriesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    private CategoriesService categoriesService;

    @Autowired
    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @GetMapping("/categories")
    public String getCategories() {
        return "List of categories"; // ya da HTML response
    }


    @GetMapping("/{id}")
    public ResponseEntity<Categories> getCategoryById(@PathVariable Long id) {
        Categories category = categoriesService.findById(id);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<Categories> createCategory(@RequestBody Categories category) {
        Categories savedCategory = categoriesService.save(category);
        return ResponseEntity.ok(savedCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriesResponse> updateCategory(@PathVariable Long id, @RequestBody Categories category) {
        CategoriesResponse updatedCategory = categoriesService.update(id, category);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        Categories category = categoriesService.findById(id);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }
        categoriesService.delete(category);
        return ResponseEntity.noContent().build();
    }
}
