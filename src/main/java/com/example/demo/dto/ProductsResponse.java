package com.example.demo.dto;

import com.example.demo.entity.Categories;

import java.util.List;

public record ProductsResponse(long id, String name, int price, long stock, List<Categories> categories) {
}
