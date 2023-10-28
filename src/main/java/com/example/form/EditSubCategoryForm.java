package com.example.form;

import com.example.common.message.ValidatingMessage;

import javax.validation.constraints.NotNull;

public class EditSubCategoryForm extends form {

	@NotNull(message = ValidatingMessage.VALIDATING_ERROR)
	private Long subCategoryId;
	@NotNull(message = ValidatingMessage.VALIDATING_ERROR)
	private boolean enable;

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
}
