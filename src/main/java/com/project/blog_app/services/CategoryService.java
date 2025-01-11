package com.project.blog_app.services;

import java.util.List;

import com.project.blog_app.payloads.CategoryDto;

public interface CategoryService {
    
    //create
    CategoryDto createCategory(CategoryDto categoryDto);

    // update
    CategoryDto updateCategory(CategoryDto categoryDto , Integer categoryId);


    // delete
    void deleteCategory(Integer categoryId);

    //get
    CategoryDto getCategoryById(Integer categoryId);

    //getAllCategories
    List<CategoryDto> getAllCategories();

}
