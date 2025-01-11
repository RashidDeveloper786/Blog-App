package com.project.blog_app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog_app.payloads.ApiResopnse;
import com.project.blog_app.payloads.CommentDto;
import com.project.blog_app.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {


    @Autowired
    CommentService commentService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@PathVariable Integer postId){
        CommentDto commentDto2 = this.commentService.createComment(commentDto, postId);
        return new ResponseEntity<CommentDto>(commentDto2,HttpStatus.OK);
    }
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResopnse> deleteComment(@PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResopnse>(new ApiResopnse("Comment Deleted Successfully",true),HttpStatus.OK);
    }

    @GetMapping("/comments")
    public ResponseEntity<List<CommentDto>> getAllComment(){
        List<CommentDto> commentDtos = this.commentService.getAllComment();
        return new ResponseEntity<List<CommentDto>>(commentDtos,HttpStatus.OK);
    }
}
