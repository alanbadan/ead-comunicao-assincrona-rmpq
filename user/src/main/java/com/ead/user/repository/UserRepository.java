package com.ead.user.repository;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ead.user.model.UserModel;
                                                                        //por causa do spec
public interface UserRepository  extends JpaRepository<UserModel, UUID>,JpaSpecificationExecutor<UserModel>{

	boolean existsByUserName(String userName);
	boolean existsByEmail(String email);
	
}
