package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.Status;
import com.example.common.exception.AlreadyExistsException;
import com.example.common.exception.DataNotFoundException;
import com.example.common.exception.SystemException;
import com.example.common.message.ErrorMessage;
import com.example.domain.SavingTarget;
import com.example.form.AddSavingTargetForm;
import com.example.form.DeleteSavingTargetForm;
import com.example.form.EditSavingTargetForm;
import com.example.form.GetSavingTargetListForm;
import com.example.mapper.SavingTargetMapper;

@Service
@Transactional
public class SavingTargetService {

	@Autowired
	private SavingTargetMapper savingTargetMapper;

	@Autowired
	private AuthenticationService authenticationService;

	/** 貯金目標一覧の取得 */
	public List<SavingTarget> getSavingTargetList(GetSavingTargetListForm form) throws SystemException {
		List<SavingTarget> savingTargetList = new ArrayList<>();

		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		savingTargetList = savingTargetMapper.getSavingTargetList(form);

		return savingTargetList;
	}

	/** 削除済み貯金目標一覧の取得 */
	public List<SavingTarget> getDeletedSavingTargetList(GetSavingTargetListForm form) throws SystemException {
		List<SavingTarget> savingTargetList = new ArrayList<>();

		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		savingTargetList = savingTargetMapper.getDeletedSavingTargetList(form);

		return savingTargetList;
	}

	/**
	 * 貯金目標の新規追加用メソッド。
	 * 新規追加前に既存の目標の目標名検索を行い、あればそのインスタンスを返却、なければDBに挿入したのち、そのインスタンスを返却します。
	 * 
	 * @param form 貯金目標新規追加用情報
	 * @return savingTarget 既存検索で見つかった/新規追加された貯金目標
	 * @throws Throwable
	 */
	public SavingTarget searchByNameAndInsertSavingTarget(AddSavingTargetForm form) throws Throwable {

		SavingTarget savingTarget = new SavingTarget();

		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		if (Objects.isNull(form.getSavingTargetId()) && !(Objects.isNull(form.getSavingTargetName()))) {

			savingTarget.setUserNo(form.getUserNo());
			savingTarget.setTargetAmount(form.getTargetAmount());
			savingTarget.setSavingTargetName(form.getSavingTargetName());

			// 名称で検索
			SavingTarget searchedSavingTarget = findSavingTargetByTargetNameAndUserNo(savingTarget);

			if (!Objects.isNull(searchedSavingTarget)) {
				// 既にあれば、その内容を返す用のインスタンスに詰め替え
				BeanUtils.copyProperties(searchedSavingTarget, savingTarget);

			} else {
				// なければ新規登録
				savingTargetMapper.addSavingTarget(savingTarget);
			}

		}
		// 取得失敗している場合はsavingTargetをnullに
		if (Objects.isNull(savingTarget.getSavingTargetId())) {
			savingTarget = null;
		}
		return savingTarget;
	}

	/**
	 * 貯金目標の編集
	 * 
	 * @param form
	 * @return
	 * @throws SystemException
	 */
	public void editSavingTarget(EditSavingTargetForm form) throws SystemException {
		List<SavingTarget> savingTargetList = new ArrayList<>();

		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		// 名称変更をする場合、同名がないか検索
		if (!(Objects.isNull(form.getSavingTargetName()))) {
			SavingTarget savingTarget = new SavingTarget();
			savingTarget.setSavingTargetId(form.getSavingTargetId());
			savingTarget.setUserNo(form.getUserNo());
			savingTarget.setSavingTargetName(form.getSavingTargetName());

			// 名称で検索
			SavingTarget searchedSavingTarget = findSavingTargetByTargetNameAndUserNo(savingTarget);

			if (!Objects.isNull(searchedSavingTarget)
					&& !(savingTarget.getSavingTargetId().equals(searchedSavingTarget.getSavingTargetId()))) {
				// 既にあり、それが変更対象以外であれば、編集に失敗したことを返す
				throw new AlreadyExistsException(ErrorMessage.SAVING_TARGET_NAME_DUPLICATED);
			}
		}

		savingTargetMapper.editSavingTarget(form);
	}

	public void deleteSavingTarget(DeleteSavingTargetForm form) throws SystemException {
		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		savingTargetMapper.deleteSavingTarget(form);

	}

	/**
	 * 貯金目標IDとユーザーNOで貯金目標を検索します。
	 * 
	 * @param savingTargetId
	 * @param userNo
	 * @return
	 * @throws SystemException
	 */
	public SavingTarget findSavingTargetByTargetIdAndUserNo(SavingTarget savingTarget) throws SystemException {

		savingTarget = savingTargetMapper.findSavingTargetByIdAndUserNo(savingTarget);

		// 該当する貯金目標がない場合は、システム例外をスロー
		if (Objects.isNull(savingTarget)) {
			throw new DataNotFoundException(ErrorMessage.SAVING_TARGET_NOT_FOUND);
		}

		return savingTarget;
	}

	/**
	 * 貯金目標名とユーザーNOで貯金目標を検索します。
	 * 
	 * @param savingTargetName
	 * @param userNo
	 * @return
	 * @throws SystemException
	 */
	public SavingTarget findSavingTargetByTargetNameAndUserNo(SavingTarget savingTarget) throws SystemException {

		savingTarget = savingTargetMapper.findSavingTargetByNameAndUserNo(savingTarget);

		// 該当する貯金目標がない場合は、システム例外をスロー
		if (Objects.isNull(savingTarget)) {
			throw new DataNotFoundException(ErrorMessage.SAVING_TARGET_NOT_FOUND);
		}

		return savingTarget;
	}
}
