package com.springboot.blog.payload;

//import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PostDto {
	
	private Long id;
	
	@NotEmpty
	@Size (min=2, message="Il post deve avere almeno due caratteri!")
	private String title;
	
	@NotEmpty
	@Size (min=10, message="La descrizione deve avere almeno 10 caratteri!")
	private String description;
	
	@NotEmpty
	private String content;
	
}
 