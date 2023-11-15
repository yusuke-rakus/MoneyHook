package com.example.form;

import javax.validation.constraints.NotNull;

public class DeleteTransactionForm extends form {

	@NotNull(message = "{validating-message.transaction-id-not-select-error}")
	private Long transactionId;

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

}
