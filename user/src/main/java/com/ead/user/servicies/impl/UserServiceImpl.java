package com.ead.user.servicies.impl;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ead.user.client.CourseClient;
import com.ead.user.enums.ActionType;
import com.ead.user.model.UserModel;
import com.ead.user.publishers.UserEventPublisher;
import com.ead.user.repository.UserRepository;
import com.ead.user.servicies.UserService;


@Service
public class UserServiceImpl implements UserService{
	

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CourseClient courseClient;
	
	@Autowired
    UserEventPublisher userEventPublisher;
	

	@Override
	public List<UserModel> findAll() {
		
		return userRepository.findAll();
	}

	@Override
	public Optional<UserModel> findById(UUID userId) {
     	return userRepository.findById(userId);
     	
	}
	
	@Transactional
    @Override
    public void delete(UserModel userModel) {
        userRepository.delete(userModel);
    }

	@Override
	public UserModel save(UserModel userModel) {
		 return userRepository.save(userModel);
		
	}

	@Override
	public boolean existByUserName(String userName) {
	
		return userRepository.existsByUserName(userName);//declarado metodo na interface no userrepository para verificar se ha cadastro
	}

	@Override
	public boolean existByEmail(String email) {
		
		return userRepository.existsByEmail(email); // declarado na interface repository
	}

	@Override
	public Page<UserModel> findAll(Specification<UserModel>spec, Pageable pageable) {
	
		return userRepository.findAll(spec, pageable);
	}
	 @Transactional
	    @Override
	    public UserModel saveUser(UserModel userModel){
	        userModel = save(userModel);
	        userEventPublisher.publishUserEvent(userModel.convertToUserEventDto(), ActionType.CREATE);
	        return userModel;
	    }

	    @Transactional
	    @Override
	    public void deleteUser(UserModel userModel) {
	        delete(userModel);
	        userEventPublisher.publishUserEvent(userModel.convertToUserEventDto(), ActionType.DELETE);
	    }

	    @Transactional
	    @Override
	    public UserModel updateUser(UserModel userModel) {
	        userModel = save(userModel);
	        userEventPublisher.publishUserEvent(userModel.convertToUserEventDto(), ActionType.UPDATE);
	        return userModel;
	    }

	    @Override
	    public UserModel updatePassword(UserModel userModel) {
	        return save(userModel);
	    }
	
}
