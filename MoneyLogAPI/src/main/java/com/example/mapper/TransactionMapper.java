package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.form.AddTransactionForm;

@Mapper
public interface TransactionMapper {
	
	/** 収支を登録 */
	public void addTransaction(AddTransactionForm form);

}
