package com.example.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.example.common.message.ValidatingMessage;

public class RegistUserForm extends form {

	@NotBlank(message = ValidatingMessage.EMAIL_EMPTY_ERROR)
	@Email(message = ValidatingMessage.EMAIL_EMPTY_ERROR)
	@Length(max = 128, message = ValidatingMessage.EMAIL_OVER_LIMIT_ERROR)
	private String email;
	
	@NotBlank(message = ValidatingMessage.PASSWORD_EMPTY_ERROR)
	@Length(max = 32, min = 8, message = ValidatingMessage.PASSWORD_RANGE_ERROR)
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

	@Override
	public String toString() {
		return "RegistUserForm [email=" + email + ", password=" + password + "]";
	}

}
