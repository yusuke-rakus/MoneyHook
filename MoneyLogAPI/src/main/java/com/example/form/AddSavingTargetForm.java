package com.example.form;

import javax.validation.constraints.NotNull;

import com.example.common.message.ValidatingMessage;
import com.example.common.validation.AnyOneNotEmpty;

@AnyOneNotEmpty(fields = {"savingTargetId","savingTargetName"})
public class AddSavingTargetForm extends form {

	private Long savingTargetId;
	private String savingTargetName;
	
	@NotNull(message = ValidatingMessage.SAVING_TARGET_AMOUNT_EMPTY_ERROR)
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

	@Override
	public String toString() {
		return "AddSavingTargetForm [savingTargetId=" + savingTargetId + ", savingTargetName=" + savingTargetName
				+ ", targetAmount=" + targetAmount + "]";
	}

}
