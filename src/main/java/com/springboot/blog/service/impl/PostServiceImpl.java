package com.springboot.blog.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import lombok.AllArgsConstructor;


@Service
public class PostServiceImpl implements PostService{

	private PostRepository postRepository;



	public PostServiceImpl(PostRepository postRepository) {
		//super();
		this.postRepository = postRepository;
	}


	// FOR POST METHOD
	@Override
	public PostDto createPost(PostDto postDto) {

		//CONVERSIONE  da DTO a ENTITY (------->>>>> RIMPIAZZATO DA METODO mapToEntity <<<<<-------)
		/*Post post = new Post();
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());*/	

		//Post newPost = this.postRepository.save(post); //PER SALVARE UNA DETERMINATA ENTITA
		Post newPost = this.postRepository.save(mapToEntity(postDto));

		//CONVERSIONE da Entity a DTO  (------->>>>> RIMPIAZZATO DA METODO mapToDTO <<<<<-------)
		/*PostDto postResponse = new PostDto();
		postResponse.setId(newPost.getId());
		postResponse.setTitle(newPost.getTitle());
		postResponse.setDescription(newPost.getDescription());
		postResponse.setContent(newPost.getContent());*/
		PostDto postdto = mapToDTO(newPost);
		
		return postdto;
	}


	//FOR GET ALL POSTS METHOD
	/*@Override
	public List<PostDto> getAllPosts(int pageNo, int pageSize){

		List<Post> allPosts = this.postRepository.findAll();
		List<PostDto> allDtoPosts= new ArrayList<PostDto>();

		for(Post post: allPosts) {
//			PostDto postResponse = new PostDto();
//			postResponse.setId(post.getId());
//			postResponse.setTitle(post.getTitle());
//			postResponse.setDescription(post.getDescription());
//			postResponse.setContent(post.getContent());
			allDtoPosts.add(mapToDTO(post));
		}


		return allDtoPosts;
	}*/
	
	/*@Override
	public List<PostDto> getAllPosts(int pageNo, int pageSize){
		
		
		// PAGING AND SORTING SUPPORT -->> CREAZIONE DI UN'INSTANZA PAGINABILE
		Pageable pageable = (Pageable) PageRequest.of(pageNo, pageSize);
		

		Page<Post> allPosts = this.postRepository.findAll(pageable); // FIND ALL DELLA CLASSE PAGEABLE
		
		// PER FARE IL GET DEL CONTENUTO DELLA PAGINA
		List<Post> listOfPosts = allPosts.getContent();
		
		
		List<PostDto> allDtoPosts= new ArrayList<PostDto>();

		for(Post post: listOfPosts) {
//			PostDto postResponse = new PostDto();
//			postResponse.setId(post.getId());
//			postResponse.setTitle(post.getTitle());
//			postResponse.setDescription(post.getDescription());
//			postResponse.setContent(post.getContent());
			allDtoPosts.add(mapToDTO(post));
		}


		return allDtoPosts;
	}*/
	
	
	/*ULTERIORE MODIFICA CON CUSTOMIZZAZIONE DEL PAGINE AND SORTING E USO POSTRESPONSE CLASS*/
	@Override
	public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir){
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		
		
		// PAGING AND SORTING SUPPORT -->> CREAZIONE DI UN'INSTANZA PAGINABILE
		//Pageable pageable = (Pageable) PageRequest.of(pageNo, pageSize);
		Pageable pageable = (Pageable) PageRequest.of(pageNo, pageSize, sort);
		

		Page<Post> allPosts = this.postRepository.findAll(pageable); // FIND ALL DELLA CLASSE PAGEABLE
		
		// PER FARE IL GET DEL CONTENUTO DELLA PAGINA
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
	
	
	// FOR GET BY ID
	@Override
	public PostDto getByID(long id) {
		
		
		/*Ho dovuto aggiungere il get finale per convertire l'oggetto OPTIONAL<POST> ritornato dal getById
		 * in un oggetto POST secco*/
		Post postFounded = this.postRepository.findById(id).get();
		
		return mapToDTO(postFounded);
		
	}


	// UPDATE POST 
	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		
		Post postFounded = this.postRepository.findById(id).get();
		
		postFounded.setTitle(postDto.getTitle());
		postFounded.setDescription(postDto.getDescription());
		postFounded.setContent(postDto.getContent());
		
		// LASCIO L'OPERAZIONE SUL DB DI SALVATAGGIO ALL'ENTITA E NON AL DTO
		Post updatePost = this.postRepository.save(postFounded);
		
		return mapToDTO(updatePost);
		
	}
	
	
	// DELETE POST BY ID
	@Override
	public void deletePostByID (long id) {
		
		this.postRepository.deleteById(id);
	}

	/*Siccome nei metodi di create post e getall uno una stessa parte di codice che è quella che risulta essere
	 * commentata, invece che ripetere il codice vado a fare un metodo unico*/

	/*QUELLI CHE SEGUONO SONOP QUINDI DUE METODI AUSILIARIW*/

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


/*
 * 
 * Annotazione SERVICE:
 * serve per offrire delle possibili operazioni impementate
 * 
 * 
 * 
 * 
 */
