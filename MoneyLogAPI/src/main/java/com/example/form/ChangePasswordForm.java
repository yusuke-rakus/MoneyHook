package com.example.form;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.example.common.message.ValidatingMessage;

public class ChangePasswordForm extends form {

	private String password;

	@NotBlank(message = ValidatingMessage.PASSWORD_EMPTY_ERROR)
	@Length(max = 32, min = 8, message = ValidatingMessage.PASSWORD_RANGE_ERROR)
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
