package com.example.service;

import com.example.common.exception.AlreadyExistsException;
import com.example.common.exception.DataNotFoundException;
import com.example.common.exception.SystemException;
import com.example.common.message.ErrorMessage;
import com.example.domain.SavingTarget;
import com.example.form.*;
import com.example.mapper.SavingTargetMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class SavingTargetService {

	@Autowired
	private SavingTargetMapper savingTargetMapper;

	@Autowired
	private AuthenticationService authenticationService;

	/**
	 * 貯金目標一覧の取得
	 */
	public List<SavingTarget> getSavingTargetList(GetSavingTargetListForm form) throws SystemException {
		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		return savingTargetMapper.getSavingTargetList(form);
	}

	/**
	 * 貯金金額含めた貯金目標一覧の取得
	 */
	public List<SavingTarget> getSavingTargetListWithSavedAmount(GetSavingTargetListForm form) throws SystemException {
		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		List<SavingTarget> savingTargetList = new ArrayList<>();
		savingTargetList = savingTargetMapper.getSavingTargetListWithSavedAmount(form);

		return savingTargetList;
	}

	/**
	 * 削除済み貯金目標一覧の取得
	 */
	public List<SavingTarget> getDeletedSavingTargetList(GetSavingTargetListForm form) throws SystemException {
		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		return savingTargetMapper.getDeletedSavingTargetList(form);
	}

	/**
	 * 貯金目標の新規追加用メソッド。
	 * 新規追加前に既存の目標の目標名検索を行い、あればそのインスタンスを返却、なければDBに挿入したのち、そのインスタンスを返却します。
	 *
	 * @param form 貯金目標新規追加用情報
	 * @return savingTarget 既存検索で見つかった/新規追加された貯金目標
	 */
	public SavingTarget searchByNameAndInsertSavingTarget(AddSavingTargetForm form)
			throws Throwable {

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
			if (Objects.isNull(searchedSavingTarget)) {
				// 新規登録
				savingTargetMapper.addSavingTarget(savingTarget);
			} else {
				// 既にあれば、その内容を返す用のインスタンスに詰め替え
				BeanUtils.copyProperties(searchedSavingTarget, savingTarget);
				throw new AlreadyExistsException(ErrorMessage.SAVING_TARGET_NAME_DUPLICATED);
			}
		}
		return savingTarget;
	}

	/**
	 * 貯金目標の編集
	 *
	 * @param form 貯金目標編集フォーム
	 * @throws SystemException システムエラー
	 */
	public void editSavingTarget(EditSavingTargetForm form) throws SystemException {
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

	/**
	 * 貯金目標を削除します。
	 *
	 * @param form 貯金目標削除フォーム
	 * @throws SystemException システムエラー
	 */
	public void deleteSavingTarget(DeleteSavingTargetForm form) throws SystemException {
		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		// 貯金目標の存在チェック
		SavingTarget checkForm = new SavingTarget();
		checkForm.setUserNo(form.getUserNo());
		checkForm.setSavingTargetId(form.getSavingTargetId());
		if (!savingTargetMapper.isSavingTargetExist(checkForm)) {
			throw new AlreadyExistsException(ErrorMessage.SAVING_TARGET_NOT_FOUND);
		}

		try {
			// 削除実行
			savingTargetMapper.deleteSavingTarget(form);
		} catch (Exception e) {
			throw new SystemException(ErrorMessage.SAVING_TARGET_DELETE_FAILED);
		}
	}

	/**
	 * 貯金目標を戻します。
	 *
	 * @param form 貯金目標戻しフォーム
	 * @throws SystemException システムエラー
	 */
	public void returnSavingTarget(ReturnSavingTargetForm form) throws SystemException {
		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		// 貯金目標の存在チェック
		SavingTarget checkForm = new SavingTarget();
		checkForm.setUserNo(form.getUserNo());
		checkForm.setSavingTargetId(form.getSavingTargetId());
		if (!savingTargetMapper.isSavingTargetExist(checkForm)) {
			throw new AlreadyExistsException(ErrorMessage.SAVING_TARGET_NOT_FOUND);
		} else {
			savingTargetMapper.returnSavingTarget(form);
		}

	}

	/**
	 * 貯金目標を物理削除します。
	 *
	 * @param form 貯金目標削除フォーム
	 * @throws SystemException システムエラー
	 */
	public void deleteSavingTargetFromTable(DeleteSavingTargetForm form) throws SystemException {
		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		// 貯金目標の存在チェック
		SavingTarget checkForm = new SavingTarget();
		checkForm.setUserNo(form.getUserNo());
		checkForm.setSavingTargetId(form.getSavingTargetId());
		if (!savingTargetMapper.isSavingTargetExist(checkForm)) {
			throw new AlreadyExistsException(ErrorMessage.SAVING_TARGET_NOT_FOUND);
		}

		if (savingTargetMapper.isTargetHasTotalSaved(form) > 0) {
			throw new AlreadyExistsException(ErrorMessage.SAVING_TARGET_HAS_TOTAL_SAVED);
		} else {
			savingTargetMapper.deleteSavingTargetFromTable(form);
		}

	}

	/**
	 * 貯金目標IDとユーザーNOで貯金目標を検索します。
	 *
	 * @param form 貯金目標検索オブジェクト
	 * @return savingTarget 貯金目標オブジェクト
	 * @throws SystemException システムエラー
	 */
	public SavingTarget findSavingTargetByTargetIdAndUserNo(SavingTarget form) throws SystemException {

		SavingTarget savingTarget = savingTargetMapper.findSavingTargetByIdAndUserNo(form);

		// 該当する貯金目標がない場合は、システム例外をスロー
		if (Objects.isNull(savingTarget)) {
			throw new DataNotFoundException(ErrorMessage.SAVING_TARGET_NOT_FOUND);
		}

		return savingTarget;
	}

	/**
	 * 貯金目標名とユーザーNOで貯金目標を検索します。
	 *
	 * @param savingTarget 貯金目標検索オブジェクト
	 * @return savingTarget 貯金目標オブジェクト
	 * @throws SystemException システムエラー
	 */
	public SavingTarget findSavingTargetByTargetNameAndUserNo(SavingTarget savingTarget) throws SystemException {
		return savingTargetMapper.findSavingTargetByNameAndUserNo(savingTarget);
	}
}
