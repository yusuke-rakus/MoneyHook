package com.example.domain;

import java.sql.Date;

public class Transaction {

	private Long transactionId;
	private Long userNo;
	private String transactionName;
	private Integer transactionAmount;
	private Date transactionDate;
	private Long categoryId;
	private Long subCategoryId;
	private boolean fixedFlg;

	private String categoryName;
	private String subCategoryName;
	private Integer totalAmount;
	private String month;

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Long getUserNo() {
		return userNo;
	}

	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}

	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

	public Integer getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Integer amount) {
		this.transactionAmount = amount;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
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

	public boolean isFixedFlg() {
		return fixedFlg;
	}

	public void setFixedFlg(boolean fixedFlg) {
		this.fixedFlg = fixedFlg;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSubCategoryName() {
		return subCategoryName;
	}

	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", userNo=" + userNo + ", transactionName="
				+ transactionName + ", amount=" + transactionAmount + ", transactionDate=" + transactionDate
				+ ", categoryId=" + categoryId + ", subCategoryId=" + subCategoryId + ", fixedFlg=" + fixedFlg + "]";
	}

}
