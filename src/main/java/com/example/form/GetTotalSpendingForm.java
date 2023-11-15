package com.example.form;

import javax.validation.constraints.NotNull;
import java.sql.Date;

public class GetTotalSpendingForm extends form {

	@NotNull(message = "{validating-message.category-not-select-error}")
	private Long categoryId;
	private Long subCategoryId;
	@NotNull(message = "{validating-message.start-month-not-input-error}")
	private Date startMonth;
	@NotNull(message = "{validating-message.end-month-not-input-error}")
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
