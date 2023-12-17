package com.example.form;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.sql.Date;

public class AddSavingForm extends form {

	@NotEmpty(message = "{validating-message.saving-name-empty-error}")
	@Length(max = 32, message = "{validating-message.saving-name-length-error}")
	private String savingName;

	@NotNull(message = "{validating-message.saving-amount-empty-error}")
	@Max(value = 9999999, message = "{validating-message.saving-amount-range-error}")
	private BigInteger savingAmount;

	@NotNull(message = "{validating-message.date-empty-error}")
	private Date savingDate;

	private Long savingTargetId;

	public Date getSavingDate() {
		return savingDate;
	}

	public void setSavingDate(Date savingDate) {
		this.savingDate = savingDate;
	}

	public BigInteger getSavingAmount() {
		return savingAmount;
	}

	public void setSavingAmount(BigInteger savingAmount) {
		this.savingAmount = savingAmount;
	}

	public String getSavingName() {
		return savingName;
	}

	public void setSavingName(String savingName) {
		this.savingName = savingName;
	}

	public Long getSavingTargetId() {
		return savingTargetId;
	}

	public void setSavingTargetId(Long savingTargetId) {
		this.savingTargetId = savingTargetId;
	}

}