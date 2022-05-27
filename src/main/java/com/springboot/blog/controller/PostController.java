package com.springboot.blog.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostDto2;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;


@RestController   	
public class PostController {
	
	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	//CREATE A POST
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/api/posts") 		
	public ResponseEntity<PostDto> createPost ( @Valid @RequestBody PostDto postDto ){
		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
	} 
	
	@GetMapping("/api/v1/allPosts")
	public PostResponse getAllPosts( @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			                         @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			                         @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
			                         @RequestParam(value = "sortDir", defaultValue = "id", required = false) String sortDir) {
		return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
	}
	
	@GetMapping(value = "api/v1/allPosts/{id}")
	public ResponseEntity<PostDto> getByIDV1(@PathVariable(name = "id") long id){
		return new ResponseEntity<PostDto>(postService.getByID(id),HttpStatus.OK);
	}
	
	@GetMapping(value = "api/allPosts/{id}")
	public ResponseEntity<PostDto2> getByIDV2(@PathVariable(name = "id") long id){
		
		PostDto pdt = postService.getByID(id);
		PostDto2 pdt2 = new PostDto2();
		
		pdt2.setId(pdt.getId());
		pdt2.setTitle(pdt.getTitle());
		pdt2.setDescription(pdt.getDescription());
		pdt2.setContent(pdt.getContent());
		
		List<String> tags = new ArrayList<String>();
		tags.add("Java");
		tags.add("Spring");
		tags.add("Programming");
		
		pdt2.setTags(tags);
		
		return ResponseEntity.ok(pdt2);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("api/v1/allPosts/{id}")
	public ResponseEntity<PostDto> updatePost( @Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id) {
		return new ResponseEntity<PostDto>(postService.updatePost(postDto, id),HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("api/v1/allPosts/{id}")
	public ResponseEntity<String> deletePostByID(@PathVariable(name = "id") long id) {
		postService.deletePostByID(id);
		return new ResponseEntity<String>("Il post è stato cancellato con successo", HttpStatus.OK);
	}
	
}