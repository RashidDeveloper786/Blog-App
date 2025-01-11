package com.project.blog_app.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blog_app.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{
    
}
