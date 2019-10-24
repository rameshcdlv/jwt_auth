package com.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserPayload {
	
	private String name;
	private String password;
	private String email;
	private boolean isAdmin;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@JsonProperty("isAdmin")
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	

}
