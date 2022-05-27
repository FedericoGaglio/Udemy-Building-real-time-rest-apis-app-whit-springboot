package com.springboot.blog.service;
import org.springframework.stereotype.Service;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

@Service
public interface PostService {
	
	PostDto createPost(PostDto postDto);
	
	PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
	
	PostDto getByID(long id);
	
	PostDto updatePost(PostDto postDto, long id);
	
	void deletePostByID(long id);
}
