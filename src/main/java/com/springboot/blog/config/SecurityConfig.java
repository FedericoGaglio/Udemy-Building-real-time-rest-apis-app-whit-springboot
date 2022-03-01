package com.springboot.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Bean
	PasswordEncoder passwordEncoder () {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure( HttpSecurity http) throws Exception {

		http	
		.csrf().disable() // disattivazione  della protezione csrf
		.authorizeRequests() // permette di ottenere un accesso ristrettto
		.antMatchers(HttpMethod.GET, "/api/**").permitAll()
		.anyRequest() // mappa qualsiasi richiesta
		.authenticated() // specifica che gli url "sono concessi" a qualsiasi utente AUTENTICATO
		.and()
		.httpBasic(); // configurazione http basica

	}



	@Override
	@Bean
	protected UserDetailsService userDetailsService() {

		/* siccome abbiamo creato l'utente pippo qui, possiamo andare a commentare/togliere l'utente
		 * dichiarato nel file application.properties con le medesime caratteristiche*/
		UserDetails pippo = User.builder()
				.username("pippo")
				.password(passwordEncoder().encode("pwd"))
				.roles("USER")
				.build();
		
		
		UserDetails admin = User.builder()
				.username("admin")
				.password(passwordEncoder().encode("admin"))
				.roles("ADMIN")
				.build();

		return new InMemoryUserDetailsManager(pippo, admin);


		/*
		 * 
		 * UserDetails ------->>>>> Fornisce informazioni sull'utente di base.
		 * Le implementazioni non vengono utilizzate direttamente da Spring
		 * Security per motivi di sicurezza. Memorizzano semplicemente le informazioni 
		 * sull'utente che vengono successivamente incapsulate negli oggetti di autenticazione.
		 * Ciò consente di archiviare in una posizione comoda le informazioni dell'utente non correlate 
		 * alla sicurezza (come indirizzi e-mail, numeri di telefono, ecc.).
		 * */
	}
}
