package com.project.blog_app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog_app.payloads.ApiResopnse;
import com.project.blog_app.payloads.CategoryDto;
import com.project.blog_app.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories/")
public class CategoryController {
    

    @Autowired
    private CategoryService categoryService;

    //create

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto ){
        CategoryDto createdCat = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(createdCat,HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto ,@PathVariable Integer categoryId){
       
        CategoryDto updatedCat = this.categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(updatedCat,HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResopnse> deleteCategory(@PathVariable Integer categoryId){
        this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new ApiResopnse("Category Deleted Successfully....", true),HttpStatus.OK);
    }

    //get
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId){
        CategoryDto cat = this.categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(cat,HttpStatus.OK);
    }

    //getAll
    @GetMapping("/")
    public List<CategoryDto> getAllCategory(){
        return this.categoryService.getAllCategories();
    }
}
