package com.example.domain;

import java.util.UUID;

public class User {
	
	private Long userNo;
	private UUID userId;
	private String email;
	private String password;
	private Integer themeColorId;

	public Long getUserNo() {
		return userNo;
	}

	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

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

	public Integer getThemeColorId() {
		return themeColorId;
	}

	public void setThemeColorId(Integer themeColorId) {
		this.themeColorId = themeColorId;
	}

	@Override
	public String toString() {
		return "User [userNo=" + userNo + ", userId=" + userId + ", email=" + email + ", password=" + password
				+ ", themeColorId=" + themeColorId + "]";
	}

}
