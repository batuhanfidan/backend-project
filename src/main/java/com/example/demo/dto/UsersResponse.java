package com.example.demo.dto;

import com.example.demo.entity.Products;

import java.util.List;

public record UsersResponse(long id, String userName, String email, String password, String fullName, String phoneNumber, String adress, long roleId, List<Products> favoriteProducts, List<Products> basket) {
}
