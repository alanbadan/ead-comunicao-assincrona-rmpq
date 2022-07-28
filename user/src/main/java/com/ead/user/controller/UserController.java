package com.ead.user.controller;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ead.user.dto.UserDto;
import com.ead.user.model.UserModel;
import com.ead.user.servicies.UserService;
import com.ead.user.specification.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user")
public class UserController {
	//metodo post esta separado pq precisade authenticacao
	
	@Autowired
	UserService userService;
	
   ///Metodo de retorno normal sem comunicacao sincrona com course///
	@GetMapping                                        //Paginacao default
	public ResponseEntity<Page<UserModel>> getAllUser(SpecificationTemplate.UserSpec spec,
			                                          @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable){
			                                         
		Page<UserModel> userModelPage = userService.findAll(spec,pageable);
		return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
	}	
	
	@GetMapping("/{userId}")
	public ResponseEntity<Object> geOneUser(@PathVariable(value = "userId") UUID userId) {
//		log.debug("Delete deleteUser userId received {}",userId);
		
		Optional<UserModel> userModelOptional = userService.findById(userId);
		if(!userModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
		}else {
			
		return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
	  }
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId){
		Optional<UserModel> userModelOptnional = userService.findById(userId);
		if(!userModelOptnional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
		}
		else {
			userService.delete(userModelOptnional.get());
			return ResponseEntity.status(HttpStatus.OK).body("Usuario deletado com sucesso");
		}
	}
	@PutMapping("/{userId}")
	public ResponseEntity<Object> upDateUser(@PathVariable(value = "userId") UUID userId, 
			                                 @RequestBody @Validated(UserDto.UserView.UserPut.class)
			                                 @JsonView(UserDto.UserView.UserPut.class) UserDto userDto){
		
///		log.debug("Put updateUser userDto received {}",userDto.toString());
		
		Optional<UserModel> userModelOptnional = userService.findById(userId);
		if(!userModelOptnional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ususario não encontrado");
		}else {
			var userModel = userModelOptnional.get();//peagando o user ja exixtente
			userModel.setFullName(userDto.getFullName());// setando os campos que pode altera r conofrme o interface no dto
			userModel.setPhoneNumber(userDto.getPhoneNumber());
			userModel.setCpf(userDto.getCpf());
			userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
			//salvando a atualizacao
			 userService.updateUser(userModel);
			
			return ResponseEntity.status(HttpStatus.OK).body(userModel);// respondendo o user atualizasdo		
		}
	}
	
   //atualizacao da senha
     @PutMapping("/{userId}/password")
     public ResponseEntity<Object> upDatePasswordUser(@PathVariable(value = "userId") UUID userId, 
    		                                          @RequestBody @Validated(UserDto.UserView.PasswordPut.class)
    		                                          @JsonView(UserDto.UserView.PasswordPut.class) UserDto userDto){
	Optional<UserModel> userModelOptnional = userService.findById(userId);
	if(!userModelOptnional.isPresent()) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ususario não encontrado");
	}//verificando a senha nova é igual a antiga
	if(!userModelOptnional.get().getPassword().equals(userDto.getOldPassword())) {
		
//		log.warn("Senhas não coincidem userid {}", userDto.getUserId()); // log para verifivar a senha caso seja errada multiplas veses
		
	    return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: senha deve ser diferente da antiga");
	
   }else {
		var userModel = userModelOptnional.get();//peagando o user ja exixtente
		userModel.setPassword(userDto.getPassword());
		userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
		userService.updatePassword(userModel);
		
		return ResponseEntity.status(HttpStatus.OK).body("Senha atualisada com sucesso");// retornando msg pq nãp precisa retornar o dados do usuario	
	}
  }

// alterando a imagem
	@PutMapping("/{userId}/imagem")
	public ResponseEntity<Object> upDateImage(@PathVariable(value = "userId") UUID userId,
			                                  @RequestBody @Validated(UserDto.UserView.ImagePut.class)
			                                  @JsonView(UserDto.UserView.ImagePut.class) UserDto userDto) {
		Optional<UserModel> userModelOptnional = userService.findById(userId);
		if (!userModelOptnional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ususario não encontrado");

		} else {
			var userModel = userModelOptnional.get();// peagando o user ja exixtente
			userModel.setImageUrl(userDto.getImagemUrl());
			userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
			userService.updateUser(userModel);

			return ResponseEntity.status(HttpStatus.OK).body(userModel);
																								
		}
	}
}


