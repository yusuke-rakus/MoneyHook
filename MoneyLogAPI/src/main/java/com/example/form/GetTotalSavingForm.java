package com.example.form;

import java.sql.Date;

import javax.validation.constraints.NotNull;

import com.example.common.message.ValidatingMessage;

public class GetTotalSavingForm extends form {
	@NotNull(message = ValidatingMessage.DATE_EMPTY_ERROR)
	private Date month;

	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		this.month = month;
	}

	@Override
	public String toString() {
		return "GetTotalSavingForm [month=" + month + "]";
	}

}