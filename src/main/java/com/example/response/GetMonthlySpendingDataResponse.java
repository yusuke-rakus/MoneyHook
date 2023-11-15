package com.example.response;

import com.example.domain.Transaction;

import java.util.List;

public class GetMonthlySpendingDataResponse extends response {

	private List<Transaction> monthlyTotalAmountList;

	public List<Transaction> getMonthlyTotalAmountList() {
		return monthlyTotalAmountList;
	}

	public void setMonthlyTotalAmountList(List<Transaction> monthlyTotalAmountList) {
		this.monthlyTotalAmountList = monthlyTotalAmountList;
	}

}
