package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.common.Status;
import com.example.common.exception.DataNotFoundException;
import com.example.common.message.SuccessMessage;
import com.example.domain.Saving;
import com.example.form.GetMonthlySavingListForm;
import com.example.form.GetSavingForm;
import com.example.response.GetSavingListResponse;
import com.example.response.GetSavingResponse;
import com.example.service.SavingService;
import com.example.service.ValidationService;

@RestController
@RequestMapping("/saving")
public class SavingController {

	@Autowired
	private SavingService savingService;

	@Autowired
	private ValidationService validationService;

	/**
	 * 月別貯金一覧を取得
	 * 
	 * @param form
	 * @param result
	 * @return
	 * @throws Throwable
	 */
	@PostMapping("/getMonthlySavingData")
	public GetSavingListResponse getSavingTargetList(@RequestBody @Validated GetMonthlySavingListForm form,
			BindingResult result) throws Throwable {

		GetSavingListResponse res = new GetSavingListResponse();

		if (result.hasErrors()) {
			String errorMessage = validationService.getFirstErrorMessage(result);
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
			return res;
		}

		List<Saving> savingList = savingService.getMonthlySavingList(form);

		res.setMessage(SuccessMessage.SAVING_LIST_GET_SUCCESSED);
		res.setSavingList(savingList);
		return res;
	}

	/**
	 * 貯金詳細情報を取得
	 * 
	 * @param form
	 * @param result
	 * @return
	 * @throws Throwable
	 */
	@PostMapping("/getSavingData")
	public GetSavingResponse getSavingTargetList(@RequestBody @Validated GetSavingForm form, BindingResult result)
			throws Throwable {

		GetSavingResponse res = new GetSavingResponse();

		if (result.hasErrors()) {
			String errorMessage = validationService.getFirstErrorMessage(result);
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
			return res;
		}

		Saving saving = new Saving();
		
		try {
			saving = savingService.getSavingDetailBySavingId(form);
		} catch (DataNotFoundException e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(e.getMessage());
			return res;
		}

		res.setMessage(SuccessMessage.SAVING_DATA_GET_SUCCESSED);
		res.setSaving(saving);
		return res;
	}
}
