package com.ead.user.securty;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ead.user.model.UserModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl  implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	private UUID userId;
	private String fullName;
	private String userName;
	@JsonIgnore
	private String password;
	private String emal;
	private Collection<? extends GrantedAuthority> authorities; //colecao d eroleModel pq extends o GrantedAuthority
	
	                                       //metodo para fazer a conversão de userModel para userDetails
	public static UserDetailsImpl build (UserModel userModel) {
		List<GrantedAuthority> authorities = userModel.getRoles().stream() // extraino a lista de autorities ( vindo do roles) // .stream transformado em dados para para poder tarablahar neles
				                             .map(role -> new SimpleGrantedAuthority(role.getAuthority())) // fazendo a interação
				                             .collect(Collectors.toList());// convertendo a stream novamente em coleção
		                           return new UserDetailsImpl(  // iniciando o novo Userdatails vindo do construtor e fazendo a conversão
			                                  userModel.getUserId(),
				                              userModel.getFullName(),
				                              userModel.getUserName(),
				                              userModel.getPassword(),
				                              userModel.getEmail(),
				                              authorities);			
				
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities; //atributo definido no metodo UserdetailsImpl
	}

	
	public UserDetailsImpl() {
		
	}
	
	public UserDetailsImpl(UUID userId, String fullName, String userName, String password, String emal,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.userId = userId;
		this.fullName = fullName;
		this.userName = userName;
		this.password = password;
		this.emal = emal;
		this.authorities = authorities;
	}


	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}


	public UUID getUserId() {
		return userId;
	}


	public void setUserId(UUID userId) {
		this.userId = userId;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getEmal() {
		return emal;
	}


	public void setEmal(String emal) {
		this.emal = emal;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

}
