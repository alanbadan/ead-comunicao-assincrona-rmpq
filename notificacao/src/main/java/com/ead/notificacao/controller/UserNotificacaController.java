package com.ead.notificacao.controller;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ead.notificacao.dto.NotificacaoDto;
import com.ead.notificacao.model.NotificacaoModel;
import com.ead.notificacao.service.NotificacaoService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserNotificacaController {
	
	@Autowired
	NotificacaoService notificacaoService;
	
	
	@PreAuthorize("hasAnyRole('STUDENT')")
	@GetMapping("/user/{userId}/notificacao") //retornando as msg que ainda não foram lidas ( satus Created)
	public ResponseEntity<Page<NotificacaoModel>> getAllNotificacao(@PathVariable(value = "userId") UUID userId,
			                                                         @PageableDefault(page = 0, size = 10, sort = "notificacaoId", direction = Sort.Direction.ASC) Pageable pageable){
		return ResponseEntity.status(HttpStatus.OK).body(notificacaoService.findAllNotificacaoByUser(userId, pageable));
	}
	
	//endpoint para atualizacao de msg de Created pta Lido
	@PreAuthorize("hasAnyRole('STUDENT')")
	@PutMapping("/user/{userId}/notificacao/{notificacaoId}")
	public ResponseEntity<Object> upDateNotificacao(@PathVariable(value = "userId") UUID userId,
			                                        @PathVariable(value = "notificacaoId") UUID notificacaoId,
			                                        @RequestBody @Valid NotificacaoDto notificacaoDto){
		Optional<NotificacaoModel> notificacaoModelOptional = notificacaoService.findByNotificacaoIdAndUserId(notificacaoId, userId); //coferindo se existe esse usuario e a notificacao
		if(notificacaoModelOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notificacao não encontrada");
		}
		
		notificacaoModelOptional.get().setNotificacaoStatus(notificacaoDto.getNotificacaoStatus()); //atualizando o status com os dados que estão vindo no dto de entrada
		notificacaoService.saveNotificacao(notificacaoModelOptional.get());  //salvando a notificacao 
		return ResponseEntity.status(HttpStatus.OK).body(notificacaoModelOptional.get());
	}

}
