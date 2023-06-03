package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.common.Status;
import com.example.common.exception.AuthenticationException;
import com.example.common.exception.SystemException;
import com.example.form.AddTransactionForm;
import com.example.form.AddTransactionListForm;
import com.example.form.DeleteTransactionForm;
import com.example.form.EditTransactionForm;
import com.example.form.FrequentTransactionNameForm;
import com.example.form.GetHomeForm;
import com.example.form.GetMonthlyFixedIncomeForm;
import com.example.form.GetMonthlyFixedSpendingForm;
import com.example.form.GetMonthlySpendingDataForm;
import com.example.form.GetMonthlyVariableDataForm;
import com.example.form.GetTimelineDataForm;
import com.example.form.GetTotalSpendingForm;
import com.example.form.GetTransactionForm;
import com.example.response.AddTransactionListResponse;
import com.example.response.AddTransactionResponse;
import com.example.response.DeleteTransactionResponse;
import com.example.response.EditTransactionResponse;
import com.example.response.FrequentTransactionNameResponse;
import com.example.response.GetHomeResponse;
import com.example.response.GetMonthlyFixedIncomeResponse;
import com.example.response.GetMonthlyFixedSpendingResponse;
import com.example.response.GetMonthlySpendingDataResponse;
import com.example.response.GetMonthlyVariableDataResponse;
import com.example.response.GetTimelineDataResponse;
import com.example.response.GetTotalSpendingResponse;
import com.example.response.GetTransactionResponse;
import com.example.service.AuthenticationService;
import com.example.service.TransactionService;
import com.example.service.ValidationService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private ValidationService validationService;

	/**
	 * 収支を登録
	 * 
	 * @throws AuthenticationException
	 */
	@PostMapping("/addTransaction")
	public AddTransactionResponse addTransaction(@RequestBody @Validated AddTransactionForm form, BindingResult result)
			throws SystemException {
		AddTransactionResponse res = new AddTransactionResponse();

		if (result.hasErrors()) {
			String errorMessage = validationService.getFirstErrorMessage(result);
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
			return res;
		}

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		return transactionService.addTransaction(form, res);
	}

	/**
	 * 収支リストを登録
	 * 
	 * @throws AuthenticationException
	 */
	@PostMapping("/addTransactionList")
	public AddTransactionListResponse addTransactionList(@RequestBody @Validated AddTransactionListForm form,
			BindingResult result) throws SystemException {
		AddTransactionListResponse res = new AddTransactionListResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.getTransactionList().forEach(tran -> tran.setUserNo(userNo));

		return transactionService.addTransactionList(form, res);
	}

	/**
	 * 収支を削除
	 * 
	 * @throws AuthenticationException
	 */
	@PostMapping("/deleteTransaction")
	public DeleteTransactionResponse deleteTransaction(@RequestBody DeleteTransactionForm form) throws SystemException {
		DeleteTransactionResponse res = new DeleteTransactionResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		return transactionService.deleteTransaction(form, res);
	}

	/**
	 * 収支を編集
	 * 
	 * @throws AuthenticationException
	 */
	@PostMapping("/editTransaction")
	public EditTransactionResponse editTransaction(@RequestBody @Validated EditTransactionForm form,
			BindingResult result) throws SystemException {
		EditTransactionResponse res = new EditTransactionResponse();

		if (result.hasErrors()) {
			String errorMessage = validationService.getFirstErrorMessage(result);
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
			return res;
		}

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		return transactionService.editTransaction(form, res);
	}

	/**
	 * 収支詳細の取得
	 * 
	 * @throws AuthenticationException
	 */
	@PostMapping("/getTransaction")
	public GetTransactionResponse getTransaction(@RequestBody GetTransactionForm form) throws SystemException {
		GetTransactionResponse res = new GetTransactionResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		return transactionService.getTransaction(form, res);
	}

	/**
	 * ６ヶ月分の合計支出を取得
	 * 
	 * @throws AuthenticationException
	 */
	@PostMapping("/getMonthlySpendingData")
	public GetMonthlySpendingDataResponse getMonthlySpendingData(@RequestBody GetMonthlySpendingDataForm form)
			throws SystemException {
		GetMonthlySpendingDataResponse res = new GetMonthlySpendingDataResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		return transactionService.getMonthlySpendingData(form, res);
	}

	/**
	 * 月別固定支出の取得
	 * 
	 * @throws AuthenticationException
	 */
	@PostMapping("/getMonthlyFixedSpending")
	public GetMonthlyFixedSpendingResponse getMonthlyFixedSpending(@RequestBody GetMonthlyFixedSpendingForm form)
			throws SystemException {
		GetMonthlyFixedSpendingResponse res = new GetMonthlyFixedSpendingResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		return transactionService.getMonthlyFixedSpending(form, res);
	}

	/**
	 * 月別固定収入の取得
	 * 
	 * @throws AuthenticationException
	 */
	@PostMapping("/getMonthlyFixedIncome")
	public GetMonthlyFixedIncomeResponse getMonthlyFixedIncome(@RequestBody GetMonthlyFixedIncomeForm form)
			throws SystemException {
		GetMonthlyFixedIncomeResponse res = new GetMonthlyFixedIncomeResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		return transactionService.getMonthlyFixedIncome(form, res);
	}

	/**
	 * 当月のTransactionデータを取得
	 * 
	 * @throws AuthenticationException
	 */
	@PostMapping("/getTimelineData")
	public GetTimelineDataResponse getMonthlyFixedIncome(@RequestBody GetTimelineDataForm form) throws SystemException {
		GetTimelineDataResponse res = new GetTimelineDataResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		return transactionService.getTimelineData(form, res);
	}

	/**
	 * ホーム画面情報の取得
	 * 
	 * @throws AuthenticationException
	 */
	@PostMapping("/getHome")
	public GetHomeResponse getHome(@RequestBody GetHomeForm form) throws SystemException {
		GetHomeResponse res = new GetHomeResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		return transactionService.getHome(form, res);
	}

	/**
	 * 指定月の変動費用・変動費合計を取得
	 * 
	 * @throws AuthenticationException
	 */
	@PostMapping("/getMonthlyVariableData")
	public GetMonthlyVariableDataResponse getMonthlyVariableData(@RequestBody GetMonthlyVariableDataForm form)
			throws SystemException {
		GetMonthlyVariableDataResponse res = new GetMonthlyVariableDataResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		return transactionService.getMonthlyVariableData(form, res);
	}

	/**
	 * 取引名レコメンド
	 * 
	 * @throws AuthenticationException
	 */
	@PostMapping("/getFrequentTransactionName")
	public FrequentTransactionNameResponse getFrequentTransactionName(@RequestBody FrequentTransactionNameForm form)
			throws SystemException {
		FrequentTransactionNameResponse res = new FrequentTransactionNameResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		return transactionService.getFrequentTransactionName(form, res);
	}

	/**
	 * カテゴリ毎の支出総額を取得
	 * 
	 * @throws AuthenticationException
	 */
	@PostMapping("/getTotalSpending")
	public GetTotalSpendingResponse getTotalSpending(@RequestBody GetTotalSpendingForm form) throws SystemException {
		GetTotalSpendingResponse res = new GetTotalSpendingResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		return transactionService.getTotalSpending(form, res);
	}
}
