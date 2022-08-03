package com.ead.user.dto;

import io.github.resilience4j.core.lang.NonNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor //po causa que somente o token vai ser passado no construtor o type ja esta definido
public class JwtDto {
	
	@NonNull
	private String token;
	private String type = "Bearer";
	
	
	
	
	public JwtDto(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	

}
