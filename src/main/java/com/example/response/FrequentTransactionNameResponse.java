package com.example.response;

import com.example.domain.Transaction;

import java.util.List;

public class FrequentTransactionNameResponse extends response {
	private List<Transaction> transactionList;

	public List<Transaction> getTransactionList() {
		return transactionList;
	}

	public void setTransactionList(List<Transaction> transactionList) {
		this.transactionList = transactionList;
	}

}
