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
import com.example.common.exception.SystemException;
import com.example.common.message.SuccessMessage;
import com.example.domain.MonthlySavingData;
import com.example.domain.Saving;
import com.example.domain.SavingTarget;
import com.example.form.AddSavingForm;
import com.example.form.AllotSavingForm;
import com.example.form.DeleteSavingForm;
import com.example.form.EditSavingForm;
import com.example.form.FrequentSavingNameForm;
import com.example.form.GetMonthlySavingListForm;
import com.example.form.GetSavingForm;
import com.example.form.GetSavingListForm;
import com.example.form.GetSavingTargetListForm;
import com.example.form.GetTotalSavingForm;
import com.example.response.AddSavingResponse;
import com.example.response.AllotSavingResponse;
import com.example.response.DeleteSavingResponse;
import com.example.response.EditSavingResponse;
import com.example.response.FrequentSavingNameResponse;
import com.example.response.GetSavingAmountForSavingTargetResponse;
import com.example.response.GetSavingListResponse;
import com.example.response.GetSavingResponse;
import com.example.response.GetTotalSavingResponse;
import com.example.service.SavingService;
import com.example.service.SavingTargetService;
import com.example.service.ValidationService;

@RestController
@RequestMapping("/saving")
public class SavingController {

	@Autowired
	private SavingService savingService;

	@Autowired
	private SavingTargetService savingTargetService;

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
	public GetSavingListResponse getMonthlySavingList(@RequestBody @Validated GetMonthlySavingListForm form,
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
	 * 未振り分けの貯金一覧を取得
	 * 
	 * @param form
	 * @param result
	 * @return
	 * @throws Throwable
	 */
	@PostMapping("/getUncategorizedSaving")
	public GetSavingListResponse getUncategorizedSavingList(@RequestBody @Validated GetSavingListForm form,
			BindingResult result) throws Throwable {

		GetSavingListResponse res = new GetSavingListResponse();

		if (result.hasErrors()) {
			String errorMessage = validationService.getFirstErrorMessage(result);
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
			return res;
		}

		List<Saving> savingList = savingService.getUncategorizedSavingList(form);

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

	/**
	 * 貯金を追加
	 * 
	 * @param form
	 * @param result
	 * @return
	 * @throws Throwable
	 */
	@PostMapping("/addSaving")
	public AddSavingResponse addSaving(@RequestBody @Validated AddSavingForm form, BindingResult result)
			throws Throwable {
		AddSavingResponse res = new AddSavingResponse();

		if (result.hasErrors()) {
			String errorMessage = validationService.getFirstErrorMessage(result);

			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
			return res;
		}

		// 追加処理
		try {
			savingService.insertSaving(form);
		} catch (SystemException e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(e.getMessage());
			return res;
		}

		res.setMessage(SuccessMessage.SAVING_INSERT_SUCCESSED);
		return res;
	}

	/**
	 * 貯金の編集
	 * 
	 * @param form
	 * @param result
	 * @return
	 * @throws Throwable
	 */
	@PostMapping("/editSaving")
	public EditSavingResponse editSaving(@RequestBody @Validated EditSavingForm form, BindingResult result)
			throws Throwable {

		EditSavingResponse res = new EditSavingResponse();

		if (result.hasErrors()) {
			String errorMessage = validationService.getFirstErrorMessage(result);

			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
			return res;
		}

		try {
			savingService.editSaving(form);
		} catch (SystemException e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(e.getMessage());
			return res;
		}

		res.setMessage(SuccessMessage.SAVING_EDIT_SUCCESSED);
		return res;
	}

	/**
	 * 貯金を削除
	 * 
	 * @param form
	 * @return
	 * @throws SystemException
	 */
	@PostMapping("/deleteSaving")
	public DeleteSavingResponse deleteSaving(@RequestBody @Validated DeleteSavingForm form, BindingResult result)
			throws SystemException {
		DeleteSavingResponse res = new DeleteSavingResponse();

		if (result.hasErrors()) {
			String errorMessage = validationService.getFirstErrorMessage(result);

			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
			return res;
		}

		savingService.deleteSaving(form);

		res.setMessage(SuccessMessage.SAVING_DATA_DELETE_SUCCESSED);
		return res;
	}

	/**
	 * 貯金の一括振り分けを行います。
	 * 
	 * @param form
	 * @param result
	 * @return
	 * @throws Throwable
	 */
	@PostMapping("/sortSavingAmount")
	public AllotSavingResponse allotSavingAmountsToNewTarget(@RequestBody @Validated AllotSavingForm form,
			BindingResult result) throws Throwable {

		AllotSavingResponse res = new AllotSavingResponse();

		if (result.hasErrors()) {
			String errorMessage = validationService.getFirstErrorMessage(result);

			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
			return res;
		}

		try {
			savingService.allotSaving(form);
		} catch (SystemException e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(e.getMessage());
			return res;
		}

		res.setMessage(SuccessMessage.SAVING_ALLOT_SUCCESSED);
		return res;
	}

	/**
	 * 貯金総額を取得します。
	 * 
	 * @param form
	 * @param result
	 * @return
	 * @throws Throwable
	 */
	@PostMapping("/getTotalSaving")
	public GetTotalSavingResponse getTotalSaving(@RequestBody @Validated GetTotalSavingForm form, BindingResult result)
			throws Throwable {
		GetTotalSavingResponse res = new GetTotalSavingResponse();

		if (result.hasErrors()) {
			String errorMessage = validationService.getFirstErrorMessage(result);

			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
			return res;
		}

		Integer totalSavingAmount = savingService.getTotalSavingAmount(form);

		List<MonthlySavingData> monthlySavingDataList = savingService.getTotalMonthlySavingAmount(form);

		res.setTotalSavingAmount(totalSavingAmount);
		res.setSavingDataList(monthlySavingDataList);
		res.setMessage(SuccessMessage.SAVING_TOTAL_DATA_GET_SUCCESSED);
		return res;
	}

	/**
	 * 貯金目標ごとの貯金総額を取得します。
	 * 
	 * @param form
	 * @param result
	 * @return
	 * @throws Throwable
	 */
	@PostMapping("/getSavingAmountForTarget")
	public GetSavingAmountForSavingTargetResponse getSavingAmountForTarget(
			@RequestBody @Validated GetSavingTargetListForm form, BindingResult result) throws Throwable {
		GetSavingAmountForSavingTargetResponse res = new GetSavingAmountForSavingTargetResponse();

		if (result.hasErrors()) {
			String errorMessage = validationService.getFirstErrorMessage(result);
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
			return res;
		}

		List<SavingTarget> savingTargetList = savingTargetService.getSavingTargetListWithSavedAmount(form);
		Integer uncategorizedAmount = savingService.getUncategorizedSavingAmount(form);

		res.setSavingTargetList(savingTargetList);
		res.setUncategorizedAmount(uncategorizedAmount);
		res.setMessage(SuccessMessage.SAVING_TARGET_AMOUNT_LIST_GET_SUCCESSED);
		return res;
	}

	/**
	 * 貯金名を取得
	 * 
	 * @param form
	 * @param result
	 * @return
	 * @throws Throwable
	 */
	@PostMapping("/getFrequentSavingName")
	public FrequentSavingNameResponse getFrequentSavingName(@RequestBody @Validated FrequentSavingNameForm form,
			BindingResult result) throws Throwable {
		FrequentSavingNameResponse res = new FrequentSavingNameResponse();

		if (result.hasErrors()) {
			String errorMessage = validationService.getFirstErrorMessage(result);
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
			return res;
		}

		List<Saving> savingList = savingService.getFrequentSavingName(form);

		res.setSavingList(savingList);
		return res;
	}
}
