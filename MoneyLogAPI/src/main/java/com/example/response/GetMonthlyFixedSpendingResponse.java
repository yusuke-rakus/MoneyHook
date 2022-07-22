package com.example.response;

import java.util.List;

import com.example.domain.MonthlyFixedList;

public class GetMonthlyFixedSpendingResponse extends response {

	private Integer disposableIncome;
	private List<MonthlyFixedList> monthlyFixedList;

	public Integer getDisposableIncome() {
		return disposableIncome;
	}

	public void setDisposableIncome(Integer disposableIncome) {
		this.disposableIncome = disposableIncome;
	}

	public List<MonthlyFixedList> getMonthlyFixedList() {
		return monthlyFixedList;
	}

	public void setMonthlyFixedList(List<MonthlyFixedList> monthlyFixedList) {
		this.monthlyFixedList = monthlyFixedList;
	}

}
