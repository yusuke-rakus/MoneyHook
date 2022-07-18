package com.example.response;

import com.example.domain.User;

public class GetUserInfoResponse extends response {

	private User userInfo;

	public User getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(User userInfo) {
		this.userInfo = userInfo;
	}

}
