package com.example.domain;

import java.sql.Date;

public class MonthlySavingData {
	private Integer monthlyTotalSavingAmount;
	private Date savingMonth;

	public MonthlySavingData() {
		super();
	}

	public MonthlySavingData(Date savingMonth) {
		super();
		this.savingMonth = savingMonth;
	}

	public Integer getMonthlyTotalSavingAmount() {
		return monthlyTotalSavingAmount;
	}

	public void setMonthlyTotalSavingAmount(Integer monthlyTotalSavingAmount) {
		this.monthlyTotalSavingAmount = monthlyTotalSavingAmount;
	}

	public Date getSavingMonth() {
		return savingMonth;
	}

	public void setSavingMonth(Date savingMonth) {
		this.savingMonth = savingMonth;
	}

}
