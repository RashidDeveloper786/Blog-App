package com.project.blog_app.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blog_app.entities.Category;
import com.project.blog_app.exceptions.ResourceNotFoundException;
import com.project.blog_app.payloads.CategoryDto;
import com.project.blog_app.repositories.CategoryRepo;
import com.project.blog_app.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category cat = this.modelMapper.map(categoryDto, Category.class);
        Category addedCat = this.categoryRepo.save(cat);
        return this.modelMapper.map(addedCat, CategoryDto.class);

    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto , Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Category Id", categoryId));
    
        cat.setCategoryTitle(categoryDto.getCategoryTitle());
        cat.setCategoryDesc(categoryDto.getCategoryDesc());

        Category updatedCat =  this.categoryRepo.save(cat);

        return  this.modelMapper.map(updatedCat,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
    
        this.categoryRepo.delete(cat);
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));
        CategoryDto categoryDto = this.modelMapper.map(cat, CategoryDto.class);

        return categoryDto;
    
    }

    @Override
    public List<CategoryDto> getAllCategories() {
      
        List<Category> categories =  this.categoryRepo.findAll();
        List<CategoryDto> categoryDtos = categories.stream().map((cat)-> 
            this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
        
        return categoryDtos;
    }
    
}
