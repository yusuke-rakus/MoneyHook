package com.example.form;

import com.example.common.validation.AnyOneNotEmpty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@AnyOneNotEmpty(fields = {"savingTargetName", "targetAmount"}, message = "{validating-message.both-of-name-and" +
		"-target-amount-empty-error}")
public class EditSavingTargetForm extends form {

	@NotNull(message = "{validating-message.id-empty-error}")
	private Long savingTargetId;

	@NotBlank(message = "{validating-message.saving-target-name-empty-error}")
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
}
