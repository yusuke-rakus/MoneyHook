package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.form.DeleteFixedForm;
import com.example.form.GetFixedForm;
import com.example.response.DeleteFixedResponse;
import com.example.response.GetFixedResponse;
import com.example.service.MonthlyTransactionService;

@RestController
@RequestMapping("/fixed")
public class MonthlyTransactionController {

	@Autowired
	private MonthlyTransactionService monthlyTransactionService;

	/** カテゴリ一覧の取得 */
	@PostMapping("/getFixed")
	public GetFixedResponse getCategoryList(@RequestBody GetFixedForm form) {
		return monthlyTransactionService.getFixed(form);
	}
	
	/** 固定費データの削除 */
	@PostMapping("/deleteFixed")
	public DeleteFixedResponse deleteFixed(@RequestBody DeleteFixedForm form) {
		return monthlyTransactionService.deleteFixed(form);
	}

}
