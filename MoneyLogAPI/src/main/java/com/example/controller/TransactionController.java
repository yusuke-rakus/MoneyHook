package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.form.AddTransactionForm;
import com.example.response.AddTransactionResponse;
import com.example.service.TransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	/** 収支を登録 */
	@PostMapping("/addTransaction")
	public AddTransactionResponse addTransaction(@RequestBody AddTransactionForm form) {
		return transactionService.addTransaction(form);
	}

}
