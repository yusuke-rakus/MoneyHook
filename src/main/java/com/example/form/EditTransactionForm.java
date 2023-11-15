package com.example.form;

import com.example.common.validation.AnyOneNotEmpty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.sql.Date;

@AnyOneNotEmpty(fields = {"subCategoryId", "subCategoryName"}, message =
		"{validating-message" + ".sub-category-no" + "-select-and-input-error}")
public class EditTransactionForm extends form {

	private Long transactionId;

	@NotNull(message = "{validating-message.date-empty-error}")
	private Date transactionDate;

	@NotNull(message = "{validating-message.transaction-amount-empty-error}")
	private BigInteger transactionAmount;

	@NotNull(message = "{validating-message.transaction-amount-empty-error}")
	private Integer transactionSign;

	@NotBlank(message = "{validating-message.transaction-name-empty-error}")
	@Length(max = 32, message = "{validating-message.transaction-name-limit-error}")
	private String transactionName;

	@NotNull(message = "{validating-message.category-not-select-error}")
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

	public BigInteger getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigInteger transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public Integer getTransactionSign() {
		return transactionSign;
	}

	public void setTransactionSign(Integer transactionSign) {
		this.transactionSign = transactionSign;
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
