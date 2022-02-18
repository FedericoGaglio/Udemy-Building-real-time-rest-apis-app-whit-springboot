package com.springboot.blog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogAPIException;
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



	@Override
	public CommentDto getCommentById(Long postId, Long commentId) {
		
		Post post = this.postRepository.findById(postId).get();
		Comment comment = this.commentRepository.findById(commentId).get();
		
		if(comment.getPost().getId() != post.getId() ) {
			throw new BlogAPIException(HttpStatus.BAD_REQUEST, "comment non ricollegabile ad alcun post");
		}
		
		return mapToDTO(comment);
	}
	
	
	@Override
    public CommentDto updateComment(Long postId, long commentId, CommentDto commentRequest) {

		Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if(comment.getPost().getId() != post.getId()){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }

        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDTO(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {

    	Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if(comment.getPost().getId() != post.getId()){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }

        commentRepository.delete(comment);
    }


}
