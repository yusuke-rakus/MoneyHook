package com.example.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.Message;
import com.example.common.Status;
import com.example.common.exception.AuthenticationException;
import com.example.common.exception.SystemException;
import com.example.domain.MonthlyFixedList;
import com.example.domain.SubCategory;
import com.example.domain.Transaction;
import com.example.form.AddTransactionForm;
import com.example.form.DeleteTransactionForm;
import com.example.form.EditTransactionForm;
import com.example.form.GetMonthlyFixedIncomeForm;
import com.example.form.GetMonthlyFixedSpendingForm;
import com.example.form.GetMonthlySpendingDataForm;
import com.example.form.GetTimelineDataForm;
import com.example.form.GetTransactionForm;
import com.example.mapper.SubCategoryMapper;
import com.example.mapper.TransactionMapper;
import com.example.response.AddTransactionResponse;
import com.example.response.DeleteTransactionResponse;
import com.example.response.EditTransactionResponse;
import com.example.response.GetMonthlyFixedIncomeResponse;
import com.example.response.GetMonthlyFixedSpendingResponse;
import com.example.response.GetMonthlySpendingDataResponse;
import com.example.response.GetTimelineDataResponse;
import com.example.response.GetTransactionResponse;

@Service
@Transactional
public class TransactionService {

	@Autowired
	private TransactionMapper transactionMapper;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private SubCategoryMapper subCategoryMapper;

	/**
	 * 収支を登録
	 * 
	 * @throws AuthenticationException
	 */
	public AddTransactionResponse addTransaction(AddTransactionForm form) throws SystemException {
		AddTransactionResponse res = new AddTransactionResponse();

		if (Objects.isNull(form.getTransactionDate())) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(Message.TRANSACTION_DATE_EMPTY_ERROR.getMessage());
			return res;
		}
		if (Objects.isNull(form.getTransactionAmount())) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(Message.AMOUNT_EMPTY_ERROR.getMessage());
			return res;
		}
		if (Objects.isNull(form.getTransactionName()) || form.getTransactionName().isEmpty()) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(Message.TRANSACTION_NAME_EMPTY_ERROR.getMessage());
			return res;
		}
		if (!Objects.isNull(form.getSubCategoryId()) && !Objects.isNull(form.getSubCategoryName())) {
			res.setStatus(Status.ERROR.getStatus());
			return res;
		}

		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

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
				res.setMessage(Message.SUB_CATEGORY_ALREADY_REGISTERED.getMessage());
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
	public DeleteTransactionResponse deleteTransaction(DeleteTransactionForm form) throws SystemException {
		DeleteTransactionResponse res = new DeleteTransactionResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

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
	public EditTransactionResponse editTransaction(EditTransactionForm form) throws SystemException {
		EditTransactionResponse res = new EditTransactionResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

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
				res.setMessage(Message.SUB_CATEGORY_ALREADY_REGISTERED.getMessage());
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
	public GetTransactionResponse getTransaction(GetTransactionForm form) throws SystemException {
		GetTransactionResponse res = new GetTransactionResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		// 収支データを取得
		try {
			Transaction transaction = transactionMapper.getTransaction(form);

			if (Objects.isNull(transaction)) {
				throw new Exception();
			}

			res.setTransaction(transaction);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(Message.TRANSACTION_DATA_SELECT_FAILED.getMessage());
			return res;
		}

		return res;
	}

	/**
	 * ６ヶ月分の合計支出を取得
	 * 
	 * @throws AuthenticationException
	 */
	public GetMonthlySpendingDataResponse getMonthlySpendingData(GetMonthlySpendingDataForm form)
			throws SystemException {
		GetMonthlySpendingDataResponse res = new GetMonthlySpendingDataResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

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
	public GetMonthlyFixedSpendingResponse getMonthlyFixedSpending(GetMonthlyFixedSpendingForm form)
			throws SystemException {
		GetMonthlyFixedSpendingResponse res = new GetMonthlyFixedSpendingResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		// 合計支出リストを取得
		try {
			List<MonthlyFixedList> monthlyFixedList = transactionMapper.getMonthlyFixedSpending(form);
			res.setMonthlyFixedList(monthlyFixedList);

			Integer disposableIncome = monthlyFixedList.stream().mapToInt(i -> i.getTotalCategoryAmount()).sum();
			res.setDisposableIncome(disposableIncome);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(Message.MONTHLY_FIXED_SPENDING_GET_FAILED.getMessage());
		}

		return res;
	}

	/**
	 * 月別固定収入の取得
	 * 
	 * @throws AuthenticationException
	 */
	public GetMonthlyFixedIncomeResponse getMonthlyFixedIncome(GetMonthlyFixedIncomeForm form) throws SystemException {
		GetMonthlyFixedIncomeResponse res = new GetMonthlyFixedIncomeResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		// 合計収入リストを取得
		try {
			List<MonthlyFixedList> monthlyFixedList = transactionMapper.getMonthlyFixedIncome(form);
			res.setMonthlyFixedList(monthlyFixedList);

			Integer disposableIncome = monthlyFixedList.stream().mapToInt(i -> i.getTotalCategoryAmount()).sum();
			res.setDisposableIncome(disposableIncome);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(Message.MONTHLY_FIXED_SPENDING_GET_FAILED.getMessage());
		}

		return res;
	}

	/**
	 * 当月のTransactionデータを取得
	 * 
	 * @throws AuthenticationException
	 */
	public GetTimelineDataResponse getTimelineData(GetTimelineDataForm form) throws SystemException {
		GetTimelineDataResponse res = new GetTimelineDataResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		// タイムラインデータを取得
		try {
			List<Transaction> transactionList = transactionMapper.getTimelineData(form);
			res.setTransactionList(transactionList);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(Message.TIMELINE_DATA_GET_FAILED.getMessage());
		}

		return res;
	}

}
