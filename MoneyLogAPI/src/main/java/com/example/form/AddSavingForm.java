package com.example.form;

import java.sql.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.common.message.ValidatingMessage;
import com.example.common.validation.AnyOneNotEmpty;

public class AddSavingForm extends form {

	@NotEmpty(message = ValidatingMessage.SAVING_NAME_EMPTY_ERROR)
	private String savingName;

	@NotNull(message = ValidatingMessage.SAVING_AMOUNT_EMPTY_ERROR)
	private Integer savingAmount;

	@NotNull(message = ValidatingMessage.DATE_EMPTY_ERROR)
	private Date savingDate;

	private Long savingTargetId;

	public Date getSavingDate() {
		return savingDate;
	}

	public void setSavingDate(Date savingDate) {
		this.savingDate = savingDate;
	}

	public Integer getSavingAmount() {
		return savingAmount;
	}

	public void setSavingAmount(Integer savingAmount) {
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

	@Override
	public String toString() {
		return "AddSavingForm [savingName=" + savingName + ", savingAmount=" + savingAmount + ", savingDate="
				+ savingDate + ", savingTargetId=" + savingTargetId + "]";
	}

}