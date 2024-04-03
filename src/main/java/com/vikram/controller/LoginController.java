package com.vikram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vikram.dto.UserLoginDTO;
import com.vikram.model.User;
import com.vikram.repository.UserRepository;
import com.vikram.service.UserService;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	
	 @ModelAttribute("user")
	    public UserLoginDTO userLoginDTO() {
	        return new UserLoginDTO();
	    }
	    
		@GetMapping
		public String login() {
			return "login";
		}
		
		@PostMapping
		public void loginUser(@ModelAttribute("user") 
		UserLoginDTO userLoginDTO) {
			System.out.println("UserDTO"+userLoginDTO);
			 userService.loadUserByUsername(userLoginDTO.getUsername());
		}
		@GetMapping("/otpVerification")
		public String otpSent(Model model,UserLoginDTO userLoginDTO) {
			model.addAttribute("otpValue", userLoginDTO);
			return "otpScreen";
			
		}
		@PostMapping("/otpVerification")
		public String otpVerification(@ModelAttribute("otpValue") UserLoginDTO userLoginDTO) {
			SecurityContext securityContext = SecurityContextHolder.getContext();
			UserDetails user = (UserDetails) securityContext.getAuthentication().getPrincipal();
			User users = userRepository.findByEmail(user.getUsername());
			if(users.getOtp() == userLoginDTO.getOtp()) {
				users.setActive(true);
			userRepository.save(users);
			return "redirect:/dashboard";
			}
			else
				return "redirect:/login/otpVerification?error";
		}
	
	
}
