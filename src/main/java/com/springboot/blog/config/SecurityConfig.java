package com.springboot.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure( HttpSecurity http) throws Exception {
		
		http	
				.csrf().disable() // disattivazione  della protezione csrf
				.authorizeRequests() // permette di ottenere un accesso ristrettto
				.anyRequest() // mappa qualsiasi richiesta
				.authenticated() // specifica che gli url "sono concessi" a qualsiasi utente AUTENTICATO
				.and()
				.httpBasic(); // configurazione http basica

	}

}
