package com.sergiovitorino.secureapiserverexample.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class User implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	@NotNull
	@Column(unique = true)
	private String username;
	
	@NotNull
	private String password;
	
	@NotNull
	private Calendar lastUpdateAt;
	
	@NotNull
	@Column(unique = true)
	private String accessToken;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Calendar getLastUpdateAt() {
		return lastUpdateAt;
	}

	public void setLastUpdateAt(Calendar lastUpdateAt) {
		this.lastUpdateAt = lastUpdateAt;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
}
