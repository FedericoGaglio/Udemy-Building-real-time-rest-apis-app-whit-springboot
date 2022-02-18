package com.springboot.blog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	private CommentRepository commentRepository;
	private PostRepository postRepository;

	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
	}
	


	@Override
	public CommentDto createComment(long postId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);

        Post post = postRepository.findById(postId).get();
        
        comment.setPost(post);

        Comment newComment =  commentRepository.save(comment);

        return mapToDTO(newComment);
    }


	/*QUELLI CHE SEGUONO SONOP QUINDI DUE METODI AUSILIARIW*/

	
	private CommentDto mapToDTO(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        commentDto.setBody(comment.getBody());
        return  commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return  comment;
    }



	@Override
	public List<CommentDto> getCommentsByPostId(long postId) {
		
		List<Comment> comments = this.commentRepository.findByPostId(postId);
		List<CommentDto> cdt = new ArrayList<CommentDto>();
		
		// portiamo la lista di commmenti nella lista di commentDto
		for(Comment comment : comments) {
			cdt.add(mapToDTO(comment));
		}
		
		return cdt;
	}

}
