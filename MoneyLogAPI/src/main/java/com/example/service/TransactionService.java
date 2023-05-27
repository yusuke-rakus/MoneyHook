package com.example.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.Status;
import com.example.common.exception.AuthenticationException;
import com.example.common.exception.SystemException;
import com.example.common.message.ErrorMessage;
import com.example.common.message.SuccessMessage;
import com.example.domain.CategoryList;
import com.example.domain.MonthlyFixedList;
import com.example.domain.SubCategory;
import com.example.domain.Transaction;
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
import com.example.form.GetTransactionForm;
import com.example.mapper.SubCategoryMapper;
import com.example.mapper.TransactionMapper;
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

			Long subCategoryId = subCategoryMapper.checkSubCategory(subCategory);
			// 入力したサブカテゴリが存在する場合
			if (!Objects.isNull(subCategoryId)) {
				form.setSubCategoryId(subCategoryId);
			} else {
				// 新規サブカテゴリ作成
				subCategoryMapper.addSubCategory(subCategory);

				Long createdSubCategoryId = subCategory.getSubCategoryId();
				form.setSubCategoryId(createdSubCategoryId);
			}
		}

		try {
			Integer transactionAmount = form.getTransactionAmount();
			Integer sign = form.getTransactionSign();
			form.setTransactionAmount(transactionAmount * sign);
			transactionMapper.addTransaction(form);
			res.setMessage(SuccessMessage.TRANSACTION_INSERT_SUCCESSED);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.TRANSACTION_DATA_INSERT_FAILED);
		}
		return res;
	}

	/**
	 * 収支リストを登録
	 * 
	 * @throws AuthenticationException
	 */
	public AddTransactionListResponse addTransactionList(AddTransactionListForm form, AddTransactionListResponse res)
			throws SystemException {

		try {
			form.getTransactionList().stream().forEach(
					tran -> tran.setTransactionAmount(tran.getTransactionSign() * tran.getTransactionAmount()));

			form.getTransactionList().stream().filter(tran -> !Objects.isNull(tran.getCategoryId()))
					.filter(tran -> !Objects.isNull(tran.getSubCategoryId()))
					.filter(tran -> !Objects.isNull(tran.getTransactionAmount()))
					.filter(tran -> !Objects.isNull(tran.getTransactionName())).toArray();
			transactionMapper.addTransactionList(form.getTransactionList());
			res.setMessage(SuccessMessage.TRANSACTION_INSERT_SUCCESSED);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.TRANSACTION_DATA_INSERT_FAILED);
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
			res.setMessage(SuccessMessage.TRANSACTION_DELETE_SUCCESSED);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.TRANSACTION_DATA_DELETE_FAILED);
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

		// 編集実行
		try {
			Integer transactionAmount = form.getTransactionAmount();
			Integer sign = form.getTransactionSign();
			form.setTransactionAmount(transactionAmount * sign);
			transactionMapper.editTransaction(form);
			res.setMessage(SuccessMessage.TRANSACTION_EDIT_SUCCESSED);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.TRANSACTION_DATA_INSERT_FAILED);
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

			if (monthlyTotalAmountList.size() < 6) {

				List<Transaction> transactionList = new ArrayList<>();
				for (int i = 0; i < 6; i++) {
					LocalDate localDate = form.getMonth().toLocalDate().minusMonths(i);
					Transaction tempTran = new Transaction(Date.valueOf(localDate));
					transactionList.add(tempTran);
				}

				for (Transaction transaction : monthlyTotalAmountList) {
					transactionList.stream().filter(t -> t.getMonth().equals(transaction.getMonth()))
							.forEach(t -> t.setTotalAmount(transaction.getTotalAmount()));
				}
				monthlyTotalAmountList = transactionList;
			}

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

			if (categoryList.size() > 7) {

				List<CategoryList> processedCategoryList = new ArrayList<>();

				Integer othersTotalAmount = 0;
				List<Transaction> othersCategoryname = new ArrayList<>();

				for (int i = 0; i < categoryList.size(); i++) {

					if (i < 7) {
						processedCategoryList.add(categoryList.get(i));
					} else {
						othersTotalAmount += categoryList.get(i).getCategoryTotalAmount();
						othersCategoryname.add(new Transaction(categoryList.get(i).getCategoryName(),
								categoryList.get(i).getCategoryTotalAmount()));
					}

				}

				CategoryList othersCategory = new CategoryList("その他", othersTotalAmount, othersCategoryname);
				processedCategoryList.add(othersCategory);
				categoryList = processedCategoryList;

			}

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

	/**
	 * 取引名レコメンド
	 * 
	 * @throws AuthenticationException
	 */
	public FrequentTransactionNameResponse getFrequentTransactionName(FrequentTransactionNameForm form,
			FrequentTransactionNameResponse res) throws SystemException {

		// データを取得
		try {
			List<Transaction> transactionList = transactionMapper.getFrequentTransactionName(form);
			res.setTransactionList(transactionList);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
		}

		return res;
	}

}
