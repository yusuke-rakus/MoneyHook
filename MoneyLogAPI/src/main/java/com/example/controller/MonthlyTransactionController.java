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
import com.example.form.EditOneFixedForm;
import com.example.form.GetDeletedFixedForm;
import com.example.form.GetFixedForm;
import com.example.form.ReturnTargetForm;
import com.example.response.DeleteFixedResponse;
import com.example.response.EditFixedResponse;
import com.example.response.EditOneFixedResponse;
import com.example.response.GetDeletedFixedResponse;
import com.example.response.GetFixedResponse;
import com.example.response.ReturnTargetResponse;
import com.example.service.AuthenticationService;
import com.example.service.MonthlyTransactionService;

@RestController
@RequestMapping("/fixed")
public class MonthlyTransactionController {

	@Autowired
	private MonthlyTransactionService monthlyTransactionService;

	@Autowired
	private AuthenticationService authenticationService;
	
	/** 月次データ一覧の取得 */
	@PostMapping("/getFixed")
	public GetFixedResponse getFixed(@RequestBody GetFixedForm form) throws SystemException {
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

	/** 固定費データの削除 */
	@PostMapping("/deleteFixedFromTable")
	public DeleteFixedResponse deleteFixedFromTable(@RequestBody DeleteFixedForm form) throws SystemException {
		DeleteFixedResponse res = new DeleteFixedResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		return monthlyTransactionService.deleteFixedFromTable(form, res);
	}

	/** 計算対象外データを戻す */
	@PostMapping("/returnTarget")
	public ReturnTargetResponse returnTarget(@RequestBody ReturnTargetForm form) throws SystemException {
		ReturnTargetResponse res = new ReturnTargetResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		return monthlyTransactionService.returnTarget(form, res);
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
	public EditFixedResponse editFixed(@RequestBody @Validated EditFixedForm form, BindingResult result)
			throws SystemException {
		EditFixedResponse res = new EditFixedResponse();
		
		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);
		form.getMonthlyTransactionList().forEach(i -> i.setUserNo(userNo));

		return monthlyTransactionService.editFixed(form, res);
	}

	/** 固定費の編集(1件) */
	@PostMapping("/editOneFixed")
	public EditOneFixedResponse editOneFixed(@RequestBody @Validated EditOneFixedForm form, BindingResult result)
			throws SystemException {
		EditOneFixedResponse res = new EditOneFixedResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);
		form.getMonthlyTransaction().setUserNo(userNo);

		return monthlyTransactionService.editOneFixed(form, res);
	}

}
