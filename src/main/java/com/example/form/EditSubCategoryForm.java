package com.example.form;

import javax.validation.constraints.NotNull;

public class EditSubCategoryForm extends form {

	@NotNull(message = "{validating-message.validating-error}")
	private Long subCategoryId;
	@NotNull(message = "{validating-message.validating-error}")
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
