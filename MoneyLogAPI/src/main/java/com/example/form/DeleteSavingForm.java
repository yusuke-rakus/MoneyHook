package com.example.form;

import javax.validation.constraints.NotNull;

import com.example.common.message.ValidatingMessage;

public class DeleteSavingForm extends form {

	@NotNull(message = ValidatingMessage.ID_EMPTY_ERROR)
	private Long savingId;

	public Long getSavingId() {
		return savingId;
	}

	public void setSavingId(Long savingId) {
		this.savingId = savingId;
	}
}
