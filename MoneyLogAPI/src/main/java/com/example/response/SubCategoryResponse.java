package com.example.response;

import java.util.List;

import com.example.domain.SubCategory;

public class SubCategoryResponse extends response {

	private List<SubCategory> subCategoryList;

	public List<SubCategory> getSubCategoryList() {
		return subCategoryList;
	}

	public void setSubCategoryList(List<SubCategory> subCategoryList) {
		this.subCategoryList = subCategoryList;
	}

}
