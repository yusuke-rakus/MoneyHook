package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.Transaction;
import com.example.form.AddTransactionForm;
import com.example.form.DeleteTransactionForm;
import com.example.form.EditTransactionForm;
import com.example.form.GetMonthlySpendingDataForm;
import com.example.form.GetTransactionForm;

@Mapper
public interface TransactionMapper {

	/** 収支を登録 */
	public void addTransaction(AddTransactionForm form);

	/** 収支を削除 */
	public void deleteTransaction(DeleteTransactionForm form);

	/** 収支を編集 */
	public void editTransaction(EditTransactionForm form);

	/** 収支詳細の取得 */
	public Transaction getTransaction(GetTransactionForm from);

	/** ６ヶ月分の合計支出を取得 */
	public List<Transaction> getMonthlySpendingData(GetMonthlySpendingDataForm form);

}
