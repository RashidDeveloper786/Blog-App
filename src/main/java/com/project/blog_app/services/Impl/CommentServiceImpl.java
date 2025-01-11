package com.project.blog_app.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blog_app.entities.Comment;
import com.project.blog_app.entities.Post;
import com.project.blog_app.exceptions.ResourceNotFoundException;
import com.project.blog_app.payloads.CommentDto;
import com.project.blog_app.repositories.CommentRepo;
import com.project.blog_app.repositories.PostRepo;
import com.project.blog_app.services.CommentService;


@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    PostRepo postRepo;

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id ", postId));

		Comment comment = this.modelMapper.map(commentDto, Comment.class);

		comment.setPost(post);

		Comment savedComment = this.commentRepo.save(comment);

		return this.modelMapper.map(savedComment, CommentDto.class);
	}

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment =  this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment", "comment id", commentId));
        this.commentRepo.delete(comment);
        
    }

    @Override
    public List<CommentDto> getAllComment() {

        List<Comment> comments = this.commentRepo.findAll();
       List<CommentDto> commentDtos = comments.stream().map((comment)-> 
            this.modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList()); 
        return commentDtos;
      }

    
    
}
