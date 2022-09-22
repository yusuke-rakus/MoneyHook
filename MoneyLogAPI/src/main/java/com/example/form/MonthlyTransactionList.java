package com.example.form;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.example.common.message.ValidatingMessage;

public class MonthlyTransactionList {

	private Long userNo;

	private Long monthlyTransactionId;

	@NotBlank(message = ValidatingMessage.TRANSACTION_NAME_EMPTY_ERROR)
	@Length(max = 32, message = ValidatingMessage.TRANSACTION_NAME_LIMIT_ERROR)
	private String monthlyTransactionName;

	@NotBlank(message = ValidatingMessage.TRANSACTION_AMOUNT_EMPTY_ERROR)
	private Integer monthlyTransactionAmount;

	private Integer monthlyTransactionSign;

	@NotBlank(message = ValidatingMessage.DATE_EMPTY_ERROR)
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
