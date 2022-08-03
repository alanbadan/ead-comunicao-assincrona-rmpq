package com.ead.user.servicies.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ead.user.enums.RoleType;
import com.ead.user.model.RoleModel;
import com.ead.user.repository.RoleRepository;
import com.ead.user.servicies.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	RoleRepository roleRepository;

	@Override
	public Optional<RoleModel> findByRoleName(RoleType roleType) {
		return roleRepository.findByRoleName(roleType);
	}

}
