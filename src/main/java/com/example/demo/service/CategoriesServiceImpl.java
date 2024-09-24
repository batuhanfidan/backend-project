package com.example.demo.service;

import com.example.demo.Repository.CategoriesRepository;
import com.example.demo.Repository.UsersRepository;
import com.example.demo.dto.CategoriesResponse;
import com.example.demo.entity.Categories;
import com.example.demo.exceptions.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesRepository categoriesRepository;

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
        return categoriesRepository.findById(id).orElseThrow(() -> new ApiException("Categories is not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public Categories save(Categories categories) {
        return categoriesRepository.save(categories);
    }

    @Override
    public CategoriesResponse update(Long id, Categories categories) {
        Categories existingCategory = categoriesRepository.findById(id).orElseThrow(() -> new ApiException("Categorie is not exist with given id:" + id, HttpStatus.NOT_FOUND));
        if (existingCategory == null) {
            return null;
        }
        existingCategory.setName(categories.getName());
        existingCategory.setProducts(categories.getProducts());

        Categories updatedCategory = categoriesRepository.save(existingCategory);

        return new CategoriesResponse(updatedCategory.getId(), updatedCategory.getName(), updatedCategory.getProducts());
    }

    @Override
    public void delete(Categories categories) {
        categoriesRepository.delete(categories);
    }
}
