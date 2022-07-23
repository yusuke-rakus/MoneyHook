package com.example.response;

import java.util.List;

import com.example.domain.CategoryList;

public class GetHomeResponse extends response {

	private Integer balance;
	private List<CategoryList> categoryList;

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public List<CategoryList> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<CategoryList> categoryList) {
		this.categoryList = categoryList;
	}

}
