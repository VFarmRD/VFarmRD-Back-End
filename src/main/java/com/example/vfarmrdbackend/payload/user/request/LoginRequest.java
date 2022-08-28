package com.example.vfarmrdbackend.payload.user;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
	@NotBlank
	private String user_name;

	@NotBlank
	private String password;

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
