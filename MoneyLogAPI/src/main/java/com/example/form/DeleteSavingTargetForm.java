package com.example.form;

import javax.validation.constraints.NotNull;

import com.example.common.message.ValidatingMessage;

public class DeleteSavingTargetForm extends form {

	@NotNull(message = ValidatingMessage.ID_EMPTY_ERROR)
	private Integer savingTargetId;

	public Integer getSavingTargetId() {
		return savingTargetId;
	}

	public void setSavingTargetId(Integer savingTargetId) {
		this.savingTargetId = savingTargetId;
	}

}
