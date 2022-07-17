package com.example.response;

import com.example.domain.User;

public class RegistUserResponse extends response {

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "RegisterUserResponse [user=" + user + "]";
	}

}
