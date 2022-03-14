package com.springboot.blog.controller;

import java.util.Collection;
import java.util.Collections;

import org.apache.catalina.realm.AuthenticatedUserRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.payload.JWTAuthResponse;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.SignUpDto;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.JWTTokenProvider;

@RestController
@RequestMapping("/api/v1/auth") 
public class AuthController {
	
	//injection (non essendo direttamente creata da me come class ad ex, la dichiaro direttamente
	// e la vado ad iniettare nel contesto tramite  l'autowired)
	@Autowired
	private AuthenticationManager authenticatormanager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JWTTokenProvider tokenProvider;
	
	
	@PostMapping("/signin")
	private ResponseEntity<JWTAuthResponse> authenticationUser(@RequestBody LoginDto loginDto) {
		
		Authentication  auth =  authenticatormanager.authenticate(new UsernamePasswordAuthenticationToken(
											loginDto.getUsernameOrEmail(), 
											loginDto.getPassword())
										  );
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		//prendiamoci il token direttamente dal token provider
		String token = tokenProvider.generateToken(auth);
		
		//return new ResponseEntity<>("L'utente si è segnato correttamente!", HttpStatus.OK);
		
		return ResponseEntity.ok(new JWTAuthResponse(token));		
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
	
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser (@RequestBody SignUpDto signUpDto) {
		
		// controllo se l'utente gia esiste o meno nel db
		
		if(userRepository.existsByUsername(signUpDto.getUsername())) {
			return new ResponseEntity<>("Questo utente risulta essere gia registrato!", HttpStatus.BAD_REQUEST);
		}
		
		// aggiunta controllo sulla mail se esiste nel db
		
		if(userRepository.existsByEmail(signUpDto.getEmail())) {
			return new ResponseEntity<>("Questa email risulta essere gia registrata!", HttpStatus.BAD_REQUEST);
		}
		
		// se allora questo utente non esiste ne vado a creare uno nuovo che inserisvo nel db
		
		User user = new User();
		user.setName(signUpDto.getName());
		user.setUsername(signUpDto.getUsername());
		user.setEmail(signUpDto.getEmail());
		user.setPassword( passwordEncoder.encode(signUpDto.getPassword()));	
		
		Role roles = roleRepository.findByName("ROLE_ADMIN").get();
		
		user.setRoles(Collections.singleton(roles));
		
		//metodo crud offert da jpa repositpory
		userRepository.save(user);
		
		return new ResponseEntity<>("Utente registrato correttamente", HttpStatus.OK);
		
	}
	
	

}
