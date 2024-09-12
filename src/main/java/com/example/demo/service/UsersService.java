package com.example.demo.service;

import com.example.demo.dto.UsersResponse;
import com.example.demo.entity.Categories;
import com.example.demo.entity.Users;

import java.util.List;

public interface UsersService {

    List<Users> findAll();

    Users findById(Long id);

    UsersResponse save(Users users);

    UsersResponse update(Long id, Users users);

    void delete(Users users);

}
