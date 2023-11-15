package com.example.form;

import javax.validation.constraints.NotNull;

public class DeleteSavingForm extends form {

	@NotNull(message = "{validating-message.id-empty-error}")
	private Long savingId;

	public Long getSavingId() {
		return savingId;
	}

	public void setSavingId(Long savingId) {
		this.savingId = savingId;
	}
}
