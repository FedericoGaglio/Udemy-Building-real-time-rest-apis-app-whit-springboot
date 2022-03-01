package com.springboot.blog.controller;

import org.apache.catalina.realm.AuthenticatedUserRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.LoginDto;

@RestController
@RequestMapping("/api/auth") 
public class AuthController {
	
	//injection (non essendo direttamente creata da me come class ad ex, la dichiaro direttamente
	// e la vado ad iniettare nel contesto tramite  l'autowired)
	@Autowired
	private AuthenticationManager authenticatormanager;
	
	@PostMapping("/signin")
	private ResponseEntity<String> authenticationUser(@RequestBody LoginDto loginDto) {
		
		Authentication  auth =  authenticatormanager.authenticate(new UsernamePasswordAuthenticationToken(
											loginDto.getUsernameOrEmail(), 
											loginDto.getPassword())
										  );
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		return new ResponseEntity<>("L'utente si è segnato correttamente!", HttpStatus.OK);
		
	}
	
	/**
	 * METODO authenticationUser:
	 * 
	 *  Inizialmente dichiaro un oggetto di tipo ->Authentication (Rappresenta il token per una richiesta
	 *  di autenticazione o per un'entità autenticata una volta che la richiesta è stata elaborata dal 
	 *  metodo AuthenticationManager.authenticate(Authentication). Una volta che la richiesta è 
	 *  stata autenticata, l'Autenticazione verrà solitamente archiviata in un SecurityContext
	 *  thread-local gestito dal SecurityContextHolder dal meccanismo di autenticazione utilizzato. 
	 *  È possibile ottenere un'autenticazione esplicita, senza utilizzare uno dei meccanismi di 
	 *  autenticazione di Spring Security, creando un'istanza di autenticazione e utilizzando il codice:)
	 *  
	 *  
	 *  SecurityContextHolder -> rappresenta il contesto nel quale viene STORATA la mia richiesta di
	 *  autenticazione
	 */
	
	
	
	

}
