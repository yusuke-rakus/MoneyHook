package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.SavingTarget;
import com.example.domain.SubCategory;
import com.example.form.GetSubCategoryListForm;

@Mapper
public interface SavingTargetMapper {

	/** 貯金目標一覧の取得 */
	public List<SavingTarget> getSavingTargetList(GetSavingTargetListForm form);
	
	/** 貯金目標をユーザーNOと目標IDで検索 */
	public SavingTarget findSavingTargetByIdAndUserNo (SavingTarget savingTarget);

	/** 貯金目標をユーザーNOと目標名で検索 */
	public SavingTarget findSavingTargetByNameAndUserNo (SavingTarget savingTarget);

	/** 貯金目標を登録 */
	public Long addSavingTarget(SavingTarget savingTarget);


}
