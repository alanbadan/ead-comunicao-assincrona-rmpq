package com.ead.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ead.user.enums.RoleType;
import com.ead.user.model.RoleModel;

public interface RoleRepository extends JpaRepository<RoleModel, UUID> {

	//metodo para busca de nome role e ver se existe
	Optional<RoleModel> findByRoleName(RoleType name);
}
