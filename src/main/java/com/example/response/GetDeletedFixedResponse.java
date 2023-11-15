package com.example.response;

import com.example.domain.MonthlyTransaction;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GetDeletedFixedResponse extends response {

	private List<MonthlyTransaction> monthlyTransactionList;

	public List<MonthlyTransaction> getMonthlyTransactionList() {
		return monthlyTransactionList;
	}

	public void setMonthlyTransactionList(List<MonthlyTransaction> monthlyTransactionList) {
		this.monthlyTransactionList = monthlyTransactionList;
	}

}
