package com.ead.user.servicies.impl;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.ead.user.servicies.UtilsService;

// implementacao da interface pra melhorar e responsabilidade de montar a uri de comunicaçao do micorservices
public class UtilsServiceImpl implements UtilsService {
	
	
	String REQUEST_URL_COURSE = "http://localhost:8082";
	
//	metodo para montar a URI
	public String createUrlGetAllCouseByUser(UUID userId, Pageable pageable) {
	return  REQUEST_URL_COURSE + "/couse?userId" + userId + "&page" + pageable.getPageNumber() + "&sizes" + 
	        pageable.getPageSize() + "&sort" + pageable.getSort().toString().replaceAll(": ", ".");

	}
}
