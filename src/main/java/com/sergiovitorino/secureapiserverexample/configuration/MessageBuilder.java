package com.sergiovitorino.secureapiserverexample.configuration;

import org.springframework.stereotype.Component;

@Component
public class MessageBuilder {

	public String build(String text) {
		return "{\"message\":\"" + text + "\"}";
	}
	
}
