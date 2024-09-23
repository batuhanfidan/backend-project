package com.example.demo.service;

import com.example.demo.Repository.CategoriesRepository;
import com.example.demo.Repository.UsersRepository;
import com.example.demo.dto.CategoriesResponse;
import com.example.demo.entity.Categories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    private CategoriesRepository categoriesRepository;

    @Autowired
    public CategoriesServiceImpl(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    @Override
    public List<Categories> findAll() {
        return categoriesRepository.findAll();
    }

    @Override
    public Categories findById(Long id) {
        return categoriesRepository.findById(id).orElse(null);
    }

    @Override
    public Categories save(Categories categories) {
        return categoriesRepository.save(categories);
    }

    @Override
    public CategoriesResponse update(Long id, Categories categories) {
        Categories existingCategory = categoriesRepository.findById(id).orElse(null);
        if (existingCategory == null) {
            return null; // Or handle this situation differently
        }
        // Update the existing category's fields
        existingCategory.setName(categories.getName());
        existingCategory.setProducts(categories.getProducts());

        // Save the updated category
        Categories updatedCategory = categoriesRepository.save(existingCategory);

        // Return a response DTO
        return new CategoriesResponse(updatedCategory.getId(), updatedCategory.getName(), updatedCategory.getProducts());
    }

    @Override
    public void delete(Categories categories) {
        categoriesRepository.delete(categories);
    }
}
