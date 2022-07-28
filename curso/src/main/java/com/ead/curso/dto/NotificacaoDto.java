package com.ead.curso.dto;

import java.util.UUID;
//COMUNICACAO ASSINCRONA VIA COMANDS
public class NotificacaoDto { //dto para receber a notificacao esse é o msm dto do projeto notificacao

	
	private UUID userId;
	private String title;
	private String message;
	
	

	
	public UUID getUserId() {
		return userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
