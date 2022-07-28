package com.ead.notificacao.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ead.notificacao.enums.NotificacaoStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;


//COMUNICACAO ASSINCRONA VIA COMANDS
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "tb_notificacao")
public class NotificacaoModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID notificacaoId;
	@Column(nullable= false)
	private UUID userId;
	@Column(nullable = false, length = 150)
	private String title;
	@Column(nullable = false)
	private String message;
	@Column(nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime creationDate;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private NotificacaoStatus notificacaoStatus;
	
	
	
	
	
	public UUID getNotificacaoId() {
		return notificacaoId;
	}
	public void setNotificacaoId(UUID notificacaoId) {
		this.notificacaoId = notificacaoId;
	}
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
	public LocalDateTime getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
	public NotificacaoStatus getNotificacaoStatus() {
		return notificacaoStatus;
	}
	public void setNotificacaoStatus(NotificacaoStatus notificacaoStatus) {
		this.notificacaoStatus = notificacaoStatus;
	}

	
}
