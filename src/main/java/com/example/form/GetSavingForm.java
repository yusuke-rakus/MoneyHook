package com.example.form;

import javax.validation.constraints.NotNull;

public class GetSavingForm extends form {

	@NotNull(message = "{validating-message.id-empty-error}")
	private Integer savingId;

	public Integer getSavingId() {
		return savingId;
	}

	public void setSavingId(Integer savingId) {
		this.savingId = savingId;
	}

}
