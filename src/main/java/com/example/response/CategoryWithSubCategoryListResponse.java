package com.example.response;

import com.example.domain.Category;

import java.util.List;

public class CategoryWithSubCategoryListResponse extends response {

	private List<Category> categoryList;

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}
}
