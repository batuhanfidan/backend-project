package com.example.demo.service;

import com.example.demo.Repository.CategoriesRepository;
import com.example.demo.Repository.UsersRepository;
import com.example.demo.dto.CategoriesResponse;
import com.example.demo.entity.Categories;

import java.util.List;

public class CategoriesServiceImpl implements CategoriesService {


    private CategoriesRepository categoriesRepository;


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
        return null;
    }

    @Override
    public void delete(Categories categories) {
        categoriesRepository.delete(categories);
    }
}
