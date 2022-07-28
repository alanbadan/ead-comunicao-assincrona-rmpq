package com.ead.notificacao.dto;

import javax.validation.constraints.NotNull;

import com.ead.notificacao.enums.NotificacaoStatus;

public class NotificacaoDto {
	
	@NotNull  //a notificacao não pode vir null
	private NotificacaoStatus notificacaoStatus;
	
	
	
	

	public NotificacaoStatus getNotificacaoStatus() {
		return notificacaoStatus;
	}

	public void setNotificacaoStatus(NotificacaoStatus notificacaoStatus) {
		this.notificacaoStatus = notificacaoStatus;
	}

	
	
}
