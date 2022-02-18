package com.springboot.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;


@RestController   					//per indicare che questa classe effettivamente svolge il ruolo di controller
//@RequestMapping("/api/posts")       //per indicare verso quale api andare a mappare le oprazioni offerte dal service dichiarato
public class PostController {
	
	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	//CREATE A POST
	@PostMapping("/api/posts") 		//per indicare che sto andando a mappare un operazione di HTTP.POST
	public ResponseEntity<PostDto> createPost (@RequestBody PostDto postDto){
		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
	}
	
	//REQUEST BODY: contiene il corpo di una richiesta che di fatto è un oggetto da andare a mappare

	
	
	//GET ALL POST CONTROLLER PART
	
	/* QUESTO METOOD è STATO CAMBIATO PER L'AGGIUNTA DEL SUPPORTO DEL PAGING AND SORTING */
	/*@GetMapping("/api/allPosts")
	public List<PostDto> getAllPosts() {
		return postService.getAllPosts();
	}*/
	
	/* METODO CON SUPPORTO DEL S & P*/
	/*@GetMapping("/api/allPosts")
	public List<PostDto> getAllPosts( @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			                          @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
		return postService.getAllPosts(pageNo, pageSize);
	}*/
	
	@GetMapping("/api/allPosts")
	public PostResponse getAllPosts( @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
			                         @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			                         @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
			                         @RequestParam(value = "sortDir", defaultValue = "id", required = false) String sortDir) {
		return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
	}
	
	// GET POST BY ID
	@GetMapping("api/allPosts/{id}")
	public ResponseEntity<PostDto> getByID(@PathVariable(name = "id") long id){
		return new ResponseEntity<PostDto>(postService.getByID(id),HttpStatus.OK);
	}
	
	
	
	// UPDATE POST BY ID
	@PutMapping("api/allPosts/{id}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable(name = "id") long id) {
		return new ResponseEntity<PostDto>(postService.updatePost(postDto, id),HttpStatus.OK);
	}
	
	
	// DELETE POST BY ID
	@DeleteMapping("api/allPosts/{id}")
	public ResponseEntity<String> deletePostByID(@PathVariable(name = "id") long id) {
		postService.deletePostByID(id);
		return new ResponseEntity<String>("Il post è stato cancellato con successo", HttpStatus.OK);
	}
	
}




/*
 * 
 * Il fatto che ci sia @RequestMapping("/api/posts") in cima alla classe vuol dire che tutti i metodi andrebbero
 * gerso questo end point, se voglio andare con un singolo metodo, ad esempio il getAllPosts, su un altro end point
 * allora vado a sovrascrivere quello di default @RequestMapping("/api/posts"), con un get mapping annotation
 * sopra il singolo specifico metodo di cui voglio cambiare l'api su cui applicarlo
 * 
 * 
 * */
