package com.example.form;

public class ForgotPasswordResetForm extends form {

	private String email;

	private String password;

	private String resetPasswordParam;

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

	public String getResetPasswordParam() {
		return resetPasswordParam;
	}

	public void setResetPasswordParam(String resetPasswordParam) {
		this.resetPasswordParam = resetPasswordParam;
	}

}
