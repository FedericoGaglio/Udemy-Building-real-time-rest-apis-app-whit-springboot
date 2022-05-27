package com.springboot.blog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService{

	private PostRepository postRepository;

	public PostServiceImpl(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	@Override
	public PostDto createPost(PostDto postDto) {

		Post newPost = this.postRepository.save(mapToEntity(postDto));

		PostDto postdto = mapToDTO(newPost);
		
		return postdto;
	}


	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir){
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		
		Pageable pageable = (Pageable) PageRequest.of(pageNo, pageSize, sort);
		
		Page<Post> allPosts = this.postRepository.findAll(pageable); 
		
		List<Post> listOfPosts = allPosts.getContent();
		
		List<PostDto> allDtoPosts= new ArrayList<PostDto>();

		for(Post post: listOfPosts) {
			allDtoPosts.add(mapToDTO(post));
		}
		
		PostResponse postReaponse =  new PostResponse();
		postReaponse.setContent(allDtoPosts);
		postReaponse.setPageNo(allPosts.getNumber());
		postReaponse.setPageSize(allPosts.getSize());
		postReaponse.setTotalElements(allPosts.getTotalElements());
		postReaponse.setTotalPages(allPosts.getTotalPages());
		postReaponse.setLast(allPosts.isLast());;


		return postReaponse;
	}
	
	@Override
	public PostDto getByID(long id) {
		
		Post postFounded = this.postRepository.findById(id).get();
		
		return mapToDTO(postFounded);
		
	}

	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		
		Post postFounded = this.postRepository.findById(id).get();
		
		postFounded.setTitle(postDto.getTitle());
		postFounded.setDescription(postDto.getDescription());
		postFounded.setContent(postDto.getContent());
		
		Post updatePost = this.postRepository.save(postFounded);
		
		return mapToDTO(updatePost);
		
	}
	
	@Override
	public void deletePostByID (long id) {
		
		this.postRepository.deleteById(id);
	}

	private Post mapToEntity(PostDto postDto) {
		
		Post post = new Post();
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());

		return post;
	}

	private PostDto mapToDTO(Post post) {

		PostDto postDto = new PostDto();
		postDto.setId(post.getId());
		postDto.setTitle(post.getTitle());
		postDto.setDescription(post.getDescription());
		postDto.setContent(post.getContent());
		
		return postDto;
	}

}
