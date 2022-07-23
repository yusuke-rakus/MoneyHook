package com.example.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.Status;
import com.example.common.exception.SystemException;
import com.example.common.message.ErrorMessage;
import com.example.domain.MonthlyTransaction;
import com.example.domain.SubCategory;
import com.example.form.DeleteFixedForm;
import com.example.form.EditFixedForm;
import com.example.form.GetDeletedFixedForm;
import com.example.form.GetFixedForm;
import com.example.mapper.MonthlyTransactionMapper;
import com.example.response.DeleteFixedResponse;
import com.example.response.EditFixedResponse;
import com.example.response.GetDeletedFixedResponse;
import com.example.response.GetFixedResponse;

@Service
@Transactional
public class MonthlyTransactionService {

	@Autowired
	private MonthlyTransactionMapper monthlyTransactionMapper;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	SubCategoryService subCategoryService;

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
			res.setMessage(ErrorMessage.MONTHLY_TRANSACTION_NOT_EXISTS);
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
			res.setMessage(ErrorMessage.DELETE_FIXED_ERROR);
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
			res.setMessage(ErrorMessage.MONTHLY_TRANSACTION_NOT_EXISTS);
		}

		return res;
	}

	/** 固定費の編集 */
	public EditFixedResponse editFixed(EditFixedForm form, EditFixedResponse res) throws SystemException {

		try {
			// monthlyTransactionIdを保持しているものを更新処理
			List<MonthlyTransaction> includingIdList = form.getMonthlyTransactionList().stream()
					.filter(i -> i.getMonthlyTransactionId() != null).collect(Collectors.toList());
			if (includingIdList.size() > 0) {
				this.updateFixed(includingIdList);
			}

			// monthlyTransactionIdがないものを登録処理
			List<MonthlyTransaction> notIncludingIdList = form.getMonthlyTransactionList().stream()
					.filter(i -> i.getMonthlyTransactionId() == null).collect(Collectors.toList());
			if (notIncludingIdList.size() > 0) {
				this.registerFixed(notIncludingIdList);
			}

		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
		}

		return res;
	}

	/** 固定費の登録 */
	public void registerFixed(List<MonthlyTransaction> list) throws SystemException {

		// サブカテゴリをDBのリストから選択した場合
		List<MonthlyTransaction> chosenSubCategoryList = list.stream().filter(i -> i.getSubCategoryId() != null)
				.collect(Collectors.toList());
		if (chosenSubCategoryList.size() > 0) {
			monthlyTransactionMapper.registerFixed(chosenSubCategoryList);
		}

		// ユーザー入力のサブカテゴリがある場合
		List<MonthlyTransaction> userInputSubCategoryList = list.stream().filter(i -> i.getSubCategoryName() != null)
				.filter(i -> !i.getSubCategoryName().isEmpty()).collect(Collectors.toList());
		if (userInputSubCategoryList.size() > 0) {
			for (MonthlyTransaction monthlyTran : userInputSubCategoryList) {

				SubCategory subCategory = new SubCategory();
				subCategory.setUserNo(monthlyTran.getUserNo());
				subCategory.setCategoryId(monthlyTran.getCategoryId());
				subCategory.setSubCategoryName(monthlyTran.getSubCategoryName());

				// サブカテゴリの登録
				subCategory = subCategoryService.insertSubCategory(subCategory);
				monthlyTran.setSubCategoryId(subCategory.getSubCategoryId());
			}
			monthlyTransactionMapper.registerFixed(userInputSubCategoryList);
		}
	}

	/** 固定費の更新 */
	public void updateFixed(List<MonthlyTransaction> list) throws SystemException {

		// サブカテゴリをDBのリストから選択した場合
		List<MonthlyTransaction> chosenSubCategoryList = list.stream().filter(i -> i.getSubCategoryId() != null)
				.collect(Collectors.toList());
		if (chosenSubCategoryList.size() > 0) {
			for (MonthlyTransaction monthlyTran : chosenSubCategoryList) {
				monthlyTransactionMapper.updateFixed(monthlyTran);
			}
		}

		// ユーザー入力のサブカテゴリがある場合
		List<MonthlyTransaction> userInputSubCategoryList = list.stream().filter(i -> i.getSubCategoryName() != null)
				.filter(i -> !i.getSubCategoryName().isEmpty()).collect(Collectors.toList());
		if (userInputSubCategoryList.size() > 0) {
			for (MonthlyTransaction monthlyTran : userInputSubCategoryList) {

				SubCategory subCategory = new SubCategory();
				subCategory.setUserNo(monthlyTran.getUserNo());
				subCategory.setCategoryId(monthlyTran.getCategoryId());
				subCategory.setSubCategoryName(monthlyTran.getSubCategoryName());

				// サブカテゴリの登録
				subCategory = subCategoryService.insertSubCategory(subCategory);
				monthlyTran.setSubCategoryId(subCategory.getSubCategoryId());
			}
			for (MonthlyTransaction monthlyTran : userInputSubCategoryList) {
				monthlyTransactionMapper.updateFixed(monthlyTran);
			}
		}
	}
}
