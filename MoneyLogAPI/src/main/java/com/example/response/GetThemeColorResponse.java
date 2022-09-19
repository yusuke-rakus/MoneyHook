package com.example.response;

import java.util.List;

import com.example.domain.User;

public class GetThemeColorResponse extends response {
	private List<User> themeColorList;

	public List<User> getThemeColorList() {
		return themeColorList;
	}

	public void setThemeColorList(List<User> themeColorList) {
		this.themeColorList = themeColorList;
	}

}
