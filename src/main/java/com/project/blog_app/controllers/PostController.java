package com.project.blog_app.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.blog_app.config.AppConstants;
import com.project.blog_app.payloads.ApiResopnse;
import com.project.blog_app.payloads.PostDto;
import com.project.blog_app.payloads.PostResponse;
import com.project.blog_app.services.FileService;
import com.project.blog_app.services.PostService;

import jakarta.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api")
public class PostController {

    @Value("${project.image}")
	private String path;

    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable Integer userId , @PathVariable Integer categoryId){
        
        PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
        
        return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);

    }

    //get all posts uploaded by the particular userId
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getAllPostByUser(@PathVariable Integer userId){

        List<PostDto> postDtos = this.postService.getAllPostByUser(userId);
        return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);

    }
    
    //get all posts with related categoryId
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getAllPostByCategory(@PathVariable Integer categoryId){

        List<PostDto> postDtos = this.postService.getAllPostByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
    }

    //delete post by postId
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResopnse> deletePost(@PathVariable Integer postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<ApiResopnse>(new ApiResopnse("Post Deleted Successfully", true),HttpStatus.OK);
    }

    //get post by postId
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        PostDto postDto= this.postService.getPostById(postId);
        return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
    }

    //get all posts
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
        @RequestParam(value = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber,
        @RequestParam(value = "pageSize" , defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize ,
        @RequestParam(value = "sortBy" , defaultValue = AppConstants.SORT_BY , required = false) String sortBy,
        @RequestParam(value = "sortDir" , defaultValue = AppConstants.SORT_DIR , required = false) String sortDir
        
        ){
        PostResponse postResponse= this.postService.getAllPost(pageNumber , pageSize , sortBy,sortDir);
        return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto ,@PathVariable Integer postId){
        PostDto updatedDto = this.postService.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(updatedDto,HttpStatus.OK);
    }
  
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable ("keywords") String keywords ){

        List<PostDto> results = this.postService.searchPost(keywords);
        return new ResponseEntity<List<PostDto>>(results,HttpStatus.OK);
    }


    //File Controller

    @PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException {

		PostDto postDto = this.postService.getPostById(postId);
		
		String fileName = this.fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);

	}

    @GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable("imageName") String imageName,
            HttpServletResponse response
    ) throws IOException {

        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream())   ;

    }
}
