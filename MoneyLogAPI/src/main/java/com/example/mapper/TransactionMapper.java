package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.form.AddTransactionForm;
import com.example.form.DeleteTransactionForm;

@Mapper
public interface TransactionMapper {

	/** 収支を登録 */
	public void addTransaction(AddTransactionForm form);

	/** 収支を削除 */
	public void deleteTransaction(DeleteTransactionForm form);

}
