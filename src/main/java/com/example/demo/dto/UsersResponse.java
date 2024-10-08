package com.example.demo.dto;

import com.example.demo.entity.Products;

import java.util.List;

public record UsersResponse(long id, String userName, String email, String fullName, String phoneNumber, String adress, List<Products> favoriteProducts, List<Products> basket) {
}
