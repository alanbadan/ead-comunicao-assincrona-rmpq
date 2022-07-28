package com.ead.curso.validation;

import java.util.Optional;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ead.curso.dto.CourseDto;
import com.ead.curso.enuns.UserType;
import com.ead.curso.model.UserModel;
import com.ead.curso.services.UserService;

@Component // monstarando que é um bean gerenciado pelo spring
public class CourseValidator  implements Validator{
	
	@Autowired // definindo um validator default para não dar conflito
	@Qualifier("defaultValidator")//sem essa anotacao da conflito pq ja estamos usando um beanValidator do SrpingMvc config support
	private Validator validator;
	
	@Autowired
	UserService userService;
	

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override  
	public void validate(Object o, Errors errors) {
		// precisa fazer um casting pq é um Object
		CourseDto courseDto = (CourseDto) o;
		validator.validate(courseDto, errors); // faz a mesma coisa que o @Valid (conferindo todos os atributos do dto)
		if(!errors.hasErrors()) { //se conter erro ele entra no if
			validateUserInstructor(courseDto.getUserInstructor(), errors);
		}
	
	}
	
	//validacao de instrutor comunicacao assincrona
	private void validateUserInstructor(UUID userInstructor, Errors errors) {
		//busacando o userInstructor
		Optional<UserModel> userModelOptional = userService.findById(userInstructor);
		if(!userModelOptional.isPresent()) { //verificando se ele existe
			errors.rejectValue("userInstructor", "UserInstructorError", "INSTRUCTOR Not Found");
		}
		if(userModelOptional.get().getUserType().equals(UserType.STUDENT.toString())) { //to string por causa do enum
			errors.rejectValue("userInstructor", "UserInstructorError", "User must be INSTRUCTOR OR ADMIN");
		}
	}

}
