package com.ead.user.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.ead.user.enums.RoleType;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_roles")
public class RoleModel implements GrantedAuthority, Serializable{

	private static final long serialVersionUID = 1L;
	
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID roleId;
	@Enumerated(EnumType.STRING)
	@Column(nullable = true, unique = true, length = 20)
	private RoleType roleName;
	
	
	
	
	
	
	// metodo subescrito do GrantedAuthority
	@Override
	@JsonIgnore // caso necessite usar essa classe ignora o metodo
	public String getAuthority() {
		return this.roleName.toString();
	}

	
	public RoleModel() {
		
	}

	public RoleModel(UUID roleId, RoleType roleName) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
	}


	public UUID getRoleId() {
		return roleId;
	}


	public void setRoleId(UUID roleId) {
		this.roleId = roleId;
	}


	public RoleType getRoleName() {
		return roleName;
	}


	public void setRoleName(RoleType roleName) {
		this.roleName = roleName;
	}
	
	

}
