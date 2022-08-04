package com.example.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.Status;
import com.example.common.exception.AuthenticationException;
import com.example.common.exception.SystemException;
import com.example.common.message.ErrorMessage;
import com.example.domain.CategoryList;
import com.example.domain.MonthlyFixedList;
import com.example.domain.SubCategory;
import com.example.domain.Transaction;
import com.example.form.AddTransactionForm;
import com.example.form.DeleteTransactionForm;
import com.example.form.EditTransactionForm;
import com.example.form.GetHomeForm;
import com.example.form.GetMonthlyFixedIncomeForm;
import com.example.form.GetMonthlyFixedSpendingForm;
import com.example.form.GetMonthlySpendingDataForm;
import com.example.form.GetMonthlyVariableDataForm;
import com.example.form.GetTimelineDataForm;
import com.example.form.GetTransactionForm;
import com.example.mapper.SubCategoryMapper;
import com.example.mapper.TransactionMapper;
import com.example.response.AddTransactionResponse;
import com.example.response.DeleteTransactionResponse;
import com.example.response.EditTransactionResponse;
import com.example.response.GetHomeResponse;
import com.example.response.GetMonthlyFixedIncomeResponse;
import com.example.response.GetMonthlyFixedSpendingResponse;
import com.example.response.GetMonthlySpendingDataResponse;
import com.example.response.GetMonthlyVariableDataResponse;
import com.example.response.GetTimelineDataResponse;
import com.example.response.GetTransactionResponse;

@Service
@Transactional
public class TransactionService {

	@Autowired
	private TransactionMapper transactionMapper;

	@Autowired
	private SubCategoryMapper subCategoryMapper;

	/**
	 * 収支を登録
	 * 
	 * @throws AuthenticationException
	 */
	public AddTransactionResponse addTransaction(AddTransactionForm form, AddTransactionResponse res)
			throws SystemException {

		// サブカテゴリを新規追加した場合
		if (Objects.isNull(form.getSubCategoryId())) {

			// サブカテゴリテーブルに登録してIDを取得する処理
			SubCategory subCategory = new SubCategory();
			subCategory.setUserNo(form.getUserNo());
			subCategory.setCategoryId(form.getCategoryId());
			subCategory.setSubCategoryName(form.getSubCategoryName());

			try {
				// 重複したサブカテゴリを作成しないよう制約を付与
				subCategoryMapper.addSubCategory(subCategory);
			} catch (Exception e) {
				res.setStatus(Status.ERROR.getStatus());
				res.setMessage(ErrorMessage.SUB_CATEGORY_ALREADY_REGISTERED);
				return res;
			}

			Long subCategoryId = subCategory.getSubCategoryId();
			form.setSubCategoryId(subCategoryId);
		}

		try {
			transactionMapper.addTransaction(form);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
		}
		return res;
	}

	/**
	 * 収支データの削除
	 * 
	 * @throws AuthenticationException
	 */
	public DeleteTransactionResponse deleteTransaction(DeleteTransactionForm form, DeleteTransactionResponse res)
			throws SystemException {

		// 削除処理
		try {
			transactionMapper.deleteTransaction(form);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			return res;
		}

		return res;
	}

	/**
	 * 収支データの編集
	 * 
	 * @throws AuthenticationException
	 */
	public EditTransactionResponse editTransaction(EditTransactionForm form, EditTransactionResponse res)
			throws SystemException {

		// サブカテゴリを新規追加した場合
		if (Objects.isNull(form.getSubCategoryId())) {

			// サブカテゴリテーブルに登録してIDを取得する処理
			SubCategory subCategory = new SubCategory();
			subCategory.setUserNo(form.getUserNo());
			subCategory.setCategoryId(form.getCategoryId());
			subCategory.setSubCategoryName(form.getSubCategoryName());

			try {
				// 重複したサブカテゴリを作成しないよう制約を付与
				subCategoryMapper.addSubCategory(subCategory);
			} catch (Exception e) {
				res.setStatus(Status.ERROR.getStatus());
				res.setMessage(ErrorMessage.SUB_CATEGORY_ALREADY_REGISTERED);
				return res;
			}

			Long subCategoryId = subCategory.getSubCategoryId();
			form.setSubCategoryId(subCategoryId);
		}

		// 編集
		try {
			transactionMapper.editTransaction(form);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			return res;
		}

		return res;
	}

	/**
	 * 収支詳細の取得
	 * 
	 * @throws SystemException
	 */
	public GetTransactionResponse getTransaction(GetTransactionForm form, GetTransactionResponse res)
			throws SystemException {

		// 収支データを取得
		try {
			Transaction transaction = transactionMapper.getTransaction(form);

			if (Objects.isNull(transaction)) {
				throw new Exception();
			}

			res.setTransaction(transaction);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.TRANSACTION_DATA_SELECT_FAILED);
			return res;
		}

		return res;
	}

	/**
	 * ６ヶ月分の合計支出を取得
	 * 
	 * @throws AuthenticationException
	 */
	public GetMonthlySpendingDataResponse getMonthlySpendingData(GetMonthlySpendingDataForm form,
			GetMonthlySpendingDataResponse res) throws SystemException {

		// 合計支出リストを取得
		try {
			List<Transaction> monthlyTotalAmountList = transactionMapper.getMonthlySpendingData(form);
			res.setMonthlyTotalAmountList(monthlyTotalAmountList);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
		}

		return res;
	}

	/**
	 * 月別固定支出の取得
	 * 
	 * @throws AuthenticationException
	 */
	public GetMonthlyFixedSpendingResponse getMonthlyFixedSpending(GetMonthlyFixedSpendingForm form,
			GetMonthlyFixedSpendingResponse res) throws SystemException {

		// 合計支出リストを取得
		try {
			List<MonthlyFixedList> monthlyFixedList = transactionMapper.getMonthlyFixedSpending(form);
			res.setMonthlyFixedList(monthlyFixedList);

			Integer disposableIncome = monthlyFixedList.stream().mapToInt(i -> i.getTotalCategoryAmount()).sum();
			res.setDisposableIncome(disposableIncome);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.MONTHLY_FIXED_SPENDING_GET_FAILED);
		}

		return res;
	}

	/**
	 * 月別固定収入の取得
	 * 
	 * @throws AuthenticationException
	 */
	public GetMonthlyFixedIncomeResponse getMonthlyFixedIncome(GetMonthlyFixedIncomeForm form,
			GetMonthlyFixedIncomeResponse res) throws SystemException {

		// 合計収入リストを取得
		try {
			List<MonthlyFixedList> monthlyFixedList = transactionMapper.getMonthlyFixedIncome(form);
			res.setMonthlyFixedList(monthlyFixedList);

			Integer disposableIncome = monthlyFixedList.stream().mapToInt(i -> i.getTotalCategoryAmount()).sum();
			res.setDisposableIncome(disposableIncome);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.MONTHLY_FIXED_SPENDING_GET_FAILED);
		}

		return res;
	}

	/**
	 * 当月のTransactionデータを取得
	 * 
	 * @throws AuthenticationException
	 */
	public GetTimelineDataResponse getTimelineData(GetTimelineDataForm form, GetTimelineDataResponse res)
			throws SystemException {

		// タイムラインデータを取得
		try {
			List<Transaction> transactionList = transactionMapper.getTimelineData(form);
			res.setTransactionList(transactionList);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.TIMELINE_DATA_GET_FAILED);
		}

		return res;
	}

	/**
	 * ホーム画面情報の取得
	 * 
	 * @throws AuthenticationException
	 */
	public GetHomeResponse getHome(GetHomeForm form, GetHomeResponse res) throws SystemException {

		// タイムラインデータを取得
		try {
			List<CategoryList> categoryList = transactionMapper.getHome(form);
			res.setCategoryList(categoryList);

			Integer balance = categoryList.stream().mapToInt(i -> i.getCategoryTotalAmount()).sum();
			res.setBalance(balance);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.TIMELINE_DATA_GET_FAILED);
		}

		return res;
	}

	/**
	 * 指定月の変動費用・変動費合計を取得
	 * 
	 * @throws AuthenticationException
	 */
	public GetMonthlyVariableDataResponse getMonthlyVariableData(GetMonthlyVariableDataForm form,
			GetMonthlyVariableDataResponse res) throws SystemException {

		// データを取得
		try {
			List<CategoryList> categoryList = transactionMapper.getMonthlyVariableData(form);
			res.setMonthlyVariableList(categoryList);

			Integer totalVariable = categoryList.stream().mapToInt(i -> i.getCategoryTotalAmount()).sum();
			res.setTotalVariable(totalVariable);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.MONTHLY_VARIABLE_DATA_GET_FAILED);
		}

		return res;
	}

}
