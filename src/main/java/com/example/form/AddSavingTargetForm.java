package com.example.form;

import com.example.common.validation.AnyOneNotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@AnyOneNotEmpty(fields = {"savingTargetId", "savingTargetName"}, message =
		"{validating-message" + ".both-of-id-and" + "-name-empty-error}")
public class AddSavingTargetForm extends form {

	private Long savingTargetId;
	private String savingTargetName;

	@NotNull(message = "{validating-message.saving-target-amount-empty-error}")
	private BigInteger targetAmount;

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

	public BigInteger getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(BigInteger targetAmount) {
		this.targetAmount = targetAmount;
	}

	@Override
	public String toString() {
		return "AddSavingTargetForm [savingTargetId=" + savingTargetId + ", savingTargetName=" + savingTargetName + ","
				+ " targetAmount=" + targetAmount + "]";
	}

}
