package com.example.form;

import javax.validation.constraints.NotNull;

import com.example.common.message.ValidatingMessage;
import com.example.common.validation.AnyOneNotEmpty;

@AnyOneNotEmpty(fields = { "savingTargetName",
		"targetAmount" }, message = ValidatingMessage.BOTH_OF_NAME_AND_TARGET_AMOUNT_EMPTY_ERROR)
public class EditSavingTargetForm extends form {

	@NotNull(message = ValidatingMessage.ID_EMPTY_ERROR)
	private Long savingTargetId;

	private String savingTargetName;

	private Integer targetAmount;

	public Long getSavingTargetId() {
		return savingTargetId;
	}

	public void setSavingTargetId(Long savingTargetId) {
		this.savingTargetId = savingTargetId;
	}

	public String getSavingTargetName() {
		return savingTargetName;
	}

	public void setSavingTargetName(String savingTargetName) {
		this.savingTargetName = savingTargetName;
	}

	public Integer getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(Integer targetAmount) {
		this.targetAmount = targetAmount;
	}

}
