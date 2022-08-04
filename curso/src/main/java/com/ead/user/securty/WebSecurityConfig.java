package com.ead.user.securty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // anoatacao para o autenticatio Mangener global
@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter{
	
	
	
	
	@Autowired
	AuthenticationEntryPointImpl authenticationEntryPoint;
	
	
	
	//inserrindo um Bean do AuthenticationJwtFilter
	@Bean
	public AuthenticationJwtFilter authenticationJwtFilter() {
		return new AuthenticationJwtFilter();
	}
	//metodo para definição de hierarquia de roles (a autorizasao a nvel de controller)
	@Bean
	public RoleHierarchy roleHierarchy( ) {
		RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
		String hierarchy = "ROLE_ADMIN > ROLE_INSTRUCTOR \n ROLE_INSTRUCTOR > ROLE_STUDENT \n ROLE_STUDENT > ROLE_USER";
		roleHierarchy.setHierarchy(hierarchy);
		return roleHierarchy();
	}
	

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
  //              .httpBasic() usamos quando se tem o basic authentication
                .exceptionHandling() 
                .authenticationEntryPoint(authenticationEntryPoint) //retorno um mensagem de erro no caso de erro na autorização
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // fazendo o gerenciamento da sessao (não guarda estado todo vez deve-se passar o token) // naõ se usa no basic atuthentication 
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();// normalmente não se desabilita quando se usa jsf/thymeleaf
 //               .formLogin();
        http.addFilterBefore(authenticationJwtFilter(), UsernamePasswordAuthenticationFilter.class); //passando o filter para validacao dos tokens
    }

	//no caso não utizamos mais esse metodo , mas deixe o metodo por caus ado extends WebSecurityConfigurerAdapter
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		
	}
	
	//metodo paea retornar um authenticationManeger no controller de authentication
	@Override
	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	/*	sem uma classe de UserDetails usa-se esse metodo pq esta gravando em memoria o password
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication() //salvando em memoria 
                .withUser("admin")
                .password(passwordEncoder().encode("123456"))
                .roles("ADMIN");

    }
*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
