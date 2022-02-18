package com.springboot.blog.service;

import org.springframework.stereotype.Service;

import com.springboot.blog.payload.CommentDto;

@Service
public interface CommentService {
	
    CommentDto createComment(long postId, CommentDto commentDto);


}
