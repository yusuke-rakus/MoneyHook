package com.example.form;

import com.example.domain.Transaction;

import java.util.List;

public class AddTransactionListForm extends form {

	private List<Transaction> transactionList;

	public List<Transaction> getTransactionList() {
		return transactionList;
	}

	public void setTransactionList(List<Transaction> transactionList) {
		this.transactionList = transactionList;
	}

}
