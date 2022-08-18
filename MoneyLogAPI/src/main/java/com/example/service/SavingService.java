package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.exception.DataNotFoundException;
import com.example.common.exception.SystemException;
import com.example.common.message.ErrorMessage;
import com.example.domain.Saving;
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
}
