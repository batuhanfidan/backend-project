package com.example.demo.Repository;

import com.example.demo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsersRepository extends JpaRepository<Users, Long > {
}
