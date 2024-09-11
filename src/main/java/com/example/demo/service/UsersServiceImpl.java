package com.example.demo.service;

import com.example.demo.Repository.UsersRepository;
import com.example.demo.entity.Users;

import java.util.List;

public class UsersServiceImpl implements UsersService{

    private UsersRepository usersRepository;

    @Override
    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    @Override
    public Users findById(Long id) {
        return usersRepository.findById(id).orElse(null);
    }

    @Override
    public Users save(Users users) {
        return usersRepository.save(users);
    }

    @Override
    public UsersService update(Long id, Users users) {
        return null;
    }

    @Override
    public void delete(Users users) {
        usersRepository.delete(users);
    }
}
