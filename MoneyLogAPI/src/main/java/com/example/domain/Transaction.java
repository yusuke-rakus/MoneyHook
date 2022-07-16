package com.example.domain;

import java.sql.Date;

public class Transaction {

	private Long transactionId;
	private Long userNo;
	private String transactionName;
	private Integer amount;
	private Date transactionDate;
	private Long categoryId;
	private Long subCategoryId;
	private boolean fixedFlg;

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

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
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

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", userNo=" + userNo + ", transactionName="
				+ transactionName + ", amount=" + amount + ", transactionDate=" + transactionDate + ", categoryId="
				+ categoryId + ", subCategoryId=" + subCategoryId + ", fixedFlg=" + fixedFlg + "]";
	}

}
