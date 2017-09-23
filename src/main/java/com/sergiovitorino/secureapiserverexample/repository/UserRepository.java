package com.sergiovitorino.secureapiserverexample.repository;

import org.springframework.data.repository.CrudRepository;

import com.sergiovitorino.secureapiserverexample.model.User;

public interface UserRepository extends CrudRepository<User, String>{

	User findByUsername(String username);
	
	User findByAccessToken(String accessToken);
	
}
