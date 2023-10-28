package com.example.response;

import java.util.List;

import com.example.domain.MonthlyTransaction;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GetFixedResponse extends response {

	private List<MonthlyTransaction> monthlyTransactionList;

	public List<MonthlyTransaction> getMonthlyTransactionList() {
		return monthlyTransactionList;
	}

	public void setMonthlyTransactionList(List<MonthlyTransaction> monthlyTransactionList) {
		this.monthlyTransactionList = monthlyTransactionList;
	}

}
