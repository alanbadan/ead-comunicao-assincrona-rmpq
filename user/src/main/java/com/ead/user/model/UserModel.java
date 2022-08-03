package com.ead.user.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;
import org.springframework.beans.BeanUtils;

import com.ead.user.dto.UserEventDto;
import com.ead.user.enums.UserStatus;
import com.ead.user.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "tb_user")
public class UserModel  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID userId;
	@Column(nullable = false, unique = true, length = 50)
	private String userName;
	@Column(nullable = false, unique = true, length = 50)
	private String email;
	@Column(nullable = false,length = 150)
	@JsonIgnore
	private String password;
	@Column(nullable = false, length = 150)
	private String fullName;
	@Column(length = 50)
	private String phoneNumber;
	@Column(length = 50)
	private String cpf;
	@Column
	private String imageUrl;
	@Column(nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime creationDate;
	@Column(nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime lastupdateDate;
	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;
	@Enumerated(EnumType.STRING)
	private UserType userType;
	
	
	//relacionamento de usuarios com Roles 
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //controlando o acessao a coleção
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tb_user_roles",  //nome tab intermediaria gerada com duas chaves 
	           joinColumns = @JoinColumn(name = "user_id"),
	           inverseJoinColumns = @JoinColumn(name = "role_id")) //relacionamento do outro lado
	private Set<RoleModel> roles = new HashSet<>();
	

	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
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
	public LocalDateTime getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}
	public LocalDateTime getLastUpdateDate() {
		return lastupdateDate;
	}
	public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
		this.lastupdateDate = lastUpdateDate;
	}
	public UserStatus getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
	
    public Set<RoleModel> getRoles() {
		return roles;
	}
	public void setRoles(Set<RoleModel> roles) {
		this.roles = roles;
	}
	/*	
	 //metodo para conversão do dto em UserModel( outra abordagem seria o BeansUtlis)
	public UserCourseModel convertToUserCourseModel(UUID courseId)  { //recebendo o courseID do Dto
			return new UserCourseModel(null, courseId, this);
	}
	*/
	public UserEventDto convertToUserEventDto(){
        var userEventDto = new UserEventDto();
        BeanUtils.copyProperties(this, userEventDto);
        userEventDto.setUserType(this.getUserType().toString());
        userEventDto.setUserStatus(this.getUserStatus().toString());
        return userEventDto;
	}
}
