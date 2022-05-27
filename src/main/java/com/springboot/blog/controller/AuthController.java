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
		
		String token = tokenProvider.generateToken(auth);
		
		return ResponseEntity.ok(new JWTAuthResponse(token));		
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser (@RequestBody SignUpDto signUpDto) {
		
		if(userRepository.existsByUsername(signUpDto.getUsername())) {
			return new ResponseEntity<>("Questo utente risulta essere gia registrato!", HttpStatus.BAD_REQUEST);
		}
		
		if(userRepository.existsByEmail(signUpDto.getEmail())) {
			return new ResponseEntity<>("Questa email risulta essere gia registrata!", HttpStatus.BAD_REQUEST);
		}
		
		User user = new User();
		user.setName(signUpDto.getName());
		user.setUsername(signUpDto.getUsername());
		user.setEmail(signUpDto.getEmail());
		user.setPassword( passwordEncoder.encode(signUpDto.getPassword()));	
		
		Role roles = roleRepository.findByName("ROLE_ADMIN").get();
		
		user.setRoles(Collections.singleton(roles));
		
		userRepository.save(user);
		
		return new ResponseEntity<>("Utente registrato correttamente", HttpStatus.OK);
		
	}
	
	

}
