package com.example.response;

import java.util.List;

import com.example.domain.MonthlyTransaction;

public class GetFixedResponse extends response {

	private List<MonthlyTransaction> monthlyTransactionList;

	public List<MonthlyTransaction> getMonthlyTransactionList() {
		return monthlyTransactionList;
	}

	public void setMonthlyTransactionList(List<MonthlyTransaction> monthlyTransactionList) {
		this.monthlyTransactionList = monthlyTransactionList;
	}

}
