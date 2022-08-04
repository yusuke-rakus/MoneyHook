package com.example.form;

import java.util.List;

import javax.validation.Valid;

public class EditFixedForm extends form {

	private List<@Valid MonthlyTransactionList> monthlyTransactionList;

	public List<MonthlyTransactionList> getMonthlyTransactionList() {
		return monthlyTransactionList;
	}

	public void setMonthlyTransactionList(List<MonthlyTransactionList> monthlyTransactionList) {
		this.monthlyTransactionList = monthlyTransactionList;
	}

}
