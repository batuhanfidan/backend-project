package com.example.demo.dto;

import com.example.demo.entity.Categorie;
import com.example.demo.entity.Products;

import java.util.List;

public record CategoriesResponse(long id, Categorie name, List<Products> products) {
}
