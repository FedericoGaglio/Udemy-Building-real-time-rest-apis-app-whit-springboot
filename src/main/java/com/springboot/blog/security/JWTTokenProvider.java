package com.springboot.blog.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.springboot.blog.exception.BlogAPIException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;


@Component
public class JWTTokenProvider {
	
	/* Questi valori fanno riferimento a quelli che sono andato  a settare all'interno dell'application.properties
	 * Per accedere a questi valori utilizzo l'annotazione value andandomeli a pescare tramite $*/
	
	@Value("${app.jwt-secret}")
	private String jwtsecret;
	
	@Value("${app.jwt-expiration-milliseconds}")
	private int jwtExpirationTimeInMS;
	
	//metodo per la generazione di un token
	public String generateToken(Authentication auth) {
		
		String username = auth.getName();
		Date currentDate = new Date();
		Date expireDate = new Date(currentDate.getTime() + jwtExpirationTimeInMS);
		
		
		String token = Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(expireDate)
				.signWith(SignatureAlgorithm.HS512, jwtsecret)
				.compact();
		
		
		return token;
	}
	
	//get username direttamente dal token
	public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtsecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // validate JWT token
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtsecret).parseClaimsJws(token);
            return true;
        }catch (SignatureException ex){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
        }
    }

}
