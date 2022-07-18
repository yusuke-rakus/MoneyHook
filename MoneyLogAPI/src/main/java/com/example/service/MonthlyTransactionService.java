package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.AuthenticationException;
import com.example.common.Message;
import com.example.common.Status;
import com.example.domain.MonthlyTransaction;
import com.example.form.GetFixedForm;
import com.example.mapper.MonthlyTransactionMapper;
import com.example.response.GetFixedResponse;

@Service
@Transactional
public class MonthlyTransactionService {

	@Autowired
	private MonthlyTransactionMapper monthlyTransactionMapper;

	@Autowired
	private AuthenticationService authenticationService;

	/** 毎月の固定費一覧の取得 */
	public GetFixedResponse getFixed(GetFixedForm form) {
		GetFixedResponse res = new GetFixedResponse();

		// ユーザーIDからユーザーNoを取得
		try {
			Long userNo = authenticationService.authUser(form);
			form.setUserNo(userNo);
		} catch (AuthenticationException e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(Message.AUTHENTICATION_ERROR.getMessage());
			return res;
		}

		try {
			List<MonthlyTransaction> monthlyTransactionList = monthlyTransactionMapper.getFixed(form);
			res.setMonthlyTransactionList(monthlyTransactionList);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(Message.MONTHLY_TRANSACTION_NOT_EXISTS.getMessage());
		}
		return res;
	}

}
