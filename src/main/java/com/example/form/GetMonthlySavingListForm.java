package com.example.form;

import javax.validation.constraints.NotNull;
import java.sql.Date;

public class GetMonthlySavingListForm extends form {

	@NotNull(message = "{validating-message.date-empty-error}")
	private Date month;

	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		this.month = month;
	}

}
