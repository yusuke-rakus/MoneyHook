package com.example.form;

import com.example.common.message.ValidatingMessage;

import javax.validation.constraints.NotNull;
import java.sql.Date;

public class GetTotalSpendingForm extends form {

	@NotNull(message = ValidatingMessage.CATEGORY_NOT_SELECT_ERROR)
	private Long categoryId;
	private Long subCategoryId;
	@NotNull(message = ValidatingMessage.START_MONTH_NOT_INPUT_ERROR)
	private Date startMonth;
	@NotNull(message = ValidatingMessage.END_MONTH_NOT_INPUT_ERROR)
	private Date endMonth;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public Date getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(Date startMonth) {
		this.startMonth = startMonth;
	}

	public Date getEndMonth() {
		return endMonth;
	}

	public void setEndMonth(Date endMonth) {
		this.endMonth = endMonth;
	}

}
