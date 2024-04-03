package com.vikram.service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vikram.dto.UserRegisteredDTO;
import com.vikram.model.Role;
import com.vikram.model.User;
import com.vikram.repository.RoleRepository;
import com.vikram.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	JavaMailSender javaMailSender;
	

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));		
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
		
	}

	@Override
	public User save(UserRegisteredDTO userRegisteredDTO) {
		
Role role = roleRepository.findByRole("USER");
		
		User user = new User();
		user.setEmail(userRegisteredDTO.getEmail_id());
		user.setName(userRegisteredDTO.getName());
		user.setPassword(passwordEncoder.encode(userRegisteredDTO.getPassword()));
		user.setRoles(role);
		
		return userRepository.save(user);
		
	}

	@Override
	public String generateOtp(User user) {
		
		try {
			int randomPIN = (int) (Math.random() * 9000) + 1000;
			user.setOtp(randomPIN);
			user.setActive(false);
			userRepository.save(user);
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setFrom("vikram5code@gmail.com");// input the senders email ID 
			msg.setTo(user.getEmail());

			msg.setSubject("Welcome "+user.getName());
			msg.setText("Please do not share your OTP with anyone for security reasons. Keep it safe! \n\n" +"Your Login OTP : " + randomPIN + ". Please Verify. \n\n"+"Regards \n"+"Vikram");

			javaMailSender.send(msg);
			
			return "success";
			}catch (Exception e) {
				e.printStackTrace();
				return "error";
			}
		
		
	}

}
