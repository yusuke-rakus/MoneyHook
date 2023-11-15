package com.example.form;

import javax.validation.constraints.NotNull;

public class DeleteSavingTargetForm extends form {

	@NotNull(message = "{validating-message.id-empty-error}")
	private Long savingTargetId;

	public Long getSavingTargetId() {
		return savingTargetId;
	}

	public void setSavingTargetId(Long savingTargetId) {
		this.savingTargetId = savingTargetId;
	}
}
