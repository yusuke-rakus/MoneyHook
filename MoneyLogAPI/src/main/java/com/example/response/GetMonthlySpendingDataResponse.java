package com.example.response;

import java.util.List;

import com.example.domain.MonthlyTransaction;
import com.example.domain.Transaction;

public class GetMonthlySpendingDataResponse extends response {

	private List<Transaction> monthlyTotalAmountList;

	public List<Transaction> getMonthlyTotalAmountList() {
		return monthlyTotalAmountList;
	}

	public void setMonthlyTotalAmountList(List<Transaction> monthlyTotalAmountList) {
		this.monthlyTotalAmountList = monthlyTotalAmountList;
	}

}
