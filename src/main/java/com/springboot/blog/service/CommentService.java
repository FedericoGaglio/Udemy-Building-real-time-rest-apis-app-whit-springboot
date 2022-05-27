package com.springboot.blog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.blog.payload.CommentDto;

@Service
public interface CommentService {
	
    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(long postId);
    
    
    CommentDto getCommentById(Long postId, Long commentId);
    
    CommentDto updateComment(Long postId, long commentId, CommentDto commentRequest);

    void deleteComment(Long postId, Long commentId);
    
    

}
