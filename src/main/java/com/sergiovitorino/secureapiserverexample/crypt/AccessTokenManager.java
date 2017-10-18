package com.sergiovitorino.secureapiserverexample.crypt;

import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class AccessTokenManager {
	
	@Value("${access.token.secret}")
	private String secret;
	
	@Value("${access.token.expire}")
	private Long expire;

	public String build(String userId) {
		try {
			Algorithm algorithmHS = Algorithm.HMAC512(secret + userId);
			Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(43200).toInstant());
			String token = JWT.create().withExpiresAt(expirationDate).withClaim("userId", userId).sign(algorithmHS);
			return token;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Boolean verify(String token, String userId) {
		try {
			Algorithm algorithm = Algorithm.HMAC512(secret + userId);
			JWTVerifier verifier = JWT.require(algorithm).withClaim("userId", userId).build(); // Reusable verifier																								// instance
			verifier.verify(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}


}
