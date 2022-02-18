package com.springboot.blog.service;
import org.springframework.stereotype.Service;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;

@Service
public interface PostService {
	
	
	// TUTTE QUESTE FUNZIONI APPLICATIVE VENGONO IMPLEMENTATE  NELLA CLASSE 'POST-SERVICE-IMPL'

	// PER FARE LA POST
	PostDto createPost(PostDto postDto);
	
	// PER FARE IL GET-ALL
	//List<PostDto> getAllPosts(); (AGGIORNATO ALLA LINEA SOTTO CON AGGIUNTA PARAMETRI DEL PAGE AND SORTING)
	PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
	
	// PER FARE IL GET-BY-ID
	PostDto getByID(long id);
	
	// PER FARE UPDATE DEL POST BY ID
	PostDto updatePost(PostDto postDto, long id);
	
	// PER FARE LA DELETE DELLA RISORSA BY ID (dovendo non tornare nulla ma dovendo solo eliminare sarà un void)
	void deletePostByID(long id);
}
