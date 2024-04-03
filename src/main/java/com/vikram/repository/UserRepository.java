package com.vikram.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vikram.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String emailId);
	
}
