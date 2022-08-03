package com.ead.user.securty;

import java.util.Date;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class JwtProvider {
	
	@Value("${ead.auth.jwtSecret}")
	private String jwtSecret;
	@Value("${ead.auth.jwtExpirationMs}")
	private int jwtExpirationMs;
	
	
	
	//metodo para gerar o token  
		//usando o UserDetaislImpl pelo  userId e não mais pelo username.
		public String generateJwt(Authentication authentication) {
			UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
			
			//pegando a lista de roles e transformando em String
			final String roles = userPrincipal.getAuthorities().stream()
					     .map(role -> {
					    	 return role.getAuthority();
					     }).collect(Collectors.joining(","));
			//usando o userId definido no UserDeatilsImpl pelo userId (melhor opção para microServices) // ele considera o UserId ao inves do Username
			return Jwts.builder()
					   .setSubject((userPrincipal.getUserId().toString())) //to string pq transforma UUID em string
					   .claim("role", roles) //passando a role depois de extrair da lista roles
					   .setIssuedAt(new Date())  //data da emissao do token
					   .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
					   .signWith(SignatureAlgorithm.HS512, jwtSecret) //assinatura e chave screta
					   .compact();
		}
	
/*	
	//metodo para gerar o token  
	//usando o UserDetaisl pelo nome e não pelo userId
	public String generateJwt(Authentication authentication) {
		UserDetails userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		
		//pegando a lista de roles e transformando em String
		final String roles = userPrincipal.getAuthorities().stream()
				     .map(role -> {
				    	 return role.getAuthority();
				     }).collect(Collectors.joining(","));
		
		// o uso do metodo userPrincipal da Interface d o Spring UserDetails ele paga pelo nome e não pelo id pq a interface do 
		// spring não ha o parametro userId , isso é bom para Monolito 
		
		return Jwts.builder()
				   .setSubject((userPrincipal.getUsername())) //nome do usuario
				   .claim("role", roles) //passando a role depois de extrair da lista roles
				   .setIssuedAt(new Date())  //data da emissao do token
				   .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				   .signWith(SignatureAlgorithm.HS512, jwtSecret) //assinatura e chave screta
				   .compact();
	}
*/	
	 //** METODOS para validacao de token
		
		//metodo para extrair o useId ou usernema metodo mais generico
		public String getUserSubjectjwt(String token) { // recebendo o token gerado
			          //recuperando o userId ultilizando essas classes ja prontas jwt
			return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
		}	
			
/*
	//metodo para extrair o username aundo aplicado o teken passando o userName 
	public String getUserNamejwt(String token) { // recebendo o token gerado
		          //recuperando o username ultilizando essas classes ja prontas jwt
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}
*/
	// metodo para validacao true or false
	public boolean validatejwt(String authToken) {
		try { //recebendo a chavo do token
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		}catch (SignatureException e) {
//			log.error("invalid Jwt signature: {}", e.getMessage());
		}catch (MalformedJwtException e) {
//			log.error("invalid Jwt : {}", e.getMessage());
		}catch (ExpiredJwtException e) {
//			log.error("invalid Jwt expireded: {}", e.getMessage());
		}catch (UnsupportedJwtException e) {
//			log.error("invalid Jwt is unsupported: {}", e.getMessage());
		}catch (IllegalArgumentException e) {
//			log.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}
	
	
	
}
