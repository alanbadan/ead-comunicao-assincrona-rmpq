package com.ead.user.servicies;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

//interface para melhorar a montagem/responsabilidade  da uri 
public interface UtilsService {
	 //declarando o metodo que cria a uri
	String createUrlGetAllCouseByUser(UUID userId, Pageable pageable);

	

}
