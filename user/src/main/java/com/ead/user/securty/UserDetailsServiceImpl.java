package com.ead.user.securty;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ead.user.model.UserModel;
import com.ead.user.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	UserRepository userRepository;
	
	//pesquisa um determina usuario pelo userName
	@Override  //retorno UserDetails implementado na classe UserDetailsImpl
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		    
		UserModel userModel = userRepository.findByUserName(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with Name: " + username));
		return UserDetailsImpl.build(userModel);
	}
	
	//pesquisa um determina usuario pelo userId
	public UserDetails loadUserById(UUID userId) throws AuthenticationCredentialsNotFoundException {
		UserModel userModel = userRepository.findById(userId)
				.orElseThrow(() -> new AuthenticationCredentialsNotFoundException("User not found with UserId:" + userId));
		return UserDetailsImpl.build(userModel);

	}

}
