package com.ead.curso.services.impl;

import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ead.curso.model.UserModel;
import com.ead.curso.repository.CourseRepository;
import com.ead.curso.repository.UserRepository;
import com.ead.curso.services.UserService;

@Service
public class UserServiceImpl  implements UserService{
	
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CourseRepository courseRepository;

	@Override
	public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {
		return userRepository.findAll(spec, pageable);
	}

	@Override
	public UserModel save(UserModel userModel) {
		return userRepository.save(userModel);
	}
    
	@Transactional
	@Override
	public void delete(UUID userId) {
		 courseRepository.deleteCourseUserByUser(userId);// deleção em cascata //metdod para deletar relacao curso usuario state transfer
		 userRepository.deleteById(userId);
		
	}

	@Override
	public Optional<UserModel> findById(UUID userInstructor) {
		return userRepository.findById(userInstructor);
	}
	

}