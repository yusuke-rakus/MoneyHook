package com.example.form;

import java.sql.Date;

import javax.validation.constraints.NotNull;

import com.example.common.message.ValidatingMessage;

public class EditSavingForm extends form {

	@NotNull(message = ValidatingMessage.ID_EMPTY_ERROR)
	private Long savingId;

	private String savingName;
	private Integer savingAmount;
	private Date savingDate;
	private Long savingTargetId;

	public Long getSavingId() {
		return savingId;
	}

	public void setSavingId(Long savingId) {
		this.savingId = savingId;
	}

	public String getSavingName() {
		return savingName;
	}

	public void setSavingName(String savingName) {
		this.savingName = savingName;
	}

	public Integer getSavingAmount() {
		return savingAmount;
	}

	public void setSavingAmount(Integer savingAmount) {
		this.savingAmount = savingAmount;
	}

	public Date getSavingDate() {
		return savingDate;
	}

	public void setSavingDate(Date savingDate) {
		this.savingDate = savingDate;
	}

	public Long getSavingTargetId() {
		return savingTargetId;
	}

	public void setSavingTargetId(Long savingTargetId) {
		this.savingTargetId = savingTargetId;
	}

	@Override
	public String toString() {
		return "EditSavingForm [savingId=" + savingId + ", savingName=" + savingName + ", savingAmount=" + savingAmount
				+ ", savingDate=" + savingDate + ", savingTargetId=" + savingTargetId + "]";
	}
}