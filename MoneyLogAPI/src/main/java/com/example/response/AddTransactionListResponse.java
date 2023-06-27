package com.example.response;

import com.example.domain.Transaction;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AddTransactionListResponse extends response {

	private List<Transaction> errorTransaction;

	public List<Transaction> getErrorTransaction() {
		return errorTransaction;
	}

	public void setErrorTransaction(List<Transaction> errorTransaction) {
		this.errorTransaction = errorTransaction;
	}
}
