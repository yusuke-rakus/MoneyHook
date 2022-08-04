package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.common.exception.SystemException;
import com.example.form.DeleteFixedForm;
import com.example.form.EditFixedForm;
import com.example.form.GetDeletedFixedForm;
import com.example.form.GetFixedForm;
import com.example.response.DeleteFixedResponse;
import com.example.response.EditFixedResponse;
import com.example.response.GetDeletedFixedResponse;
import com.example.response.GetFixedResponse;
import com.example.service.AuthenticationService;
import com.example.service.MonthlyTransactionService;

@RestController
@RequestMapping("/fixed")
public class MonthlyTransactionController {

	@Autowired
	private MonthlyTransactionService monthlyTransactionService;

	@Autowired
	private AuthenticationService authenticationService;

	/** カテゴリ一覧の取得 */
	@PostMapping("/getFixed")
	public GetFixedResponse getCategoryList(@RequestBody GetFixedForm form) throws SystemException {
		GetFixedResponse res = new GetFixedResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		return monthlyTransactionService.getFixed(form, res);
	}

	/** 固定費データの削除 */
	@PostMapping("/deleteFixed")
	public DeleteFixedResponse deleteFixed(@RequestBody DeleteFixedForm form) throws SystemException {
		DeleteFixedResponse res = new DeleteFixedResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		return monthlyTransactionService.deleteFixed(form, res);
	}

	/** 計算対象外の固定費一覧取得 */
	@PostMapping("/getDeletedFixed")
	public GetDeletedFixedResponse getDeletedFixed(@RequestBody GetDeletedFixedForm form) throws SystemException {
		GetDeletedFixedResponse res = new GetDeletedFixedResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		return monthlyTransactionService.getDeletedFixed(form, res);
	}

	/** 固定費の編集 */
	@PostMapping("/editFixed")
	public EditFixedResponse getFixed(@RequestBody @Validated EditFixedForm form, BindingResult result)
			throws SystemException {
		EditFixedResponse res = new EditFixedResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);
		form.getMonthlyTransactionList().forEach(i -> i.setUserNo(userNo));

		return monthlyTransactionService.editFixed(form, res);
	}

}
