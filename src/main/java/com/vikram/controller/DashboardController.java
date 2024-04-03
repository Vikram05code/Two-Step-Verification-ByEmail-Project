package com.vikram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vikram.model.User;
import com.vikram.repository.UserRepository;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
    public String displayDashboard(Model model){
		SecurityContext securityContext = SecurityContextHolder.getContext();
		UserDetails user = (UserDetails) securityContext.getAuthentication().getPrincipal();
		User users = userRepository.findByEmail(user.getUsername());
		if(users.isActive()) {
		model.addAttribute("userDetails", users.getName());
        return "dashboard";
		}else {
			return "redirect:/login/otpVerification?error";
		}
    }
	
}
