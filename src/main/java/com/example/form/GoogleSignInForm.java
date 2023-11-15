package com.example.form;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class GoogleSignInForm extends form {

	@NotBlank(message = "{validating-message.user-id-not-found}")
	@Size(min = 64, max = 64, message = "{validating-message.user-id-not-found}")
	private String userId;

	private String token;

	@Override
	public String getUserId() {
		return userId;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
