package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.domain.MonthlyTransaction;
import com.example.form.DeleteFixedForm;
import com.example.form.GetDeletedFixedForm;
import com.example.form.GetFixedForm;
import com.example.form.MonthlyTransactionList;
import com.example.form.ReturnTargetForm;

@Mapper
public interface MonthlyTransactionMapper {

	/** 毎月の固定費一覧の取得 */
	public List<MonthlyTransaction> getFixed(GetFixedForm form);

	/** 固定費データの削除(論理) */
	public void deleteFixed(DeleteFixedForm form);

	/** 固定費データの削除(物理) */
	public void deleteFixedFromTable(DeleteFixedForm form);

	/** 計算対象外データを戻す */
	public void returnTarget(ReturnTargetForm form);

	/** 計算対象外の固定費一覧取得 */
	public List<MonthlyTransaction> getDeletedFixed(GetDeletedFixedForm form);

	/** 固定費の登録 */
	public boolean registerFixed(@Param("monthlyTransactionList") List<MonthlyTransactionList> list);

	/** 固定費の更新 */
	public boolean updateFixed(MonthlyTransactionList monthlyTransaction);

}
