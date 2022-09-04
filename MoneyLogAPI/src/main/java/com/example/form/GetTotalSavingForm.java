package com.example.form;

import java.sql.Date;

public class GetTotalSavingForm extends form {
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
