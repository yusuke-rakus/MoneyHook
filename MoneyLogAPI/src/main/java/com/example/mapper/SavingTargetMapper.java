package com.example.mapper;

import com.example.domain.SavingTarget;
import com.example.form.DeleteSavingTargetForm;
import com.example.form.EditSavingTargetForm;
import com.example.form.GetSavingTargetListForm;
import com.example.form.ReturnSavingTargetForm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SavingTargetMapper {

    /**
     * 貯金目標一覧の取得
     */
    public List<SavingTarget> getSavingTargetList(GetSavingTargetListForm form);

    /**
     * 貯金金額含めた目標一覧の取得
     */
    public List<SavingTarget> getSavingTargetListWithSavedAmount(GetSavingTargetListForm form);

    /**
     * 削除済み貯金目標一覧の取得
     */
    public List<SavingTarget> getDeletedSavingTargetList(GetSavingTargetListForm form);

    /**
     * 貯金目標をユーザーNOと目標IDで検索
     */
    public SavingTarget findSavingTargetByIdAndUserNo(SavingTarget savingTarget);

    /**
     * 貯金目標をユーザーNOと目標名で検索
     */
    public SavingTarget findSavingTargetByNameAndUserNo(SavingTarget savingTarget);

    /**
     * 貯金目標を登録
     */
    public Long addSavingTarget(SavingTarget savingTarget);

    /**
     * 貯金目標を編集
     */
    public void editSavingTarget(EditSavingTargetForm form);

    /**
     * 貯金目標を削除(論理)
     */
    public void deleteSavingTarget(DeleteSavingTargetForm form);

    /**
     * 貯金目標を戻す
     */
    public void returnSavingTarget(ReturnSavingTargetForm form);

    /**
     * 貯金目標の存在チェック
     */
    public boolean isSavingTargetExist(ReturnSavingTargetForm form);

    /**
     * 貯金目標を削除(物理)
     */
    public void deleteSavingTargetFromTable(DeleteSavingTargetForm form);

    /**
     * 貯金目標に貯金実績があるか判定
     */
    public Integer isTargetHasTotalSaved(DeleteSavingTargetForm form);

}
