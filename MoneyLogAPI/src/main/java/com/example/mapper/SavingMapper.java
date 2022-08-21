package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.Saving;
import com.example.domain.SavingTarget;
import com.example.form.AddSavingForm;
import com.example.form.DeleteSavingForm;
import com.example.form.EditSavingForm;
import com.example.form.GetMonthlySavingListForm;
import com.example.form.GetSavingForm;

@Mapper
public interface SavingMapper {

	/** 月別貯金一覧の取得 */
	public List<Saving> getMonthlySavingList(GetMonthlySavingListForm form);
	
	/** 貯金詳細の取得 */
	public Saving load(GetSavingForm form);
	
	/** 貯金の編集 */
	public void editSaving(EditSavingForm form);
	
}
