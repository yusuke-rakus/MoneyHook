package com.example.form;

import com.example.common.message.ValidatingMessage;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class GoogleSignInForm extends form {

	@NotBlank(message = ValidatingMessage.USER_ID_NOT_FOUND)
	@Size(min = 64, max = 64, message = ValidatingMessage.USER_ID_NOT_FOUND)
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
