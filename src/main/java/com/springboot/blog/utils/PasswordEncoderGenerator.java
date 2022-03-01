package com.springboot.blog.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderGenerator {
	
	public static void main(String[] args) {
		
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//System.out.println(passwordEncoder.encode("pwd")); //$2a$10$1dOVM08xWkbOm38kRqbzMeXwYIV6hOZgVKBaEJiY6lfFBAUhq.bJe
		System.out.println(passwordEncoder.encode("admin")); //$2a$10$eQ2JWCjyhh7uaMrZrBFNfOiudiO3PUzQEmijA2iQpLg.q5V47t0ry
		
	}
	
	/**
	 * questa classe viene semplicemente utilizzata per poter andare a fare l'encoding delle due password 
	 * che si sono gia usate in preceenza, ovvero:
	 * 
	 * utente PIPPO -> pwd PWD
	 * 
	 * utente ADMIN -> pwd ADMIN
	 * 
	 * 
	 * andando quindi a lanciare il mail e passando la password che si vuole andare ad encodare, questa
	 * viene riproposta in sys output. Poi l'ho inserita nel db.
	 */

}
