package com.example.service;

import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.Status;
import com.example.common.exception.AuthenticationException;
import com.example.common.message.ErrorMessage;
import com.example.common.message.SuccessMessage;
import com.example.common.message.ValidatingMessage;
import com.example.domain.SavingTarget;
import com.example.form.AddSavingTargetForm;
import com.example.mapper.SavingTargetMapper;
import com.example.response.AddSavingTargetResponse;

@Service
@Transactional
public class SavingTargetService {

	@Autowired
	private SavingTargetMapper savingTargetMapper;

	@Autowired
	private AuthenticationService authenticationService;

	/**
	 * 貯金目標を登録
	 * 
	 * @throws AuthenticationException
	 */
	public AddSavingTargetResponse addSavingTarget(AddSavingTargetForm form) throws Throwable {
		AddSavingTargetResponse res = new AddSavingTargetResponse();

		if (Objects.isNull(form.getSavingTargetName()) || form.getSavingTargetName().isEmpty()) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ValidatingMessage.SAVING_TARGET_NAME_EMPTY_ERROR);
			return res;
		}
		if (Objects.isNull(form.getTargetAmount())) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ValidatingMessage.TRANSACTION_AMOUNT_EMPTY_ERROR);
			return res;
		}

		// 既存検索・追加処理
		SavingTarget savingTarget = searchByNameAndInsertSavingTarget(form);

		// 既存検索・追加に失敗した場合
		if (Objects.isNull(savingTarget)) {
			res.setMessage(ErrorMessage.SAVING_TARGET_INSERT_FAILED);
			return res;
		}

		res.setSavingTarget(savingTarget);
		res.setMessage(SuccessMessage.SAVING_TARGET_INSERT_SUCCESSED);
		return res;
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
			SavingTarget searchedSavingTarget = savingTargetMapper.findSavingTargetByNameAndUserNo(savingTarget);

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

}
