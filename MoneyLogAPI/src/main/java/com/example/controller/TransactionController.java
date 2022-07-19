package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.common.exception.AuthenticationException;
import com.example.common.exception.SystemException;
import com.example.form.AddTransactionForm;
import com.example.form.DeleteTransactionForm;
import com.example.form.EditTransactionForm;
import com.example.form.GetMonthlySpendingDataForm;
import com.example.form.GetTransactionForm;
import com.example.response.AddTransactionResponse;
import com.example.response.DeleteTransactionResponse;
import com.example.response.EditTransactionResponse;
import com.example.response.GetMonthlySpendingDataResponse;
import com.example.response.GetTransactionResponse;
import com.example.service.TransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	/**
	 * 収支を登録
	 * 
	 * @throws AuthenticationException
	 */
	@PostMapping("/addTransaction")
	public AddTransactionResponse addTransaction(@RequestBody AddTransactionForm form) throws SystemException {
		return transactionService.addTransaction(form);
	}

	/**
	 * 収支を削除
	 * 
	 * @throws AuthenticationException
	 */
	@PostMapping("/deleteTransaction")
	public DeleteTransactionResponse deleteTransaction(@RequestBody DeleteTransactionForm form) throws SystemException {
		return transactionService.deleteTransaction(form);
	}

	/**
	 * 収支を編集
	 * 
	 * @throws AuthenticationException
	 */
	@PostMapping("/editTransaction")
	public EditTransactionResponse editTransaction(@RequestBody EditTransactionForm form) throws SystemException {
		return transactionService.editTransaction(form);
	}

	/**
	 * 収支詳細の取得
	 * 
	 * @throws AuthenticationException
	 */
	@PostMapping("/getTransaction")
	public GetTransactionResponse getTransaction(@RequestBody GetTransactionForm form) throws SystemException {
		return transactionService.getTransaction(form);
	}

	/**
	 * ６ヶ月分の合計支出を取得
	 * 
	 * @throws AuthenticationException
	 */
	@PostMapping("/getMonthlySpendingData")
	public GetMonthlySpendingDataResponse getMonthlySpendingData(@RequestBody GetMonthlySpendingDataForm form)
			throws SystemException {
		return transactionService.getMonthlySpendingData(form);
	}
}
