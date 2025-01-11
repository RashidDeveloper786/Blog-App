package com.project.blog_app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blog_app.entities.Category;
import com.project.blog_app.entities.Post;
import com.project.blog_app.entities.User;

public interface PostRepo extends JpaRepository<Post,Integer>{
    
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String title);
}
