package com.project.blog_app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blog_app.entities.User;

public interface UserRepo extends JpaRepository<User,Integer>{
    
    Optional<User> findByEmail(String email);
}
