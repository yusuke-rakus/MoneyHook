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
import com.example.form.MonthlyTransactionList;
import com.example.form.ReturnTargetForm;
import com.example.mapper.MonthlyTransactionMapper;
import com.example.response.DeleteFixedResponse;
import com.example.response.EditFixedResponse;
import com.example.response.GetDeletedFixedResponse;
import com.example.response.GetFixedResponse;
import com.example.response.ReturnTargetResponse;

@Service
@Transactional
public class MonthlyTransactionService {

	@Autowired
	private MonthlyTransactionMapper monthlyTransactionMapper;

	@Autowired
	SubCategoryService subCategoryService;

	/** 毎月の固定費一覧の取得 */
	public GetFixedResponse getFixed(GetFixedForm form, GetFixedResponse res) throws SystemException {

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
	public DeleteFixedResponse deleteFixed(DeleteFixedForm form, DeleteFixedResponse res) throws SystemException {

		try {
			monthlyTransactionMapper.deleteFixed(form);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.DELETE_FIXED_ERROR);
		}

		return res;
	}

	/**
	 * 固定費データの削除(物理)
	 * 
	 * @throws SystemException
	 */
	public DeleteFixedResponse deleteFixedFromTable(DeleteFixedForm form, DeleteFixedResponse res) throws SystemException {

		try {
			monthlyTransactionMapper.deleteFixedFromTable(form);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.DELETE_FIXED_ERROR);
		}

		return res;
	}

	/**
	 * 計算対象外データを戻す
	 * 
	 * @throws SystemException
	 */
	public ReturnTargetResponse returnTarget(ReturnTargetForm form, ReturnTargetResponse res) throws SystemException {

		try {
			monthlyTransactionMapper.returnTarget(form);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.DELETE_FIXED_ERROR);
		}

		return res;
	}

	/** 計算対象外の固定費一覧取得 */
	public GetDeletedFixedResponse getDeletedFixed(GetDeletedFixedForm form, GetDeletedFixedResponse res)
			throws SystemException {

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
			List<MonthlyTransactionList> includingIdList = form.getMonthlyTransactionList().stream()
					.filter(i -> i.getMonthlyTransactionId() != null).collect(Collectors.toList());
			if (includingIdList.size() > 0) {
				this.updateFixed(includingIdList);
			}

			// monthlyTransactionIdがないものを登録処理
			List<MonthlyTransactionList> notIncludingIdList = form.getMonthlyTransactionList().stream()
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
	public void registerFixed(List<MonthlyTransactionList> list) throws SystemException {

		// サブカテゴリをDBのリストから選択した場合
		List<MonthlyTransactionList> chosenSubCategoryList = list.stream().filter(i -> i.getSubCategoryId() != null)
				.collect(Collectors.toList());
		if (chosenSubCategoryList.size() > 0) {
			monthlyTransactionMapper.registerFixed(chosenSubCategoryList);
		}

		// ユーザー入力のサブカテゴリがある場合
		List<MonthlyTransactionList> userInputSubCategoryList = list.stream()
				.filter(i -> i.getSubCategoryName() != null).filter(i -> !i.getSubCategoryName().isEmpty())
				.collect(Collectors.toList());
		if (userInputSubCategoryList.size() > 0) {
			for (MonthlyTransactionList monthlyTran : userInputSubCategoryList) {

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
	public void updateFixed(List<MonthlyTransactionList> list) throws SystemException {

		// サブカテゴリをDBのリストから選択した場合
		List<MonthlyTransactionList> chosenSubCategoryList = list.stream().filter(i -> i.getSubCategoryId() != null)
				.collect(Collectors.toList());
		if (chosenSubCategoryList.size() > 0) {
			for (MonthlyTransactionList monthlyTran : chosenSubCategoryList) {
				monthlyTransactionMapper.updateFixed(monthlyTran);
			}
		}

		// ユーザー入力のサブカテゴリがある場合
		List<MonthlyTransactionList> userInputSubCategoryList = list.stream()
				.filter(i -> i.getSubCategoryName() != null).filter(i -> !i.getSubCategoryName().isEmpty())
				.collect(Collectors.toList());
		if (userInputSubCategoryList.size() > 0) {
			for (MonthlyTransactionList monthlyTran : userInputSubCategoryList) {

				SubCategory subCategory = new SubCategory();
				subCategory.setUserNo(monthlyTran.getUserNo());
				subCategory.setCategoryId(monthlyTran.getCategoryId());
				subCategory.setSubCategoryName(monthlyTran.getSubCategoryName());

				// サブカテゴリの登録
				subCategory = subCategoryService.insertSubCategory(subCategory);
				monthlyTran.setSubCategoryId(subCategory.getSubCategoryId());
			}
			for (MonthlyTransactionList monthlyTran : userInputSubCategoryList) {
				monthlyTransactionMapper.updateFixed(monthlyTran);
			}
		}
	}
}
