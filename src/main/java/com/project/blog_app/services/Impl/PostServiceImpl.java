package com.project.blog_app.services.Impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.blog_app.entities.Category;
import com.project.blog_app.entities.Post;
import com.project.blog_app.entities.User;
import com.project.blog_app.exceptions.ResourceNotFoundException;
import com.project.blog_app.payloads.PostDto;
import com.project.blog_app.payloads.PostResponse;
import com.project.blog_app.repositories.CategoryRepo;
import com.project.blog_app.repositories.PostRepo;
import com.project.blog_app.repositories.UserRepo;
import com.project.blog_app.services.PostService;


@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper; 
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private UserRepo userRepo;

    @Override
    public PostDto createPost(PostDto postDto ,Integer userId , Integer categoryId) {

        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user" , "user id" , userId));
        
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category" , "category id" , categoryId));
        
        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);

        Post newPost = this.postRepo.save(post);

        return this.modelMapper.map(newPost, PostDto.class);
     
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post ", "post id", postId));

        Category category = this.categoryRepo.findById(postDto.getCategory().getCategoryId()).get();

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        post.setCategory(category);


        Post updatedPost = this.postRepo.save(post);
        return this.modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id ", postId));
        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber , Integer pageSize , String sortBy , String sortDir) {


        Sort sort = (sortDir.equalsIgnoreCase("abc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNumber ,pageSize ,sort);
        Page<Post> pagePosts = this.postRepo.findAll(p);
        List<Post> posts = pagePosts.getContent();

        List<PostDto> postDto = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDto);
        postResponse.setPageNumber(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setLastPage(pagePosts.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {

        Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id ", postId));
        PostDto postDto= this.modelMapper.map(post, PostDto.class);
        return postDto;
    }

    @Override
    public List<PostDto> getAllPostByCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","category id ",categoryId));
        List<Post> posts = this.postRepo.findByCategory(category);
        
        List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        
        return postDtos;
    }

    @Override
    public List<PostDto> getAllPostByUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "user id ", userId));
        List<Post> posts = this.postRepo.findByUser(user);

        List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> searchPost(String keyword) {

        List<Post> posts = this.postRepo.findByTitleContaining(keyword);
        List<PostDto> postDto = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDto;
    }
    
}
