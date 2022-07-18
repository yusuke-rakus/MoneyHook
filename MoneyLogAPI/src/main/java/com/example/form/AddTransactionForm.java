package com.example.form;

import java.sql.Date;

public class AddTransactionForm extends form {

	private Long userNo;
	private Date transactionDate;
	private Integer transactionAmount;
	private String transactionName;
	private Long categoryId;
	private Long subCategoryId;
	private String subCategoryName;
	private boolean fixedFlg;

	public Long getUserNo() {
		return userNo;
	}

	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Integer getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Integer amount) {
		this.transactionAmount = amount;
	}

	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public boolean isFixedFlg() {
		return fixedFlg;
	}

	public void setFixedFlg(boolean fixedFlg) {
		this.fixedFlg = fixedFlg;
	}

	@Override
	public String toString() {
		return "AddTransactionForm [userNo=" + userNo + ", transactionDate=" + transactionDate + ", amount=" + transactionAmount
				+ ", transactionName=" + transactionName + ", categoryId=" + categoryId + ", subCategoryId="
				+ subCategoryId + ", subCategoryName=" + subCategoryName + ", fixedFlg=" + fixedFlg + "]";
	}

}
