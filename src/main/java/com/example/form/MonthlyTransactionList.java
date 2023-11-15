package com.example.form;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class MonthlyTransactionList {

	private Long userNo;

	private Long monthlyTransactionId;

	@NotBlank(message = "{validating-message.transaction-name-empty-error}")
	@Length(max = 32, message = "{validating-message.transaction-name-limit-error}")
	private String monthlyTransactionName;

	@NotBlank(message = "{validating-message.transaction-amount-empty-error}")
	private Integer monthlyTransactionAmount;

	private Integer monthlyTransactionSign;

	@NotBlank(message = "{validating-message.date-empty-error}")
	private Integer monthlyTransactionDate;

	private Long categoryId;

	private Long subCategoryId;

	private String subCategoryName;

	public Long getUserNo() {
		return userNo;
	}

	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}

	public Long getMonthlyTransactionId() {
		return monthlyTransactionId;
	}

	public void setMonthlyTransactionId(Long monthlyTransactionId) {
		this.monthlyTransactionId = monthlyTransactionId;
	}

	public String getMonthlyTransactionName() {
		return monthlyTransactionName;
	}

	public void setMonthlyTransactionName(String monthlyTransactionName) {
		this.monthlyTransactionName = monthlyTransactionName;
	}

	public Integer getMonthlyTransactionAmount() {
		return monthlyTransactionAmount;
	}

	public void setMonthlyTransactionAmount(Integer monthlyTransactionAmount) {
		this.monthlyTransactionAmount = monthlyTransactionAmount;
	}

	public Integer getMonthlyTransactionSign() {
		return monthlyTransactionSign;
	}

	public void setMonthlyTransactionSign(Integer monthlyTransactionSign) {
		this.monthlyTransactionSign = monthlyTransactionSign;
	}

	public Integer getMonthlyTransactionDate() {
		return monthlyTransactionDate;
	}

	public void setMonthlyTransactionDate(Integer monthlyTransactionDate) {
		this.monthlyTransactionDate = monthlyTransactionDate;
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

}
