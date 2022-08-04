package com.ead.user.client;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.ead.user.dto.CourseDto;
import com.ead.user.dto.ResponsePageDto;
import com.ead.user.servicies.UtilsService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.log4j.Log4j2;

//CIRCUITEBRAKER E RETRY(NÃO RECOMENDADO PQ COUSA MUITAS TENTAIVAS DE CHAMADAS) 

@Log4j2
@Component //anotacao para ser um Bean do Spring paea ele gerenciar
public class CourseClient { //classe para comunicao sincrona  implementacao da requisiao de microservice user p course
	                      //precisa de uma classe de config para  (comunicacao externa) restTemplate
	
	@Autowired
	RestTemplate restTemplate;
	
	
	@Autowired //injetando para uso do metodo para montar a uri
	UtilsService utilsService;
	
	 @Value("${ead.api.url.course}")
       String REQUEST_URL_COURSE;

	//@Retry(name = "retryInstance", fallbackMethod = "retryfallback")
     @CircuitBreaker(name = "circuitbreakerInstance")
	public Page<CourseDto> getAllCourseByUser(UUID userId, Pageable pageable, String token){ //o token para comunicao sincrona de autorizacao
		
		 //usando o metodo da interface
		List<CourseDto> serchResult =null;
		ResponseEntity<ResponsePageDto<CourseDto>> result = null;
		String url = REQUEST_URL_COURSE + utilsService.createUrlGetAllCouseByUser(userId, pageable);
		//montando o header para passar quando vem o token na requisicao
		HttpHeaders headers = new HttpHeaders(); //inicainado os headers
		headers.set("Authorization", token); // setando o paremetro do metodo
		HttpEntity<String> requestEntity = new HttpEntity<String>("parameters", headers); // montando o body dos headers

		try {
			//utilisando um classe ABSTRATA DO  spring CORE  para reposta da paginacao
			                            //definindo a parametrisaçao de cursoDto
			  ParameterizedTypeReference<ResponsePageDto<CourseDto>> responseType = new ParameterizedTypeReference<ResponsePageDto<CourseDto>>() {};
			//defindo o response entity http
		     result = restTemplate.exchange(url, HttpMethod.GET, requestEntity, responseType); //null pa não ha header/ com a montagem do header passa o header com o teken da requisicao do estudante 
			//obtendo o content nesse caso o content
			serchResult = result.getBody().getContent();
			
		}catch (HttpStatusCodeException e) {
//			log.error("Error request /courses {} ", e);
		}
			return result.getBody();
	}
       public Page<CourseDto> circuitbreakerfallback(UUID userId, Pageable pageable, Throwable t) {
 //     log.error("Inside circuit breaker fallback, cause - {}", t.toString());
        List<CourseDto> searchResult = new ArrayList<>();
        return new PageImpl<>(searchResult);

		
	}
      
       public Page<CourseDto> retryfallback(UUID userId, Pageable pageable, Throwable t) {
//        log.error("Inside retry retryfallback, cause - {}", t.toString());
        List<CourseDto> searchResult = new ArrayList<>();
        return new PageImpl<>(searchResult);

 }
}
 