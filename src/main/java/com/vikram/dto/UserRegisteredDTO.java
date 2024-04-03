package com.vikram.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisteredDTO {

    private String name;
    
	private String email_id;
	
	private String password;
	
	private String role;
	
	
}
