package com.ead.user.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ead.user.dto.InstructorDto;
import com.ead.user.enums.UserType;
import com.ead.user.model.UserModel;
import com.ead.user.servicies.UserService;

import lombok.extern.log4j.Log4j2;

@Log4j2 //não funciona
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/instructor")
public class InstructorController { //controller par instrutor
	
	@Autowired
	UserService userService;
	
	
	@PostMapping("/subscription")
	public ResponseEntity<Object> saveSubscriptionInstructor(@RequestBody @Valid InstructorDto instructorDto){
		// verificando se esse userId exixte
		Optional<UserModel> userModelOptional = userService.findById(instructorDto.getUserId());
		if(!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
		}else {
			//pegando o usuario e stando um novos dados agora como instrutor
			var userModel = userModelOptional.get();
			userModel.setUserType(UserType.INSTRUCTOR);
			userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
			userService.save(userModel);//atualizando os dsdos e slavando
			return ResponseEntity.status(HttpStatus.OK).body(userModel);
		}
		
	}

}
