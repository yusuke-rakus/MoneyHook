package com.example.form;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class ChangePasswordForm extends form {

	private String password;

	@NotBlank(message = "{validating-message.password-empty-error}")
	@Length(max = 32, min = 8, message = "{validating-message.password-range-error}")
	private String newPassword;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
