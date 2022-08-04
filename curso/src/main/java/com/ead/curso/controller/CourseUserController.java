package com.ead.curso.controller;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ead.curso.dto.SubscriptionDto;
import com.ead.curso.enuns.UserStatus;
import com.ead.curso.model.CourseModel;
import com.ead.curso.model.UserModel;
import com.ead.curso.services.CourseService;
import com.ead.curso.services.UserService;
import com.ead.curso.specification.SpecificationTemplate;

import lombok.extern.log4j.Log4j2;

@Log4j2  
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
  //CONTROLLER PARA COMUNICACAO SINCRONA ENTRE USER E COURSE
public class CourseUserController {
	
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	UserService userService;

	
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	@GetMapping("/course/{courseId}/user")
	public ResponseEntity<Object> getAllUserByCourse(SpecificationTemplate.UserSpec spec, //spec criado por causa da comunicacao assincrona manytomany
			                                         @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
			                                         @PathVariable(value = "courseId") UUID courseId){
		
		Optional<CourseModel> courseModelOptional = courseService.findById(courseId);//verificando se o curso existe
		if(!courseModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(userService.findAll(SpecificationTemplate.userCourseId(courseId).and(spec), pageable)); 
		
	}
	 
	
	@PreAuthorize("hasAnyRole('STUDENT')")
	  @PostMapping("/courses/{courseId}/users/subscription")
	    public  ResponseEntity<Object> saveSubscriptionUserInCourse(@PathVariable(value = "courseId") UUID courseId,
	                                                                @RequestBody @Valid SubscriptionDto subscriptionDto){
		    //verifiacndo se existe 
	        Optional<CourseModel> courseModelOptional = courseService.findById(courseId);
	        if(!courseModelOptional.isPresent()){
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course Not Found.");
	        }
	         //verificando se existe uma incricao
	        if(courseService.existsByCourseAndUser(courseId, subscriptionDto.getUserId())) {
	            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: subscription already exists!");
	        }
	        // se  retorna false ele verifica se o usuario existe
	        Optional<UserModel> userModelOptional = userService.findById(subscriptionDto.getUserId());
	        if(!userModelOptional.isPresent()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
	        }
	        //verifiacando se é um usuario bloqueado ao não 
	        if(userModelOptional.get().getUserStatus().equals(UserStatus.BLOCKED.toString())){ // to string pq vem como String no UserModel
	            return ResponseEntity.status(HttpStatus.CONFLICT).body("User is blocked.");
	        }
	        // salvando a matricula sem usso de mensageria para notificacao de inscricao de um aluno em um determinado curso.
//	        courseService.saveSubscriptionUserInCourse(courseModelOptional.get().getCourseId(), userModelOptional.get().getUserId());
//	        return ResponseEntity.status(HttpStatus.CREATED).body("Subscription created successfully.");
//   }
	  //aqui ele publica uma msg para o broker e cham o metodo para publicar a msg
	        courseService.saveSubscriptionUserInCourseAndSendNotificacao(courseModelOptional.get(), userModelOptional.get());
	        return ResponseEntity.status(HttpStatus.CREATED).body("Subscription created successfully.");
	  }
}