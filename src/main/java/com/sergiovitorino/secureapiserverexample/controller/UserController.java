package com.sergiovitorino.secureapiserverexample.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sergiovitorino.secureapiserverexample.configuration.MessageBuilder;
import com.sergiovitorino.secureapiserverexample.core.UserCore;
import com.sergiovitorino.secureapiserverexample.model.User;

@RequestMapping("/api")
@RestController
public class UserController {
	
	@Autowired
	private UserCore core;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private MessageBuilder messageBuilder;

	@RequestMapping(value = { "user/{id}" }, method = RequestMethod.GET)
	public ResponseEntity<String> find(
			@RequestHeader("bearer") String token,
			@RequestHeader("userId") String userId,
			@PathVariable(required = true, value = "id") String id) {
		try {
			core.validate(token, userId);
			return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(core.find(id)));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageBuilder.build(e.getMessage()));
		}
	}

	@RequestMapping(value = { "user/sign-up" }, method = RequestMethod.POST)
	public ResponseEntity<String> signUp(@RequestBody User user) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(mapper.writeValueAsString(core.signUp(user)));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageBuilder.build(e.getMessage()));
		}
	}
	
	@RequestMapping(value = { "user/sign-in" }, method = RequestMethod.POST)
	public ResponseEntity<String> signIn(@RequestBody Map<String, String> json) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(mapper.writeValueAsString(core.signIn(json.get("username"), json.get("password"))));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageBuilder.build(e.getMessage()));
		}
	}
	
}
