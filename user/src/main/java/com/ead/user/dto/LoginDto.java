package com.ead.user.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginDto { //dto para recebimento dos parametro de entrada pa logar

	@NotBlank //não pode ser null e nem vazio
	private String userName;
	
	@NotBlank
	private String password;
	
	
	
	
	public LoginDto() {
		
	}
	

	public LoginDto(@NotBlank String userName, @NotBlank String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
