package com.example.domain;

public class SubCategory {

	private Long subCategoryId;
	private Long userNo;
	private Long categoryId;
	private String subCategoryName;

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public Long getUserNo() {
		return userNo;
	}

	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	@Override
	public String toString() {
		return "SubCategory [subCategoryId=" + subCategoryId + ", userNo=" + userNo + ", categoryId=" + categoryId
				+ ", subCategoryName=" + subCategoryName + "]";
	}

}
