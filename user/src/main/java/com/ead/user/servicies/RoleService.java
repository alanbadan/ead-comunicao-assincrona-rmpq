package com.ead.user.servicies;

import java.util.Optional;

import com.ead.user.enums.RoleType;
import com.ead.user.model.RoleModel;

public interface RoleService  {

	Optional<RoleModel> findByRoleName(RoleType roleType);
}
