package com.example.form;

import javax.validation.constraints.NotNull;

import com.example.common.message.ValidatingMessage;

public class GetSavingForm extends form {

	@NotNull(message = ValidatingMessage.ID_EMPTY_ERROR)
	private Integer savingId;

	public Integer getSavingId() {
		return savingId;
	}

	public void setSavingId(Integer savingId) {
		this.savingId = savingId;
	}

}
