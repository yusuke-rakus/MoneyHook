package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.MonthlyTransaction;
import com.example.form.DeleteFixedForm;
import com.example.form.GetFixedForm;

@Mapper
public interface MonthlyTransactionMapper {

	/** 毎月の固定費一覧の取得 */
	public List<MonthlyTransaction> getFixed(GetFixedForm form);

	/** 固定費データの削除 */
	public void deleteFixed(DeleteFixedForm form);

}
