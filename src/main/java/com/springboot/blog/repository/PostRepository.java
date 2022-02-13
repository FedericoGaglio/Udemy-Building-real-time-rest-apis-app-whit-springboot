package com.springboot.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.springboot.blog.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	
}




/*
 * 
 * JpaRepository  -> i parametri che vanno passati all'interno sono:
 * 1) entity name -> in questo caso quindi Post
 * 2) id type     -> quindi in questo caso un long (indicato comunque nella classe che rappresenta l'entita)
 * 
 */
