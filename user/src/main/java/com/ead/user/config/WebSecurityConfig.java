package com.ead.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // anoatacao para o autenticatio Mangener global
@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter{
	
	
	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	AuthenticationEntryPointImpl authenticationEntryPoint;
	
	
	
	private static final String[] AUTH_WHITELIST = {  // definindo uma lista de uri que não precisa de autenticacao
			"/auth/**"
	};
	
	
	

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint) //retorno um mensagem de erro no caso de erro na autorização
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll() // permitindo todas as chamadas sem autenticacao
                .anyRequest().authenticated()
                .and()
                .csrf().disable()// normalmente não se desabilita quando se usa jsf/thymeleaf
                .formLogin();
    }

	//buscando o ´passwor npo banco e convertendo userModel em UserDatails e verificando as credenciais salvas no banco e realisa a autenticacao
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceImpl)
		     .passwordEncoder(passwordEncoder());
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
