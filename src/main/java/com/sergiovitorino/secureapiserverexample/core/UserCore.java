package com.sergiovitorino.secureapiserverexample.core;

import java.util.Calendar;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sergiovitorino.secureapiserverexample.cript.AccessTokenManager;
import com.sergiovitorino.secureapiserverexample.cript.PasswordEncrypter;
import com.sergiovitorino.secureapiserverexample.model.User;
import com.sergiovitorino.secureapiserverexample.repository.UserRepository;

@Component
public class UserCore {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PasswordEncrypter passwordEncrypter;
	
	@Autowired
	private AccessTokenManager accessTokenManager;
	
	@Value("${access.token.inactive}")
	private Long inactive;
	
	
	public User signUp(User user) {
		if(repository.findByUsername(user.getUsername()) != null) {
			throw new IllegalArgumentException("Username has used");
		}
		user.setId(UUID.randomUUID().toString());
		user.setPassword(passwordEncrypter.encrypt(user.getPassword()));
		user.setLastUpdateAt(Calendar.getInstance());
		user.setAccessToken(accessTokenManager.build(user.getId()));
		repository.save(user);
		return user;
	}
	
	public User find(String id) {
		return repository.findOne(id);
	}
	
	public User signIn(String username, String password) throws IllegalArgumentException {
		User user = repository.findByUsername(username);
		if(user == null) {
			throw new IllegalArgumentException("User not found");
		}
		if(!user.getPassword().equals(passwordEncrypter.encrypt(password))) {
			throw new IllegalArgumentException("Invalid Credentials");
		}
		user.setAccessToken(accessTokenManager.build(user.getId()));
		user.setLastUpdateAt(Calendar.getInstance());
		repository.save(user);
		return user;
	}
	
	public void validate(String accessToken, String userId) throws IllegalArgumentException{
		User user = repository.findByAccessToken(accessToken);
		if (user == null || !user.getId().equals(userId) || !accessTokenManager.verify(accessToken, userId)) {
			throw new IllegalArgumentException("Unauthorized");
		}
		Calendar maxInactiveDate = Calendar.getInstance();
		maxInactiveDate.setTimeInMillis(Calendar.getInstance().getTimeInMillis() - inactive);
		if(user.getLastUpdateAt().before(maxInactiveDate)) {
			throw new IllegalArgumentException("Invalid Session");
		}
		user.setLastUpdateAt(Calendar.getInstance());
		repository.save(user);
	}
}
