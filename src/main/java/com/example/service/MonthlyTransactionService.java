package com.example.service;

import com.example.common.Status;
import com.example.common.exception.DataNotFoundException;
import com.example.common.exception.SystemException;
import com.example.common.message.Message;
import com.example.domain.Category;
import com.example.domain.MonthlyTransaction;
import com.example.domain.SubCategory;
import com.example.form.*;
import com.example.mapper.MonthlyTransactionMapper;
import com.example.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class MonthlyTransactionService {

	@Autowired
	private MonthlyTransactionMapper monthlyTransactionMapper;

	@Autowired
	SubCategoryService subCategoryService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private Message message;

	private final Integer SUCCESS = 0;

	private final Integer CATEGORY_IS_NOT_RELATIONAL_ERROR = 1;

	/** 毎月の固定費一覧の取得 */
	public GetFixedResponse getFixed(GetFixedForm form, GetFixedResponse res) throws SystemException {

		try {
			List<MonthlyTransaction> monthlyTransactionList = monthlyTransactionMapper.getFixed(form);
			if (monthlyTransactionList.isEmpty()) {
				throw new Exception();
			} else {
				monthlyTransactionList = monthlyTransactionList.stream()
						.sorted(Comparator.comparing(MonthlyTransaction::getMonthlyTransactionId))
						.sorted(Comparator.comparing(MonthlyTransaction::getMonthlyTransactionDate))
						.collect(Collectors.toList());
			}
			res.setMonthlyTransactionList(monthlyTransactionList);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(message.get("error-message.monthly-transaction-not-exists"));
		}
		return res;
	}

	/**
	 * 固定費データの削除
	 */
	public DeleteFixedResponse deleteFixed(DeleteFixedForm form, DeleteFixedResponse res) throws SystemException {

		try {
			monthlyTransactionMapper.deleteFixed(form);
			res.setMessage(message.get("success-message.monthly-transaction-delete-successed"));
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(message.get("error-message.delete-fixed-error"));
		}

		return res;
	}

	/**
	 * 固定費データの削除(物理)
	 */
	public DeleteFixedResponse deleteFixedFromTable(DeleteFixedForm form,
			DeleteFixedResponse res) throws SystemException {

		try {
			monthlyTransactionMapper.deleteFixedFromTable(form);
		} catch (Exception e) {
			throw new SystemException(message.get("error-message.delete-fixed-error"));
		}

		res.setMessage(message.get("success-message.monthly-transaction-delete-from-table-successed"));

		return res;
	}

	/**
	 * 計算対象外データを戻す
	 */
	public ReturnTargetResponse returnTarget(ReturnTargetForm form, ReturnTargetResponse res) throws SystemException {

		try {
			monthlyTransactionMapper.returnTarget(form);
		} catch (Exception e) {
			throw new SystemException(message.get("error-message.system-error"));
		}

		res.setMessage(message.get("success-message.monthly-transaction-back-successed"));

		return res;
	}

	/** 計算対象外の固定費一覧取得 */
	public GetDeletedFixedResponse getDeletedFixed(GetDeletedFixedForm form,
			GetDeletedFixedResponse res) throws SystemException {

		try {
			List<MonthlyTransaction> monthlyTransactionList = monthlyTransactionMapper.getDeletedFixed(form);
			if (monthlyTransactionList.isEmpty()) {
				throw new DataNotFoundException(message.get("error-message.monthly-transaction-not-exists"));
			}
			res.setMonthlyTransactionList(monthlyTransactionList);
		} catch (DataNotFoundException e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(e.getMessage());
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(message.get("error-message.system-error"));
		}

		return res;
	}

	/** 固定費の編集 */
	public EditFixedResponse editFixed(EditFixedForm form, EditFixedResponse res) throws SystemException {

		Integer updateStatus = null;

		try {
			// monthlyTransactionIdを保持しているものを更新処理
			List<MonthlyTransactionList> includingIdList = form.getMonthlyTransactionList().stream()
					.filter(i -> i.getMonthlyTransactionId() != null).collect(Collectors.toList());
			if (!includingIdList.isEmpty()) {
				updateStatus = this.updateFixed(includingIdList);
			}

			// monthlyTransactionIdがないものを登録処理
			List<MonthlyTransactionList> notIncludingIdList = form.getMonthlyTransactionList().stream()
					.filter(i -> i.getMonthlyTransactionId() == null).collect(Collectors.toList());
			if (!notIncludingIdList.isEmpty()) {
				updateStatus = this.registerFixed(notIncludingIdList);
			}

			if (CATEGORY_IS_NOT_RELATIONAL_ERROR.equals(updateStatus)) {
				res.setStatus(Status.ERROR.getStatus());
				res.setMessage(message.get("error-message.category-is-not-relational"));
				return res;
			}

			res.setMessage(message.get("success-message.monthly-transaction-edit-successed"));

		} catch (Exception e) {
			throw new SystemException(message.get("error-message.monthly-transaction-edit-error"));
		}

		return res;
	}

	/** 固定費の編集(1件) */
	public EditOneFixedResponse editOneFixed(EditOneFixedForm form, EditOneFixedResponse res) throws SystemException {

		Integer updateStatus = null;

		try {
			if (form.getMonthlyTransaction().getMonthlyTransactionId() != null) {
				// monthlyTransactionIdを保持しているものを更新処理
				List<MonthlyTransactionList> includingIdList = new ArrayList<>();
				includingIdList.add(form.getMonthlyTransaction());
				if (includingIdList.size() > 0) {
					updateStatus = this.updateFixed(includingIdList);
				}
			} else if (form.getMonthlyTransaction().getMonthlyTransactionId() == null) {
				// monthlyTransactionIdがないものを登録処理
				List<MonthlyTransactionList> notIncludingIdList = new ArrayList<>();
				notIncludingIdList.add(form.getMonthlyTransaction());
				if (notIncludingIdList.size() > 0) {
					updateStatus = this.registerFixed(notIncludingIdList);
				}
			}

			if (CATEGORY_IS_NOT_RELATIONAL_ERROR.equals(updateStatus)) {
				res.setStatus(Status.ERROR.getStatus());
				res.setMessage(message.get("error-message.category-is-not-relational"));
				return res;
			}

			res.setMessage(message.get("success-message.monthly-transaction-edit-successed"));

		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(message.get("error-message.monthly-transaction-edit-error"));
		}

		return res;
	}

	/** 固定費の登録 */
	public Integer registerFixed(List<MonthlyTransactionList> list) throws SystemException {
		// 更新対象のカテゴリ及びサブカテゴリMapを取得
		Map<Long, List<Category>> categoryMap = getCategory(list, list.get(0).getUserNo());

		// サブカテゴリをDBのリストから選択した場合
		List<MonthlyTransactionList> chosenSubCategoryList = list.stream().filter(i -> i.getSubCategoryId() != null)
				.collect(Collectors.toList());
		if (!chosenSubCategoryList.isEmpty()) {
			monthlyTransactionMapper.registerFixed(chosenSubCategoryList);
			return SUCCESS;
		}

		// ユーザー入力のサブカテゴリがある場合
		List<MonthlyTransactionList> userInputSubCategoryList = list.stream()
				.filter(i -> i.getSubCategoryName() != null).filter(i -> !i.getSubCategoryName().isEmpty())
				.collect(Collectors.toList());
		if (!userInputSubCategoryList.isEmpty()) {

			for (MonthlyTransactionList monthlyTran : userInputSubCategoryList) {
				if (Objects.isNull(categoryMap.get(monthlyTran.getCategoryId()))) {
					return CATEGORY_IS_NOT_RELATIONAL_ERROR;
				}
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
		return SUCCESS;
	}

	/** 固定費の更新 */
	public Integer updateFixed(List<MonthlyTransactionList> list) throws SystemException {
		// 更新対象のカテゴリ及びサブカテゴリMapを取得
		Map<Long, List<Category>> categoryMap = getCategory(list, list.get(0).getUserNo());

		// サブカテゴリをDBのリストから選択した場合
		List<MonthlyTransactionList> chosenSubCategoryList = list.stream().filter(i -> i.getSubCategoryId() != null)
				.collect(Collectors.toList());

		if (!chosenSubCategoryList.isEmpty()) {
			for (MonthlyTransactionList monthlyTran : chosenSubCategoryList) {
				List<Category> categoryList = categoryMap.get(monthlyTran.getCategoryId());
				if (categoryList.stream().noneMatch(c -> c.getSubCategoryId().equals(monthlyTran.getSubCategoryId()))) {
					return CATEGORY_IS_NOT_RELATIONAL_ERROR;
				}

				Integer monthlyTransactionAmount = monthlyTran.getMonthlyTransactionAmount();
				Integer sign = monthlyTran.getMonthlyTransactionSign();
				monthlyTran.setMonthlyTransactionAmount(monthlyTransactionAmount * sign);
				monthlyTransactionMapper.updateFixed(monthlyTran);
			}
		}

		// ユーザー入力のサブカテゴリがある場合
		List<MonthlyTransactionList> userInputSubCategoryList = list.stream().filter(i -> i.getSubCategoryId() == null)
				.collect(Collectors.toList());
		if (!userInputSubCategoryList.isEmpty()) {
			for (MonthlyTransactionList monthlyTran : userInputSubCategoryList) {
				if (Objects.isNull(categoryMap.get(monthlyTran.getCategoryId()))) {
					return CATEGORY_IS_NOT_RELATIONAL_ERROR;
				}

				SubCategory subCategory = new SubCategory();
				subCategory.setUserNo(monthlyTran.getUserNo());
				subCategory.setCategoryId(monthlyTran.getCategoryId());
				subCategory.setSubCategoryName(monthlyTran.getSubCategoryName());

				// サブカテゴリの登録
				subCategory = subCategoryService.insertSubCategory(subCategory);
				monthlyTran.setSubCategoryId(subCategory.getSubCategoryId());

				Integer monthlyTransactionAmount = monthlyTran.getMonthlyTransactionAmount();
				Integer sign = monthlyTran.getMonthlyTransactionSign();
				monthlyTran.setMonthlyTransactionAmount(monthlyTransactionAmount * sign);
				monthlyTransactionMapper.updateFixed(monthlyTran);
			}
		}
		return SUCCESS;
	}

	/* カテゴリとサブカテゴリのリレーションチェックを行うためのMapを作成*/
	private Map<Long, List<Category>> getCategory(List<MonthlyTransactionList> mtList, Long userNo) {
		Set<Long> categoryIdSet = mtList.stream().map(MonthlyTransactionList::getCategoryId)
				.collect(Collectors.toSet());
		List<Long> categoryIds = new ArrayList<>(categoryIdSet);
		List<Category> categoryList = categoryService.getList(categoryIds, userNo);

		return categoryList.stream().collect(Collectors.groupingBy(Category::getCategoryId));
	}

}
