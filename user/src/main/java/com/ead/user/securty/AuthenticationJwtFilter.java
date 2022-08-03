package com.ead.user.securty;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class AuthenticationJwtFilter extends OncePerRequestFilter{
	
	
	@Autowired
	JwtProvider jwtProvider;
	
	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		// usando a abordagem de userId na geracao de token 
		try {
			String jwtStr = getTokenHeader(request);
			if(jwtStr != null && jwtProvider.validatejwt(jwtStr)) { // verificando se não é null e se o token esta corretor com o metodo  **public boolean validatejwt(String authToken)** 
				String userId = jwtProvider.getUserSubjectjwt(jwtStr); // extraindo o userId do token com metodo ** public String getUserIdjwt(String token)**
				UserDetails userDetails = userDetailsServiceImpl.loadUserById(UUID.fromString(userId)); // convertendo String em UUId
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); //null pq estamos trabalhando sem credenciais // getauthorities (student,admin ...)
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
	
/*		
		// usando a abordagem de userName na geracao de token 
	try {
		String jwtStr = getTokenHeader(request);
		if(jwtStr != null && jwtProvider.validatejwt(jwtStr)) { // verificando se não é null e se o token esta corretor com o metodo  **public boolean validatejwt(String authToken)** 
			String userName = jwtProvider.getUserNamejwt(jwtStr); // extraindo o username do token com metodo ** public String getUserNamejwt(String token)**
			UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userName);
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); //null pq estamos trabalhando sem credenciais // getauthorities (student,admin ...)
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
*/		
	}catch (Exception e) {
		logger.error("Cannot set user Authentication: {}", e);
	}
	filterChain.doFilter(request, response);
		
	}
	
	private String getTokenHeader(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization"); // extraindo o header onde esta o Autorization(lembrando que o token é dividido em tres partes 
		if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer")) {
			return headerAuth.substring(7); //extrindo tudo depois do carcter 7
		}
		return null;
	}

}