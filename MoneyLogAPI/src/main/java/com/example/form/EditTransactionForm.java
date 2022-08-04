package com.example.form;

import java.sql.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.example.common.message.ValidatingMessage;

public class EditTransactionForm extends form {

	private Long transactionId;

	@NotNull(message = ValidatingMessage.TRANSACTION_DATE_EMPTY_ERROR)
	private Date transactionDate;

	@NotNull(message = ValidatingMessage.TRANSACTION_AMOUNT_EMPTY_ERROR)
	private Integer transactionAmount;

	@NotBlank(message = ValidatingMessage.TRANSACTION_NAME_EMPTY_ERROR)
	@Length(max = 32, message = ValidatingMessage.TRANSACTION_NAME_LIMIT_ERROR)
	private String transactionName;

	private Long categoryId;

	private Long subCategoryId;

	private String subCategoryName;

	private boolean fixedFlg;

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
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

	public void setTransactionAmount(Integer transactionAmount) {
		this.transactionAmount = transactionAmount;
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
