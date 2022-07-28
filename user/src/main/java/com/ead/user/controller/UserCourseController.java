package com.ead.user.controller;

import java.util.Optional;
import java.util.UUID;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ead.user.client.CourseClient;
import com.ead.user.model.UserModel;
import com.ead.user.servicies.UserService;

import lombok.extern.log4j.Log4j2;

@Log4j2  
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
  //CONTROLLER PARA COMUNICACAO SINCRONA ENTRE USER E COURSE
public class UserCourseController {
	
	@Autowired
	CourseClient courseClient;
	
	@Autowired
	UserService userService;
	
	
	
	
	  /*
		metedo se retorno com object ( sem o uso do metodo de delecao e verificacdao se existe a realcao user e course
		@GetMapping("/user/{userId}/course")
		public ResponseEntity<Page<CourseDto>> getAllCourseByUser(@PageableDefault(page = 0, size = 10, sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable,
				                                                  @PathParam(value = "userId") UUID userId){
			return ResponseEntity.status(HttpStatus.OK).body(courseClient.getAllCourseByUser(userId, pageable)); //metodo CLIENT para chamr um curso de um determinado usuario
		}
	*/	
	//metedo get com verificaao sincrona se ha reacao entere user e course 
	//lembrando do Object para retorno de qualquer tipo dependendo do cenario
	@GetMapping("/user/{userId}/course")
	public ResponseEntity<Object> getAllCourseByUser(@PageableDefault(page = 0, size = 10, sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable,
			                                                  @PathParam(value = "userId") UUID userId){
		//verificando se exixte um usuario para o curso (comunicacao sincrona com curse)
	    Optional<UserModel> userModelOptional= userService.findById(userId)	;	
	    if(!userModelOptional.isPresent()) {
	    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
	    }
	    //se existir as realcao o ms retorna toda a relacao
	    return ResponseEntity.status(HttpStatus.OK).body(courseClient.getAllCourseByUser(userId, pageable)); //metodo CLIENT para chamr um curso de um determinado usuario
	}
	

	
	
}
