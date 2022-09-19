package com.example.response;

import java.util.List;

import com.example.domain.Transaction;

public class FrequentTransactionNameResponse extends response {
	private List<Transaction> transactionList;

	public List<Transaction> getTransactionList() {
		return transactionList;
	}

	public void setTransactionList(List<Transaction> transactionList) {
		this.transactionList = transactionList;
	}

}
