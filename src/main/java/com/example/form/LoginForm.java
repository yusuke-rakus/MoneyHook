package com.example.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class LoginForm extends form {

	@NotBlank(message = "{validating-message.email-empty-error}")
	@Email(message = "{validating-message.email-empty-error}")
	private String email;

	@NotBlank(message = "{validating-message.password-empty-error}")
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
