package com.ead.user.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;


 //classe para aplicacao  do EntryPoint  do metodo configure(HttpSecurity http) na class WebSecurtyConfig
@Log4j2
@Component // é um Bean que deve ser grencia pelo Spring e valos injetar no WebConfigureSecurty
public class AuthenticationEntryPointImpl  implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AuthenticationException authException) throws IOException, ServletException {
//	        log.error("Unauthorized erro: {}", e.getMessage());
		    httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorazed");
		
	}

}
