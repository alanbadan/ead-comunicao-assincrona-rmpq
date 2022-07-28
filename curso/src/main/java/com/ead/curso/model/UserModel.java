package com.ead.curso.model;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.catalina.User;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "tb_user") //replicacao parcila pq ele usa somente o que precisa
public class UserModel {
	      // sem anotracao do GenerateValue
	@Id /// como é uma comunicação sincrona o ID ja esta vindo do microsrvice User 
	private UUID userId;
	@Column(nullable = false, unique = true, length = 50)
	private String email;
	@Column(nullable = false, length = 100)
	private String fullName;
	@Column(nullable = false)
	private String userStatus;// para não ter muito vinculo com os enus do User (salva como string), para nãp precisar alterar
	@Column(nullable = false)
	private String userType;
	@Column(length = 20)
	private String cpf;
	@Column
	private String imageUrl;
	
	
	@ManyToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Set<CourseModel> course;
   
	public UserModel() {
		
	}

	public UserModel(UUID userId, String email, String fullName, String userStatus, String userType, String cpf,
			String imageUrl, Set<CourseModel> course) {
		super();
		this.userId = userId;
		this.email = email;
		this.fullName = fullName;
		this.userStatus = userStatus;
		this.userType = userType;
		this.cpf = cpf;
		this.imageUrl = imageUrl;
		this.course = course;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
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

	public Set<CourseModel> getCourse() {
		return course;
	}

	public void setCourse(Set<CourseModel> course) {
		this.course = course;
	}
	
     
}
