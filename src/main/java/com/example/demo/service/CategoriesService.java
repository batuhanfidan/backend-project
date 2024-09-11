package com.example.demo.service;

import com.example.demo.dto.CategoriesResponse;
import com.example.demo.entity.Categories;

import java.util.List;

public interface CategoriesService {

    List<Categories> findAll();

    Categories findById(Long id);

    Categories save(Categories categories);

    CategoriesResponse update(Long id, Categories categories);

    void delete(Categories categories);

}
