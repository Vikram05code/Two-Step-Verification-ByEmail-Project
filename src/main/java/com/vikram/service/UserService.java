package com.vikram.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.vikram.dto.UserRegisteredDTO;
import com.vikram.model.User;

public interface UserService extends UserDetailsService{
	
	User save(UserRegisteredDTO userRegisteredDTO);
	
	String generateOtp(User user);

}
