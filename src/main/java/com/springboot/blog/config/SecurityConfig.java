package com.springboot.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springboot.blog.security.CustomUserDetailsService;
import com.springboot.blog.security.JwtAuthenticationEntryPoint;
import com.springboot.blog.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	//aggiunto attributo dopo aver creato la classe JWTAuthResponse
	/*questa classe aveva un metodo, ovvero commence, che veniva chiamato ogni volta che viene generata 
	 * un'eccezione a causa di un utente non autenticato che tenta di accedere a una 
	 * risorsa che richiede un'autenticazione*/
	@Autowired 
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	
	@Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return  new JwtAuthenticationFilter();
    }
	
	
	@Bean
	PasswordEncoder passwordEncoder () {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure( HttpSecurity http) throws Exception {

		http	
		.csrf().disable() // disattivazione  della protezione csrf
		
		.exceptionHandling()
        .authenticationEntryPoint(authenticationEntryPoint)
        .and()
        
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        
		.authorizeRequests() // permette di ottenere un accesso ristrettto
		.antMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
		.antMatchers("/api/v1/auth/**").permitAll()
		.antMatchers("/v3/api-docs/**").permitAll()
        .antMatchers("/swagger-ui/**").permitAll()
        .antMatchers("/swagger-resources/**").permitAll()
        .antMatchers("/swagger-ui.html").permitAll()
        .antMatchers("/webjars/**").permitAll()
		.anyRequest() // mappa qualsiasi richiesta
		.authenticated(); // specifica che gli url "sono concessi" a qualsiasi utente AUTENTICATO
		//.and() //TOLTA DOPO IMPLEMENTAZIOMNE DTO -> JWTAUTHRESPONSE
		//.httpBasic(); // configurazione http basica //TOLTA DOPO IMPLEMENTAZIOMNE DTO -> JWTAUTHRESPONSE
		
        http.
        addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);


	}

	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
	
	
	/**
	 * Parte relativa al modulo di LOGIN/SIGNING
	 * 
	 * L' authenticationManager serve semplicemente a processare le richieste che vengono effettutate.
	 */
	@Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


//	@Override
//	@Bean
//	protected UserDetailsService userDetailsService() {
//
//		/* siccome abbiamo creato l'utente pippo qui, possiamo andare a commentare/togliere l'utente
//		 * dichiarato nel file application.properties con le medesime caratteristiche*/
//		UserDetails pippo = User.builder()
//				.username("pippo")
//				.password(passwordEncoder().encode("pwd"))
//				.roles("USER")
//				.build();
//		
//		
//		UserDetails admin = User.builder()
//				.username("admin")
//				.password(passwordEncoder().encode("admin"))
//				.roles("ADMIN")
//				.build();
//
//		return new InMemoryUserDetailsManager(pippo, admin);


		/*
		 * 
		 * UserDetails ------->>>>> Fornisce informazioni sull'utente di base.
		 * Le implementazioni non vengono utilizzate direttamente da Spring
		 * Security per motivi di sicurezza. Memorizzano semplicemente le informazioni 
		 * sull'utente che vengono successivamente incapsulate negli oggetti di autenticazione.
		 * Ciò consente di archiviare in una posizione comoda le informazioni dell'utente non correlate 
		 * alla sicurezza (come indirizzi e-mail, numeri di telefono, ecc.).
		 * */
	//}
}
