package com.project.blog_app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blog_app.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment,Integer>{
    
}
