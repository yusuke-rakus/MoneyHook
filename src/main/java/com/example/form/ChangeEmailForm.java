package com.example.form;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class ChangeEmailForm extends form {

	@NotBlank(message = "{validating-message.email-empty-error}")
	@Email(message = "{validating-message.email-empty-error}")
	@Length(max = 128, message = "{validating-message.email-over-limit-error}")
	private String email;

	@NotBlank(message = "{validating-message.password-empty-error}")
	@Length(max = 32, min = 8, message = "{validating-message.password-range-error}")
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
