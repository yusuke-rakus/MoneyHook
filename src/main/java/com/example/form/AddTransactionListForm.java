package com.example.form;

import java.util.List;

import com.example.domain.Transaction;

public class AddTransactionListForm extends form {

	private List<Transaction> transactionList;

	public List<Transaction> getTransactionList() {
		return transactionList;
	}

	public void setTransactionList(List<Transaction> transactionList) {
		this.transactionList = transactionList;
	}

}
