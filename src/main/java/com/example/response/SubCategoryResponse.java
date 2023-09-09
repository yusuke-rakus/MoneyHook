package com.example.response;

import java.util.List;

import com.example.domain.SubCategory;
import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SubCategoryResponse extends response {

	private List<SubCategory> subCategoryList;

	public List<SubCategory> getSubCategoryList() {
		return subCategoryList;
	}

	public void setSubCategoryList(List<SubCategory> subCategoryList) {
		this.subCategoryList = subCategoryList;
	}

}
