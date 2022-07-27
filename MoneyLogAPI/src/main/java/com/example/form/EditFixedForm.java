package com.example.form;

import java.util.List;

import com.example.domain.MonthlyTransaction;

public class EditFixedForm extends form {

	private List<MonthlyTransactionList> monthlyTransactionList;

	public List<MonthlyTransactionList> getMonthlyTransactionList() {
		return monthlyTransactionList;
	}

	public void setMonthlyTransactionList(List<MonthlyTransactionList> monthlyTransactionList) {
		this.monthlyTransactionList = monthlyTransactionList;
	}

}
