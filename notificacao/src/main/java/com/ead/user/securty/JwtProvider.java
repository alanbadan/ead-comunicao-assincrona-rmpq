package com.ead.user.securty;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;





@Component
public class JwtProvider {
	
  
	
	@Value("${ead.auth.jwtSecret}")
	private String jwtSecret;

	
   public String getSubjectJwt(String token) { // peagando o subjecgt do token
	   return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJwt(token).getBody().getSubject();
   }
   
   
   //metodo para extrair os claims(reinvidicações)
   public String getClainNameJwt(String token, String clainName) {                    //pasando o clain que se quer extrair
	   return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get(clainName).toString();
   }
	

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
