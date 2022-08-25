package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.exception.DataNotFoundException;
import com.example.common.exception.SystemException;
import com.example.common.message.ErrorMessage;
import com.example.domain.Saving;
import com.example.domain.SavingTarget;
import com.example.form.AddSavingForm;
import com.example.form.EditSavingForm;
import com.example.form.GetMonthlySavingListForm;
import com.example.form.GetSavingForm;
import com.example.mapper.SavingMapper;

@Service
@Transactional
public class SavingService {

	@Autowired
	private SavingMapper savingMapper;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private SavingTargetService savingTargetService;

	/** 月別貯金一覧の取得 */
	public List<Saving> getMonthlySavingList(GetMonthlySavingListForm form) throws SystemException {
		List<Saving> savingList = new ArrayList<>();

		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		savingList = savingMapper.getMonthlySavingList(form);

		return savingList;
	}

	/** 月別貯金一覧の取得 */
	public Saving getSavingDetailBySavingId(GetSavingForm form) throws SystemException {
		Saving saving = new Saving();

		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		saving = savingMapper.load(form);

		if (Objects.isNull(saving)) {
			throw new DataNotFoundException(ErrorMessage.SAVING_DATA_SELECT_FAILED);
		}

		return saving;
	}

	/** 貯金の編集 */
	public void editSaving(EditSavingForm form) throws SystemException {

		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		// 貯金目標(振り分け先)を変更する場合
		if (!Objects.isNull(form.getSavingTargetId())) {

			SavingTarget savingTarget = new SavingTarget();
			BeanUtils.copyProperties(form, savingTarget);

			// IDでの指定
			// 対象としているsavingTargetIdを持ち、かつリクエスト元のuserNoを持つデータが有るかを検索
			savingTarget = savingTargetService.findSavingTargetByTargetIdAndUserNo(savingTarget);
		}

		try {
			savingMapper.editSaving(form);
		} catch (Exception e) {
			throw new SystemException(ErrorMessage.SAVING_DATA_EDIT_FAILED);
		}
	}

	/** 貯金の登録 */
	public void insertSaving(AddSavingForm form) throws SystemException {

		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		// 貯金目標(振り分け先)を登録する場合
		if (!Objects.isNull(form.getSavingTargetId())) {

			SavingTarget savingTarget = new SavingTarget();
			BeanUtils.copyProperties(form, savingTarget);

			// IDでの指定
			// 対象としているsavingTargetIdを持ち、かつリクエスト元のuserNoを持つデータが有るかを検索
			savingTarget = savingTargetService.findSavingTargetByTargetIdAndUserNo(savingTarget);
		}

		try {
			savingMapper.insertSaving(form);
		} catch (Exception e) {
			throw new SystemException(ErrorMessage.SAVING_DATA_INSERT_FAILED);
		}
	}
}
