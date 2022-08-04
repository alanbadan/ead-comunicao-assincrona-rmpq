package com.ead.user.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ead.user.dto.InstructorDto;
import com.ead.user.enums.RoleType;
import com.ead.user.enums.UserType;
import com.ead.user.model.RoleModel;
import com.ead.user.model.UserModel;
import com.ead.user.servicies.RoleService;
import com.ead.user.servicies.UserService;

import lombok.extern.log4j.Log4j2;

@Log4j2 //não funciona
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/instructor")
public class InstructorController { //controller par instrutor
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@PreAuthorize("hasAnyRole('INSTRUCTOR')")
	@PostMapping("/subscription")
	public ResponseEntity<Object> saveSubscriptionInstructor(@RequestBody @Valid InstructorDto instructorDto){
		// verificando se esse userId exixte
		Optional<UserModel> userModelOptional = userService.findById(instructorDto.getUserId());
		if(!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not Found");
		}else {
			//verificando se a role de instructor esta no banco de dados
			RoleModel roleModel = roleService.findByRoleName(RoleType.ROLE_INSTRUCTOR)
					.orElseThrow(() -> new RuntimeException("Error Role not Found"));
			
			//pegando o usuario e stando um novos dados agora como instrutor
			var userModel = userModelOptional.get();
			userModel.setUserType(UserType.INSTRUCTOR);
			userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
			userModel.getRoles().add(roleModel);// adicionando a colecai de roles antes de salvar o usuario
//			userService.save(userModel);//salvando
			userService.updateUser(userModel);//atualizando os dsdos e
			return ResponseEntity.status(HttpStatus.OK).body(userModel);
		}
		
	}

}
