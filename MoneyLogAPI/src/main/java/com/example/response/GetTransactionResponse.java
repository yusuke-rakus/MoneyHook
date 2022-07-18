package com.example.response;

import com.example.domain.Transaction;

public class GetTransactionResponse extends response {

	Transaction transaction;

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

}
