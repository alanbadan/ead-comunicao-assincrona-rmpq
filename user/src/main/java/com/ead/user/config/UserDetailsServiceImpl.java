package com.ead.user.config;

import org.springframework.beans.factory.annotation.Autowired;
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
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with Name : " + username));
		return UserDetailsImpl.build(userModel);
	}

}
