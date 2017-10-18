package com.sergiovitorino.secureapiserverexample.crypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component
public class PasswordEncrypter {
	
	public String encrypt(String password) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] passwordBytes = password.getBytes();
			messageDigest.reset();
	        byte[] digested = messageDigest.digest(passwordBytes);
	        StringBuffer sb = new StringBuffer();
	        for(int i=0;i<digested.length;i++){
	            sb.append(Integer.toHexString(0xff & digested[i]));
	        }
	        return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}


}
