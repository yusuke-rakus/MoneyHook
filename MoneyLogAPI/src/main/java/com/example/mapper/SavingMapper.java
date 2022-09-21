package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.MonthlySavingData;
import com.example.domain.Saving;
import com.example.form.AddSavingForm;
import com.example.form.AllotSavingForm;
import com.example.form.DeleteSavingForm;
import com.example.form.EditSavingForm;
import com.example.form.GetMonthlySavingListForm;
import com.example.form.GetSavingForm;
import com.example.form.GetSavingListForm;
import com.example.form.GetTotalSavingForm;
import com.example.form.form;

@Mapper
public interface SavingMapper {

	/** 月別貯金一覧の取得 */
	public List<Saving> getMonthlySavingList(GetMonthlySavingListForm form);

	/** 未振り分け貯金一覧の取得 */
	public List<Saving> getUncategorizedSavingList(GetSavingListForm form);

	/** 貯金詳細の取得 */
	public Saving load(GetSavingForm form);

	/** 貯金の編集 */
	public void editSaving(EditSavingForm form);

	/** 貯金の追加 */
	public void insertSaving(AddSavingForm form);

	/** 貯金を削除 */
	public void deleteSaving(DeleteSavingForm form);

	/** 貯金を一括振り分け */
	public void allotSaving(AllotSavingForm form);

	/** 月別貯金金額をを取得 */
	public List<MonthlySavingData> getTotalMonthlySavingAmount(GetTotalSavingForm form);

	/** 累計貯金金額を取得 */
	public Integer getTotalSavingAmount(GetTotalSavingForm form);

	/** 未振り分けの貯金金額を取得 */
	public Integer getUncategorizedSavingAmount(form form);

	/** 貯金名の候補 */
	public List<Saving> getFrequentSavingName(form form);

}
