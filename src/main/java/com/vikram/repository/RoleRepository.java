package com.vikram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vikram.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

	
	Role findByRole(String name);
	
}
