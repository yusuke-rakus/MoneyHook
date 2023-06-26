package com.example.mapper;

import com.example.domain.MonthlySavingData;
import com.example.domain.Saving;
import com.example.form.*;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface SavingMapper {

	/**
	 * 月別貯金一覧の取得
	 */
	public List<Saving> getMonthlySavingList(GetMonthlySavingListForm form);

	/**
	 * 未振り分け貯金一覧の取得
	 */
	public List<Saving> getUncategorizedSavingList(GetSavingListForm form);

	/**
	 * 貯金詳細の取得
	 */
	public Saving load(GetSavingForm form);

	/**
	 * 貯金の編集
	 */
	public void editSaving(EditSavingForm form);

	/**
	 * 貯金の追加
	 */
	public void insertSaving(AddSavingForm form);

	/**
	 * 貯金を削除
	 */
	public void deleteSaving(DeleteSavingForm form);

	/**
	 * 貯金を一括振り分け
	 */
	public void allotSaving(AllotSavingForm form);

	/**
	 * 月別貯金金額をを取得
	 */
	public List<MonthlySavingData> getTotalMonthlySavingAmount(GetTotalSavingForm form);

	/**
	 * 累計貯金金額を取得
	 */
	public BigInteger getTotalSavingAmount(GetTotalSavingForm form);

	/**
	 * 未振り分けの貯金金額を取得
	 */
	public BigInteger getUncategorizedSavingAmount(form form);

	/**
	 * 貯金名の候補
	 */
	public List<Saving> getFrequentSavingName(form form);

	/**
	 * 貯金存在チェック
	 */
	public boolean isSavingExist(Saving param);
}
