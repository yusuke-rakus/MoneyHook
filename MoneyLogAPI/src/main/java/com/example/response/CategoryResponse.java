package com.example.response;

import java.util.List;

import com.example.domain.Category;

public class CategoryResponse extends response {

	private List<Category> categoryList;

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

}
