package com.example.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.Message;
import com.example.common.Status;
import com.example.domain.SubCategory;
import com.example.form.AddTransactionForm;
import com.example.mapper.SubCategoryMapper;
import com.example.mapper.TransactionMapper;
import com.example.mapper.UserMapper;
import com.example.response.AddTransactionResponse;

@Service
@Transactional
public class TransactionService {

	@Autowired
	private TransactionMapper transactionMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private SubCategoryMapper subCategoryMapper;

	/** 収支を登録 */
	public AddTransactionResponse addTransaction(AddTransactionForm form) {
		AddTransactionResponse res = new AddTransactionResponse();

		if (Objects.isNull(form.getTransactionDate())) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(Message.TRANSACTION_DATE_EMPTY_ERROR.getMessage());
			return res;
		}
		if (Objects.isNull(form.getAmount())) {
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
		Long UserNo = userMapper.getUserNoFromUserId(form.getUserId());
		form.setUserNo(UserNo);

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

}
