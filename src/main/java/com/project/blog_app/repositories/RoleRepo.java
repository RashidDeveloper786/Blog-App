package com.project.blog_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blog_app.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {
    
    
    
}
