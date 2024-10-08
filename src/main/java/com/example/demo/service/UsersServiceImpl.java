package com.example.demo.service;

import com.example.demo.Repository.UsersRepository;
import com.example.demo.dto.UsersResponse;
import com.example.demo.entity.Users;
import com.example.demo.exceptions.ApiException;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService{

    private final PasswordEncoder passwordEncoder;

    private final UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository,PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    @Override
    public Users findById(Long id) {
        return usersRepository.findById(id).orElseThrow(() -> new ApiException("User is not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public UsersResponse save(Users users) {

        if (users.getEmail() == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }

        Optional<Users> existingUser = usersRepository.findById(users.getId());
        if (existingUser.isPresent()) {
            throw new ApiException("This user is already registered", HttpStatus.ALREADY_REPORTED);
        }

        String encodedPassword = passwordEncoder.encode(users.getPassword());

        users.setPassword(encodedPassword);

        Users savedUser = usersRepository.save(users);
        return new UsersResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getUsername(),
                savedUser.getFullName(),
                savedUser.getAdress(),
                savedUser.getPhoneNumber(),
                savedUser.getFavoriteProducts(),
                savedUser.getBasket()
        );
    }

    @Override
    public UsersResponse update(Long id, Users users) {

        Optional<Users> existingUserOpt = usersRepository.findById(id);

        if (existingUserOpt.isEmpty()) {
            throw new ApiException("User not found", HttpStatus.NOT_FOUND);
        }

        Users existingUser = existingUserOpt.get();

        existingUser.setEmail(users.getEmail());
        existingUser.setPassword(users.getPassword());
        existingUser.setUserName(users.getUsername());
        existingUser.setFullName(users.getFullName());
        existingUser.setAdress(users.getAdress());
        existingUser.setPhoneNumber(users.getPhoneNumber());
        existingUser.setFavoriteProducts(users.getFavoriteProducts());
        existingUser.setBasket(users.getBasket());

        Users updatedUser = usersRepository.save(existingUser);

        return new UsersResponse(
                updatedUser.getId(),
                updatedUser.getEmail(),
                updatedUser.getUsername(),
                updatedUser.getFullName(),
                updatedUser.getAdress(),
                updatedUser.getPhoneNumber(),
                updatedUser.getFavoriteProducts(),
                updatedUser.getBasket()
        );
    }

    @Override
    public void delete(Users users) {
        usersRepository.delete(users);
    }
}
