package com.example.form;

import com.example.common.message.ValidatingMessage;
import com.example.common.validation.AnyOneNotEmpty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@AnyOneNotEmpty(fields = {"savingTargetName",
		"targetAmount"}, message = ValidatingMessage.BOTH_OF_NAME_AND_TARGET_AMOUNT_EMPTY_ERROR)
public class EditSavingTargetForm extends form {

	@NotNull(message = ValidatingMessage.ID_EMPTY_ERROR)
	private Long savingTargetId;

	@NotBlank(message = ValidatingMessage.SAVING_TARGET_NAME_EMPTY_ERROR)
	private String savingTargetName;

	@NotNull(message = ValidatingMessage.SAVING_TARGET_AMOUNT_EMPTY_ERROR)
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
