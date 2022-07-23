package com.example.form;

import java.util.List;

import com.example.domain.MonthlyTransaction;

public class EditFixedForm extends form {

	private List<MonthlyTransaction> monthlyTransactionList;

	public List<MonthlyTransaction> getMonthlyTransactionList() {
		return monthlyTransactionList;
	}

	public void setMonthlyTransactionList(List<MonthlyTransaction> monthlyTransactionList) {
		this.monthlyTransactionList = monthlyTransactionList;
	}

}
