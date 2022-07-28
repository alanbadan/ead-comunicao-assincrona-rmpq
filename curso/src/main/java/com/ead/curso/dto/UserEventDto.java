package com.ead.curso.dto;

import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.ead.curso.model.UserModel;

import lombok.Data;

@Data //esse enum esat no msUsertb pq ele é quem vai publicar / aqui o curso esta escutando para receber no COSUMER
public class UserEventDto { //dto para o userEvent publicar quase todos atributos do userModel

	private UUID userId;
	private String userName;
	private String email;	
	private String fullName;
	private String userStatus;
	private String userType;
	private String phoneNumber;
	private String cpf;
	private String imageUrl;
	private String actionType; //preenche com o tipo da açao (get,post ,put criacao,atualizacao,deleçao de um usuario)
	
	
	//lombok não esta funcionando 	
	public UserEventDto() {
		
	}
	
	public UserEventDto(UUID userId, String userName, String email, String fullName, String userStatus, String userType,
			String phoneNumber, String cpf, String imageUrl, String actionType) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.email = email;
		this.fullName = fullName;
		this.userStatus = userStatus;
		this.userType = userType;
		this.phoneNumber = phoneNumber;
		this.cpf = cpf;
		this.imageUrl = imageUrl;
		this.actionType = actionType;
		
		
	}
	public UUID getUserId() {
		return userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	
// metod para converter userEvnet Dto para userModel
	public UserModel convertToUserModel() {
		var userModel = new UserModel();
		BeanUtils.copyProperties(this, userModel); //o this é o userEventDto
		return userModel;
	}
  
}
