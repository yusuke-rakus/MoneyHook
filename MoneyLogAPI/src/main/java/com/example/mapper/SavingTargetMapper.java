package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.SavingTarget;
import com.example.form.DeleteSavingTargetForm;
import com.example.form.EditSavingTargetForm;
import com.example.form.EditTransactionForm;
import com.example.form.GetSavingTargetListForm;

@Mapper
public interface SavingTargetMapper {

	/** 貯金目標一覧の取得 */
	public List<SavingTarget> getSavingTargetList(GetSavingTargetListForm form);

	/** 貯金金額含めた目標一覧の取得 */
	public List<SavingTarget> getSavingTargetListWithSavedAmount(GetSavingTargetListForm form);
	
	/** 削除済み貯金目標一覧の取得 */
	public List<SavingTarget> getDeletedSavingTargetList(GetSavingTargetListForm form);
	
	/** 貯金目標をユーザーNOと目標IDで検索 */
	public SavingTarget findSavingTargetByIdAndUserNo (SavingTarget savingTarget);

	/** 貯金目標をユーザーNOと目標名で検索 */
	public SavingTarget findSavingTargetByNameAndUserNo (SavingTarget savingTarget);

	/** 貯金目標を登録 */
	public Long addSavingTarget(SavingTarget savingTarget);
	
	/** 貯金目標を編集 */
	public void editSavingTarget (EditSavingTargetForm form);

	/** 貯金目標を削除 */
	public void deleteSavingTarget(DeleteSavingTargetForm form);


}
