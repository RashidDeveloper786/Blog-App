package com.project.blog_app.services;


import java.util.List;

import com.project.blog_app.payloads.CommentDto;

public interface CommentService {
    
    CommentDto createComment(CommentDto commentDto , Integer postId);

    List<CommentDto> getAllComment();

    void deleteComment(Integer commentId);
}
