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
import com.example.common.exception.SystemException;
import com.example.common.message.ErrorMessage;
import com.example.domain.Saving;
import com.example.domain.SavingTarget;
import com.example.form.AddSavingTargetForm;
import com.example.form.DeleteSavingTargetForm;
import com.example.form.EditSavingTargetForm;
import com.example.form.GetMonthlySavingListForm;
import com.example.form.GetSavingTargetListForm;
import com.example.mapper.SavingMapper;
import com.example.mapper.SavingTargetMapper;

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
}
