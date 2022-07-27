package com.example.form;

import java.sql.Date;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.example.common.message.ValidatingMessage;

public class AddTransactionForm extends form {

	@NotBlank(message = ValidatingMessage.TRANSACTION_DATE_EMPTY_ERROR)
	private Date transactionDate;

	@NotBlank(message = ValidatingMessage.TRANSACTION_AMOUNT_EMPTY_ERROR)
	private Integer transactionAmount;

	@NotBlank(message = ValidatingMessage.TRANSACTION_NAME_EMPTY_ERROR)
	@Length(max = 32, message = ValidatingMessage.TRANSACTION_NAME_LIMIT_ERROR)
	private String transactionName;

	@NotBlank(message = ValidatingMessage.CATEGORY_NOT_SELECT_ERROR)
	private Long categoryId;

	private Long subCategoryId;

	private String subCategoryName;

	private boolean fixedFlg;

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

}
