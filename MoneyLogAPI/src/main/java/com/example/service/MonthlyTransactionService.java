package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.Message;
import com.example.common.Status;
import com.example.common.exception.AuthenticationException;
import com.example.common.exception.SystemException;
import com.example.domain.MonthlyTransaction;
import com.example.form.DeleteFixedForm;
import com.example.form.GetDeletedFixedForm;
import com.example.form.GetFixedForm;
import com.example.mapper.MonthlyTransactionMapper;
import com.example.response.DeleteFixedResponse;
import com.example.response.GetDeletedFixedResponse;
import com.example.response.GetFixedResponse;

@Service
@Transactional
public class MonthlyTransactionService {

	@Autowired
	private MonthlyTransactionMapper monthlyTransactionMapper;

	@Autowired
	private AuthenticationService authenticationService;

	/** 毎月の固定費一覧の取得 */
	public GetFixedResponse getFixed(GetFixedForm form) throws SystemException {
		GetFixedResponse res = new GetFixedResponse();

		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		try {
			List<MonthlyTransaction> monthlyTransactionList = monthlyTransactionMapper.getFixed(form);
			if (monthlyTransactionList.size() == 0) {
				throw new Exception();
			}
			res.setMonthlyTransactionList(monthlyTransactionList);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(Message.MONTHLY_TRANSACTION_NOT_EXISTS.getMessage());
		}
		return res;
	}

	/**
	 * 固定費データの削除
	 * 
	 * @throws SystemException
	 */
	public DeleteFixedResponse deleteFixed(DeleteFixedForm form) throws SystemException {
		DeleteFixedResponse res = new DeleteFixedResponse();

		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		try {
			monthlyTransactionMapper.deleteFixed(form);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(Message.DELETE_FIXED_ERROR.getMessage());
		}

		return res;
	}

	/** 計算対象外の固定費一覧取得 */
	public GetDeletedFixedResponse getDeletedFixed(GetDeletedFixedForm form) throws SystemException {
		GetDeletedFixedResponse res = new GetDeletedFixedResponse();

		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		try {
			List<MonthlyTransaction> monthlyTransactionList = monthlyTransactionMapper.getDeletedFixed(form);
			if (monthlyTransactionList.size() == 0) {
				throw new Exception();
			}
			res.setMonthlyTransactionList(monthlyTransactionList);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(Message.MONTHLY_TRANSACTION_NOT_EXISTS.getMessage());
		}

		return res;
	}

}
