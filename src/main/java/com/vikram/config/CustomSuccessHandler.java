package com.vikram.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.vikram.model.User;
import com.vikram.repository.UserRepository;
import com.vikram.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler{

	 @Autowired
	 private UserRepository userRepository;
	 
	 @Autowired
	 private UserService userService;
	
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
	
		String redirectUrl = null;
		 UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepository.findByEmail(username);
        String output = userService.generateOtp(user);
        if(output=="success") 
       	 redirectUrl="/login/otpVerification";
        
		new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
		
		
		
	}

}
