package com.springboot.blog.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CommentDto {
	
	private long id;
	
	@NotEmpty( message = "Il NOME non può essere nullo oppure vuoto!")
	private String name;
	
	@NotEmpty( message = "La EMAIL non può essere nullo oppure vuoto!")
	@Email
	private String email;
	
	@NotEmpty
	@Size( min = 10, message = "Il body deve avere almeno una lunghezza maggiore  a 10 caratteri!")
	private String body;

}
