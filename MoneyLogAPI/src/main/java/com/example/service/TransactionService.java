package com.example.service;

import com.example.common.DateFormatter;
import com.example.common.Status;
import com.example.common.exception.*;
import com.example.common.message.ErrorMessage;
import com.example.common.message.SuccessMessage;
import com.example.domain.*;
import com.example.form.*;
import com.example.mapper.CategoryMapper;
import com.example.mapper.SubCategoryMapper;
import com.example.mapper.TransactionMapper;
import com.example.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class TransactionService {

	@Autowired
	private TransactionMapper transactionMapper;

	@Autowired
	private SubCategoryMapper subCategoryMapper;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CategoryMapper categoryMapper;

	/**
	 * 収支を登録
	 */
	public AddTransactionResponse addTransaction(AddTransactionForm form, AddTransactionResponse res)
			throws SystemException {

		// サブカテゴリを新規追加した場合
		if (Objects.isNull(form.getSubCategoryId())) {
			Long subCategoryId = findOrCreateSubCategory(form.getUserNo(), form.getCategoryId(),
					form.getSubCategoryName());
			form.setSubCategoryId(subCategoryId);
		}

		// カテゴリ存在チェック
		checkCategoryExist(form.getCategoryId());

		// カテゴリとサブカテゴリのリレーションをチェック
		checkCategoryRelational(form.getUserNo(), form.getCategoryId(), form.getSubCategoryId());

		try {
			BigInteger transactionAmount = form.getTransactionAmount();
			int sign = form.getTransactionSign() < 0 ? -1 : 1;
			form.setTransactionAmount(transactionAmount.multiply(BigInteger.valueOf(sign)));
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
	 */
	public AddTransactionListResponse addTransactionList(AddTransactionListForm form, AddTransactionListResponse res) {

		try {
			// 収支リストのエラーチェック
			checkTransactionList(form.getTransactionList(), res);

			form.getTransactionList().forEach(
					tran -> tran.setTransactionAmount(tran.getTransactionAmount().multiply(BigInteger.valueOf(tran.getTransactionSign()))));

			// 収支登録
			transactionMapper.addTransactionList(form.getTransactionList());
			res.setMessage(SuccessMessage.TRANSACTION_INSERT_SUCCESSED);
		} catch (HasErrorTransactionException e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(e.getMessage());
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.TRANSACTION_DATA_INSERT_FAILED);
		}
		return res;
	}

	/**
	 * 収支データの削除
	 */
	public DeleteTransactionResponse deleteTransaction(DeleteTransactionForm form, DeleteTransactionResponse res)
			throws SystemException {

		// 収支存在チェック
		checkTransactionExist(form.getUserNo(), form.getTransactionId());

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
	 */
	public EditTransactionResponse editTransaction(EditTransactionForm form, EditTransactionResponse res)
			throws SystemException {

		// サブカテゴリを新規追加した場合
		if (Objects.isNull(form.getSubCategoryId())) {
			Long subCategoryId = findOrCreateSubCategory(form.getUserNo(), form.getCategoryId(),
					form.getSubCategoryName());
			form.setSubCategoryId(subCategoryId);
		}

		// 収支存在チェック
		checkTransactionExist(form.getUserNo(), form.getTransactionId());

		// カテゴリ存在チェック
		checkCategoryExist(form.getCategoryId());

		// 編集実行
		try {
			BigInteger transactionAmount = form.getTransactionAmount();
			Integer sign = form.getTransactionSign();
			form.setTransactionAmount(transactionAmount.multiply(BigInteger.valueOf(sign)));
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
	 */
	public GetMonthlySpendingDataResponse getMonthlySpendingData(GetMonthlySpendingDataForm form,
																 GetMonthlySpendingDataResponse res) throws SystemException {

		// 合計支出リストを取得
		try {
			form.setMonth(DateFormatter.toFirstDayOfMonth(form.getMonth()));
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
	 */
	public GetMonthlyFixedSpendingResponse getMonthlyFixedSpending(GetMonthlyFixedSpendingForm form,
																   GetMonthlyFixedSpendingResponse res) throws SystemException {

		// 合計支出リストを取得
		try {
			form.setMonth(DateFormatter.toFirstDayOfMonth(form.getMonth()));
			List<MonthlyFixedList> monthlyFixedList = transactionMapper.getMonthlyFixedSpending(form);
			res.setMonthlyFixedList(monthlyFixedList);

			BigInteger disposableIncome =
					monthlyFixedList.stream().map(MonthlyFixedList::getTotalCategoryAmount).reduce(BigInteger.ZERO,
							BigInteger::add);
			res.setDisposableIncome(disposableIncome);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.MONTHLY_FIXED_SPENDING_GET_FAILED);
		}

		return res;
	}

	/**
	 * 月別固定収入の取得
	 */
	public GetMonthlyFixedIncomeResponse getMonthlyFixedIncome(GetMonthlyFixedIncomeForm form,
															   GetMonthlyFixedIncomeResponse res) throws SystemException {

		// 合計収入リストを取得
		try {
			form.setMonth(DateFormatter.toFirstDayOfMonth(form.getMonth()));
			List<MonthlyFixedList> monthlyFixedList = transactionMapper.getMonthlyFixedIncome(form);
			res.setMonthlyFixedList(monthlyFixedList);

			BigInteger disposableIncome =
					monthlyFixedList.stream().map(MonthlyFixedList::getTotalCategoryAmount).reduce(BigInteger.ZERO,
							BigInteger::add);
			res.setDisposableIncome(disposableIncome);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.MONTHLY_FIXED_SPENDING_GET_FAILED);
		}

		return res;
	}

	/**
	 * 当月のTransactionデータを取得
	 */
	public GetTimelineDataResponse getTimelineData(GetTimelineDataForm form, GetTimelineDataResponse res)
			throws SystemException {

		// タイムラインデータを取得
		try {
			form.setMonth(DateFormatter.toFirstDayOfMonth(form.getMonth()));
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
	 */
	public GetHomeResponse getHome(GetHomeForm form, GetHomeResponse res) throws SystemException {

		// タイムラインデータを取得
		try {
			form.setMonth(DateFormatter.toFirstDayOfMonth(form.getMonth()));
			List<CategoryList> categoryList = transactionMapper.getHome(form);

			if (categoryList.size() > 7) {

				List<CategoryList> processedCategoryList = new ArrayList<>();

				BigInteger othersTotalAmount = BigInteger.ZERO;
				List<Transaction> othersCategoryName = new ArrayList<>();

				for (int i = 0; i < categoryList.size(); i++) {

					if (i < 7) {
						processedCategoryList.add(categoryList.get(i));
					} else {
						othersTotalAmount.add(categoryList.get(i).getCategoryTotalAmount());
						othersCategoryName.add(new Transaction(categoryList.get(i).getCategoryName(),
								categoryList.get(i).getCategoryTotalAmount()));
					}

				}

				CategoryList othersCategory = new CategoryList("その他", othersTotalAmount, othersCategoryName);
				processedCategoryList.add(othersCategory);
				categoryList = processedCategoryList;

			}

			res.setCategoryList(categoryList);

			BigInteger balance =
					categoryList.stream().map(CategoryList::getCategoryTotalAmount).reduce(BigInteger.ZERO,
							BigInteger::add);
			res.setBalance(balance);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.TIMELINE_DATA_GET_FAILED);
		}

		return res;
	}

	/**
	 * 指定月の変動費用・変動費合計を取得
	 */
	public GetMonthlyVariableDataResponse getMonthlyVariableData(GetMonthlyVariableDataForm form,
																 GetMonthlyVariableDataResponse res) throws SystemException {

		// データを取得
		try {
			form.setMonth(DateFormatter.toFirstDayOfMonth(form.getMonth()));
			List<CategoryList> categoryList = transactionMapper.getMonthlyVariableData(form);
			res.setMonthlyVariableList(categoryList);

			BigInteger totalVariable =
					categoryList.stream().map(CategoryList::getCategoryTotalAmount).reduce(BigInteger.ZERO,
							BigInteger::add);
			res.setTotalVariable(totalVariable);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.MONTHLY_VARIABLE_DATA_GET_FAILED);
		}

		return res;
	}

	/**
	 * 取引名レコメンド
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

	/**
	 * カテゴリ毎の支出総額を取得
	 */
	public GetTotalSpendingResponse getTotalSpending(GetTotalSpendingForm form, GetTotalSpendingResponse res) {

		try {
			// 期間チェック
			checkDateRange(form.getStartMonth(), form.getEndMonth());

			// カテゴリ・サブカテゴリのリレーションチェック
			checkCategoryRelational(form.getUserNo(), form.getCategoryId(), form.getSubCategoryId());

			// データを取得
			List<CategoryList> categoryList = transactionMapper.getTotalSpending(form);
			res.setCategoryTotalList(categoryList);

			BigInteger totalSpending =
					categoryList.stream().map(CategoryList::getCategoryTotalAmount).reduce(BigInteger.ZERO,
							BigInteger::add);
			res.setTotalSpending(totalSpending);
		} catch (CategoryRelationalException | DateException e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(e.getMessage());
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
		}

		return res;
	}

	/** 収支リストエラーチェック */
	private void checkTransactionList(List<Transaction> transactionList,
									  AddTransactionListResponse res) throws SystemException {
		List<Transaction> ErrorList = new ArrayList<>();

		for (Transaction tran : transactionList) {
			// バリデーションチェック
			if (Objects.isNull(tran.getTransactionDate()) ||
					Objects.isNull(tran.getTransactionAmount()) ||
					Objects.isNull(tran.getTransactionSign()) ||
					Objects.isNull(tran.getTransactionName()) ||
					tran.getTransactionName().isBlank() ||
					Objects.isNull(tran.getCategoryId()) ||
					Objects.isNull(tran.getSubCategoryId())
			) {
				ErrorList.add(tran);
				continue;
			}

			try {
				// カテゴリ存在チェック
				checkCategoryExist(tran.getCategoryId());

				// カテゴリ・サブカテゴリのリレーションチェック
				checkCategoryRelational(tran.getUserNo(), tran.getCategoryId(), tran.getSubCategoryId());
			} catch (Exception e) {
				ErrorList.add(tran);
			}
		}

		if (ErrorList.size() > 0) {
			res.setErrorTransaction(ErrorList);
			throw new HasErrorTransactionException(ErrorMessage.TRANSACTION_ERROR_DATA_EXIST);
		}
	}

	/** カテゴリ存在チェック */
	private void checkCategoryExist(Long categoryId) throws SystemException {
		Category categoryParam = new Category();
		categoryParam.setCategoryId(categoryId);
		if (!categoryMapper.isCategoryExist(categoryParam)) {
			throw new DataNotFoundException(ErrorMessage.CATEGORY_NOT_FOUND_ERROR);
		}
	}

	/** サブカテゴリ名検索 */
	private Long findOrCreateSubCategory(Long userNo, Long CategoryId, String subCategoryName) {

		// サブカテゴリテーブルに登録してIDを取得する処理
		SubCategory subCategory = new SubCategory();
		subCategory.setUserNo(userNo);
		subCategory.setCategoryId(CategoryId);
		subCategory.setSubCategoryName(subCategoryName);

		Long subCategoryId = subCategoryMapper.checkSubCategory(subCategory);

		if (!Objects.isNull(subCategoryId)) {
			// 入力したサブカテゴリが存在する場合
			return subCategoryId;
		} else {
			// 新規サブカテゴリ作成
			subCategoryMapper.addSubCategory(subCategory);
			return subCategory.getSubCategoryId();
		}
	}

	/** 収支存在チェック */
	private void checkTransactionExist(Long userNo, Long transactionId) throws SystemException {
		// 収支存在チェック
		Transaction param = new Transaction();
		param.setUserNo(userNo);
		param.setTransactionId(transactionId);
		if (!transactionMapper.isTransactionExist(param)) {
			throw new DataNotFoundException(ErrorMessage.TRANSACTION_DATA_NOT_FOUND);
		}
	}

	/** カテゴリ・サブカテゴリのリレーションチェック */
	private void checkCategoryRelational(Long userNo, Long CategoryId, Long subCategoryId) throws SystemException {

		// カテゴリとサブカテゴリのリレーションをチェック
		Category category = new Category();
		category.setUserNo(userNo);
		category.setCategoryId(CategoryId);
		boolean isCategoryRelational = categoryService.isCategoryRelational(category, subCategoryId);
		if (!isCategoryRelational) {
			throw new CategoryRelationalException(ErrorMessage.CATEGORY_IS_NOT_RELATIONAL);
		}
	}

	/** 日付期間チェック */
	private void checkDateRange(Date startMonth, Date endMonth) throws DateException {
		// 期間の逆転チェック
		if (startMonth.after(endMonth)) {
			throw new DateException(ErrorMessage.DATE_REVERSED_ERROR);
		}

		// 期間の範囲チェック(3年未満)
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startMonth);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endMonth);

		int yearDiff = endCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR);

		if (yearDiff > 2) {
			throw new DateException(ErrorMessage.DATE_RANGE_ERROR);
		}
	}

}
