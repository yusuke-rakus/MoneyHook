package com.example.form;

import java.sql.Date;

public class GetMonthlySpendingDataForm extends form {

	private Long userNo;
	private Date month;

	public Long getUserNo() {
		return userNo;
	}

	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}

	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		this.month = month;
	}

}
