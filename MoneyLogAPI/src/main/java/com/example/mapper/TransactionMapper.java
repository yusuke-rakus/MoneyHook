package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.Transaction;
import com.example.form.AddTransactionForm;
import com.example.form.DeleteTransactionForm;
import com.example.form.GetTransactionForm;

@Mapper
public interface TransactionMapper {

	/** 収支を登録 */
	public void addTransaction(AddTransactionForm form);

	/** 収支を削除 */
	public void deleteTransaction(DeleteTransactionForm form);

	/** 収支詳細の取得 */
	public Transaction getTransaction(GetTransactionForm from);

}
