package com.springboot.blog.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 											
@AllArgsConstructor 							
@NoArgsConstructor 								
@Entity 										
@Table( 										
		name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
)
public class Post {
	
	@Id 										
	@GeneratedValue (							
		strategy = GenerationType.IDENTITY
	)							
	private long id;
	
	@Column(name="title" , nullable=false)		
	private String title;
	
	@Column(name="description" , nullable=false)
	private String description;
	
	@Column(name="content" , nullable=false)
	private String content;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

}
 