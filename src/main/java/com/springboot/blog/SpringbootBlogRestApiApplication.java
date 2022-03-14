package com.springboot.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.springboot.blog.security.JwtAuthenticationEntryPoint;
import com.springboot.blog.security.JwtAuthenticationFilter;


@SpringBootApplication
public class SpringbootBlogRestApiApplication { //ENTRYPOINT
	
	// injection del model mapper
//	@Bean
//	public ModelMapper modelMapper() {
//		return new ModelMapper();
//	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}
	
	@Bean //senza questa injection restituiva un errore perchè voleva un bean
    public JwtAuthenticationEntryPoint commence(){
        return  new JwtAuthenticationEntryPoint();
    }

}
