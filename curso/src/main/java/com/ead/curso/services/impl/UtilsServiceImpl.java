package com.ead.curso.services.impl;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.ead.curso.services.UtilsService;

public class UtilsServiceImpl implements UtilsService {
	
	
   String REQUEST_URL_COURSE = "http://localhost:8087";

//	metodo para montar a URI
    public String createUrlGetAllUsersByCourse(UUID courseId, Pageable pageable) {
	return  REQUEST_URL_COURSE + "/user?courseId" + courseId + "&page" + pageable.getPageNumber() + "&sizes" + 
	        pageable.getPageSize() + "&sort" + pageable.getSort().toString().replaceAll(": ", ".");
}
	
   
}
