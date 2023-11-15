package com.example.service;

import com.example.common.exception.AlreadyExistsException;
import com.example.common.exception.DataNotFoundException;
import com.example.common.exception.SystemException;
import com.example.common.message.Message;
import com.example.domain.SavingTarget;
import com.example.form.*;
import com.example.mapper.SavingTargetMapper;
import com.example.response.SortSavingTargetResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class SavingTargetService {

	@Autowired
	private SavingTargetMapper savingTargetMapper;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private Message message;

	/**
	 * 並び替えの際にMAPで使うEnum
	 */
	private enum SortNoTarget {
		OldTarget, NewTarget
	}

	/**
	 * 貯金目標一覧の取得
	 */
	public List<SavingTarget> getSavingTargetList(GetSavingTargetListForm form) throws SystemException {
		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);

		return savingTargetMapper.getSavingTargetList(userNo);
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

			if (Objects.isNull(searchedSavingTarget)) {
				//有効な貯金目標を全て取得
				List<SavingTarget> savingTargetList = savingTargetMapper.getSavingTargetList(userNo);
				savingTarget.setSortNo(savingTargetList.size() + 1);
				// 新規登録
				savingTargetMapper.addSavingTarget(savingTarget);
			} else {
				// 既にあれば、その内容を返す用のインスタンスに詰め替え
				BeanUtils.copyProperties(searchedSavingTarget, savingTarget);
				throw new AlreadyExistsException(message.get("error-message.saving-target-name-duplicated"));
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
			if (!Objects.isNull(searchedSavingTarget) && !(savingTarget.getSavingTargetId()
					.equals(searchedSavingTarget.getSavingTargetId()))) {
				// 既にあり、それが変更対象以外であれば、編集に失敗したことを返す
				throw new AlreadyExistsException(message.get("error-message.saving-target-name-duplicated"));
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
		SavingTarget deleteTarget = findSavingTargetByTargetIdAndUserNo(checkForm);
		deleteTarget.setUserNo(userNo);

		try {
			//ソート順を変更する貯金目標を取得
			List<SavingTarget> changeTargetList = savingTargetMapper.getSavingTargetListBySortNo(deleteTarget);
			// 削除実行
			savingTargetMapper.deleteSavingTarget(checkForm);
			if (!changeTargetList.isEmpty()) {
				updateAnotherTarget(changeTargetList, userNo, false);
			}

		} catch (Exception e) {
			throw new SystemException(message.get("error-message.saving-target-delete-failed"));
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
			throw new AlreadyExistsException(message.get("error-message.saving-target-not-found"));
		} else {
			List<SavingTarget> savingTargetList = savingTargetMapper.getSavingTargetList(userNo);
			checkForm.setSortNo(savingTargetList.size() + 1);
			savingTargetMapper.returnSavingTarget(checkForm);
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
			throw new AlreadyExistsException(message.get("error-message.saving-target-not-found"));
		}

		if (savingTargetMapper.isTargetHasTotalSaved(form) > 0) {
			throw new AlreadyExistsException(message.get("error-message.saving-target-has-total-saved"));
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
			throw new DataNotFoundException(message.get("error-message.saving-target-not-found"));
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

	/**
	 * 貯金目標の並び替えをします。
	 *
	 * @param form 並び替えオブジェクト
	 */
	public SortSavingTargetResponse sortNewSavingTarget(SortSavingTargetForm form,
			SortSavingTargetResponse response) throws SystemException {

		Long userNo = authenticationService.authUser(form);
		List<SavingTarget> dbTargetList = savingTargetMapper.getSavingTargetList(userNo);

		//ソートNoが変わる対象オブジェクトを探す
		Map<SortNoTarget, SavingTarget> map = compareSavingTarget(dbTargetList, form.getSavingTargetList());
		//並び替えが発生しない場合は正常終了とする
		if (Objects.isNull(map)) {
			response.setMessage(message.get("success-message.saving-target-edit-sort-no-successed"));
			return response;
		}

		try {
			//影響受ける他のオブジェクトを先に更新
			updateTargetFilter(dbTargetList, map.get(SortNoTarget.OldTarget)
					.getSortNo(), map.get(SortNoTarget.NewTarget).getSortNo(), userNo);
			//対象のオブジェクトの更新
			SavingTarget newSortTarget = map.get(SortNoTarget.NewTarget);
			newSortTarget.setUserNo(userNo);
			savingTargetMapper.updateSavingTargetSortNo(newSortTarget);
			response.setMessage(message.get("success-message.saving-target-edit-sort-no-successed"));
		} catch (Exception e) {
			throw new SystemException(message.get("error-message.saving-target-update-sort-no-failed"));
		}

		return response;
	}

	/**
	 * リクエストの貯金目標とDBの貯金目標を比べて、更新対象を探します。
	 *
	 * @param dbTargetList  　DBの貯金目標
	 * @param newTargetList 　リクエストの貯金目標
	 * @return Map<SortNoTarget, SavingTarget> 変更があったオブジェクトをもったMAP or 変更がなければnullを返す
	 */
	private Map<SortNoTarget, SavingTarget> compareSavingTarget(List<SavingTarget> dbTargetList,
			List<SavingTarget> newTargetList) {
		Map<SortNoTarget, SavingTarget> map = new HashMap<>();

		for (SavingTarget newTarget : newTargetList) {
			for (SavingTarget dbTarget : dbTargetList) {
				if (Objects.equals(newTarget.getSavingTargetId(), dbTarget.getSavingTargetId()) && !Objects.equals(newTarget.getSortNo(), dbTarget.getSortNo())) {
					map.put(SortNoTarget.OldTarget, dbTarget);
					map.put(SortNoTarget.NewTarget, newTarget);
					return map;
				}
			}
		}
		return null;
	}


	/**
	 * 貯金目標の並び替えによって受ける他データを判別します。
	 *
	 * @param savingTargetList 　更新対象の貯金目標
	 * @param oldSortNo        変更前のソート順
	 * @param newSortNo        変更後のソート順
	 * @param userNo           current-user userNo
	 */
	private void updateTargetFilter(List<SavingTarget> savingTargetList, int oldSortNo, int newSortNo, Long userNo) {
		List<SavingTarget> updateSavingTargetList;
		//新旧のソートNoの大きさを比較
		if (newSortNo < oldSortNo) {
			updateSavingTargetList = savingTargetList.stream()
					.filter(saving -> saving.getSortNo() < oldSortNo && saving.getSortNo() >= newSortNo)
					.collect(Collectors.toList());
			updateAnotherTarget(updateSavingTargetList, userNo, true);
		} else {
			updateSavingTargetList = savingTargetList.stream()
					.filter(saving -> saving.getSortNo() > oldSortNo && saving.getSortNo() <= newSortNo)
					.collect(Collectors.toList());
			updateAnotherTarget(updateSavingTargetList, userNo, false);
		}

	}

	/**
	 * 貯金目標の並び替えによって影響する他データを更新します。
	 */
	private void updateAnotherTarget(List<SavingTarget> updateList, Long userNo, boolean upFlg) {
		//更新処理を分岐
		if (upFlg) {
			for (SavingTarget target : updateList) {
				target.setSortNo(target.getSortNo() + 1);
				target.setUserNo(userNo);
				savingTargetMapper.updateSavingTargetSortNo(target);
			}
		} else {
			for (SavingTarget target : updateList) {
				target.setSortNo(target.getSortNo() - 1);
				target.setUserNo(userNo);
				savingTargetMapper.updateSavingTargetSortNo(target);
			}
		}
	}
}
