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

        Optional<Categories> existingCategoryOpt = categoriesRepository.findById(id);
        if (existingCategoryOpt.isEmpty()) {
            throw new RuntimeException("Category not found");
        }

        Categories existingCategory = existingCategoryOpt.get();
        existingCategory.setName(categories.getName());
        existingCategory.setProducts(categories.getProducts());

        Categories updatedCategory = categoriesRepository.save(existingCategory);

        return new CategoriesResponse(
                updatedCategory.getId(),
                updatedCategory.getName(),
                updatedCategory.getProducts()
        );
    }

    @Override
    public void delete(Categories categories) {
        categoriesRepository.delete(categories);
    }
}
