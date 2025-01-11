package com.project.blog_app.services;

import java.util.List;

import com.project.blog_app.payloads.PostDto;
import com.project.blog_app.payloads.PostResponse;

public interface PostService {

    //create
    PostDto createPost(PostDto postDto , Integer userId , Integer categoryId);

    //update
    PostDto updatePost(PostDto postDto , Integer postId);

    //delete
    void deletePost(Integer postId);

    //getAllPost
    PostResponse getAllPost(Integer pageNumber , Integer pageSize , String sortBy , String sortDir);

    //getPostById
    PostDto getPostById(Integer postId);

    //getAllPostByCategory
    List<PostDto> getAllPostByCategory(Integer categoryId);

    //getAllPostByUser
    List<PostDto> getAllPostByUser(Integer userId);

    //search Post
    List<PostDto> searchPost(String keyword);
}
