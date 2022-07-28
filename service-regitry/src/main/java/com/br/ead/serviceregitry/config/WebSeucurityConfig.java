package com.br.ead.serviceregitry.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // configuracao para acessar o serviceRegistry com authenticacao agora todoa ms devem se autorizas antes de se registrarem no eureka
public class WebSeucurityConfig extends WebSecurityConfigurerAdapter{
	
	@Value("${ead.serviceRegistry.username}")
	private String username;
	
	@Value("${ead.serviceRegistry.password}")
	private String password;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.httpBasic()
		    .and()
		    .authorizeRequests()
		    .anyRequest().authenticated() //qualquer requisião pe=recisa estar autenticada // podedo passar diferentes roles aqui tb
		    .and()
		    .csrf().disable()
		    .formLogin();
	}
	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.inMemoryAuthentication() //aqui esta em memorai futuramente estara no banco de dados
		     .withUser(username)
		     .password(passwordEncoder().encode(password))
		     .roles("ADMIN");		 
	}
    
	@Bean
	private PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
