package com.example.form;

import com.example.common.message.ValidatingMessage;

import javax.validation.constraints.NotNull;

public class DeleteTransactionForm extends form {

	@NotNull(message = ValidatingMessage.TRANSACTION_ID_NOT_SELECT_ERROR)
	private Long transactionId;

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

}
