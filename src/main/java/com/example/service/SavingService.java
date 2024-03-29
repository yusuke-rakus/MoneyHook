package com.example.service;

import com.example.common.DateFormatter;
import com.example.common.exception.DataNotFoundException;
import com.example.common.exception.SystemException;
import com.example.common.message.Message;
import com.example.domain.MonthlySavingData;
import com.example.domain.Saving;
import com.example.domain.SavingTarget;
import com.example.form.*;
import com.example.mapper.SavingMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class SavingService {

	@Autowired
	private SavingMapper savingMapper;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private SavingTargetService savingTargetService;

	@Autowired
	private Message message;

	/**
	 * 月別貯金一覧の取得
	 */
	public List<Saving> getMonthlySavingList(GetMonthlySavingListForm form) throws SystemException {
		List<Saving> savingList = new ArrayList<>();

		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);
		form.setMonth(DateFormatter.toFirstDayOfMonth(form.getMonth()));

		savingList = savingMapper.getMonthlySavingList(form);

		return savingList;
	}

	/**
	 * 月別貯金一覧の取得
	 */
	public Saving getSavingDetailBySavingId(GetSavingForm form) throws SystemException {
		Saving saving = new Saving();

		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		saving = savingMapper.load(form);

		if (Objects.isNull(saving)) {
			throw new DataNotFoundException(message.get("error-message.saving-data-select-failed"));
		}

		return saving;
	}

	/**
	 * 貯金の編集
	 */
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

		Saving param = new Saving();
		param.setUserNo(form.getUserNo());
		param.setSavingId(form.getSavingId());
		if (!savingMapper.isSavingExist(param)) {
			throw new DataNotFoundException(message.get("error-message.saving-data-not-found"));
		}

		try {
			savingMapper.editSaving(form);
		} catch (Exception e) {
			throw new SystemException(message.get("error-message.saving-data-edit-failed"));
		}
	}

	/**
	 * 貯金の登録
	 */
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
			throw new SystemException(message.get("error-message.saving-data-insert-failed"));
		}
	}

	/**
	 * 貯金の削除
	 */
	public void deleteSaving(DeleteSavingForm form) throws SystemException {

		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		Saving param = new Saving();
		param.setUserNo(form.getUserNo());
		param.setSavingId(form.getSavingId());
		if (!savingMapper.isSavingExist(param)) {
			throw new DataNotFoundException(message.get("error-message.saving-data-not-found"));
		}

		try {
			savingMapper.deleteSaving(form);
		} catch (Exception e) {
			throw new SystemException(message.get("error-message.saving-data-delete-failed"));
		}
	}

	/**
	 * 貯金の一括振り分け
	 */
	public void allotSaving(AllotSavingForm form) throws SystemException {

		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		SavingTarget savingTarget = new SavingTarget();
		savingTarget.setSavingTargetId(form.getSavingTargetId());
		savingTarget.setUserNo(form.getUserNo());

		// IDでの指定
		// 対象としているsavingTargetIdを持ち、かつリクエスト元のuserNoを持つデータが有るかを検索
		savingTarget = savingTargetService.findSavingTargetByTargetIdAndUserNo(savingTarget);

		try {
			savingMapper.allotSaving(form);
		} catch (Exception e) {
			throw new SystemException(message.get("error-message.saving-data-allot-failed"));
		}
	}

	/**
	 * 未振り分け貯金一覧の取得
	 */
	public List<Saving> getUncategorizedSavingList(GetSavingListForm form) throws SystemException {
		List<Saving> savingList = new ArrayList<>();

		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		savingList = savingMapper.getUncategorizedSavingList(form);

		return savingList;
	}

	/**
	 * 月ごと貯金総額の取得
	 */
	public List<MonthlySavingData> getTotalMonthlySavingAmount(GetTotalSavingForm form) throws SystemException {
		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		List<MonthlySavingData> savingAmountList = new ArrayList<>();

		savingAmountList = savingMapper.getTotalMonthlySavingAmount(form);

		if (savingAmountList.size() < 6) {

			List<MonthlySavingData> savingList = new ArrayList<>();
			// 受け取った月から6ヶ月分の空データを作成
			for (int i = 0; i < 6; i++) {
				LocalDate localDate = form.getMonth().toLocalDate().minusMonths(i);
				MonthlySavingData tempSaving = new MonthlySavingData(Date.valueOf(localDate));
				savingList.add(tempSaving);
			}

			// 取得したデータの値をマッチする月にセット
			for (MonthlySavingData savingData : savingAmountList) {
				savingList.stream().filter(s -> s.getSavingMonth().equals(savingData.getSavingMonth()))
						.forEach(s -> s.setMonthlyTotalSavingAmount(savingData.getMonthlyTotalSavingAmount()));
			}

			// 金額がnullの場合は前月の値をセット
			Integer monthlyTotalSavingAmount = 0;
			for (int i = savingList.size() - 1; i >= 0; i--) {
				if (Objects.isNull(savingList.get(i).getMonthlyTotalSavingAmount())) {
					savingList.get(i).setMonthlyTotalSavingAmount(monthlyTotalSavingAmount);
				}
				monthlyTotalSavingAmount = savingList.get(i).getMonthlyTotalSavingAmount();
			}

			savingAmountList = savingList;
		}

		return savingAmountList;
	}

	/**
	 * 累計貯金金額を取得
	 */
	public BigInteger getTotalSavingAmount(GetTotalSavingForm form) throws SystemException {
		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);
		form.setMonth(DateFormatter.toFirstDayOfMonth(form.getMonth()));

		BigInteger totalAmount = savingMapper.getTotalSavingAmount(form);
		if (Objects.isNull(totalAmount)) {
			totalAmount = BigInteger.ZERO;
		}

		return totalAmount;
	}

	/**
	 * 未振り分け貯金金額を取得
	 */
	public BigInteger getUncategorizedSavingAmount(form form) throws SystemException {
		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		return savingMapper.getUncategorizedSavingAmount(form);
	}

	/**
	 * 貯金名を取得
	 */
	public List<Saving> getFrequentSavingName(form form) throws SystemException {
		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		return savingMapper.getFrequentSavingName(form);
	}

}
