package com.example.domain;

import java.util.List;

public class MonthlyFixedList {

	private String categoryName;
	private Integer totalCategoryAmount;
	private List<Transaction> transactionList;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getTotalCategoryAmount() {
		return totalCategoryAmount;
	}

	public void setTotalCategoryAmount(Integer totalCategoryAmount) {
		this.totalCategoryAmount = totalCategoryAmount;
	}

	public List<Transaction> getTransactionList() {
		return transactionList;
	}

	public void setTransactionList(List<Transaction> transactionList) {
		this.transactionList = transactionList;
	}

}
