package com.example.domain;

import java.util.List;

public class CategoryList {

	private String categoryName;
	private Integer categoryTotalAmount;
	private List<Transaction> subCategoryList;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getCategoryTotalAmount() {
		return categoryTotalAmount;
	}

	public void setCategoryTotalAmount(Integer categoryTotalAmount) {
		this.categoryTotalAmount = categoryTotalAmount;
	}

	public List<Transaction> getSubCategoryList() {
		return subCategoryList;
	}

	public void setSubCategoryList(List<Transaction> subCategoryList) {
		this.subCategoryList = subCategoryList;
	}

	public CategoryList() {
		super();
	}

	public CategoryList(String categoryName, Integer categoryTotalAmount, List<Transaction> subCategoryList) {
		super();
		this.categoryName = categoryName;
		this.categoryTotalAmount = categoryTotalAmount;
		this.subCategoryList = subCategoryList;
	}
}
