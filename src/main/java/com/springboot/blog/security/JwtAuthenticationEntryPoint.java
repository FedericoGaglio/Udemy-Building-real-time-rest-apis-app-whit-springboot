package com.springboot.blog.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{
	
	//STEP 2 DI UTILIZZO JWT

	/*Questo metodo viene chiamato ogni volta che viene generata un'eccezione a causa di un utente non 
	 * autenticato che tenta di accedere a una risorsa che richiede un'autenticazione*/
	
	@Override //QUESTO METODO SERVE AD INIZIARE UNO SCHEMA DI AUTENTICAZIONE
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
		
	}
	
	//ORA IL prossimo step è andare a modificare  l'application.properties

}
