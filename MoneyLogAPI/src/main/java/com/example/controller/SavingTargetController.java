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
import com.example.common.exception.AlreadyExistsException;
import com.example.common.message.SuccessMessage;
import com.example.domain.SavingTarget;
import com.example.form.AddSavingTargetForm;
import com.example.form.DeleteSavingTargetForm;
import com.example.form.EditSavingTargetForm;
import com.example.form.GetSavingTargetListForm;
import com.example.form.ReturnSavingTargetForm;
import com.example.response.AddSavingTargetResponse;
import com.example.response.DeleteSavingTargetResponse;
import com.example.response.EditSavingTargetResponse;
import com.example.response.GetSavingTargetListResponse;
import com.example.response.ReturnSavingTargetResponse;
import com.example.service.SavingTargetService;
import com.example.service.ValidationService;

@RestController
@RequestMapping("/savingTarget")
public class SavingTargetController {

	@Autowired
	private SavingTargetService savingTargetService;

	@Autowired
	private ValidationService validationService;

	/**
	 * 貯金目標一覧を取得
	 * 
	 * @param form
	 * @param result
	 * @return
	 * @throws Throwable
	 */
	@PostMapping("/getSavingTargetList")
	public GetSavingTargetListResponse getSavingTargetList(@RequestBody @Validated GetSavingTargetListForm form,
			BindingResult result) throws Throwable {

		GetSavingTargetListResponse res = new GetSavingTargetListResponse();

		if (result.hasErrors()) {
			String errorMessage = validationService.getFirstErrorMessage(result);
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
			return res;
		}

		List<SavingTarget> savingTargetList = savingTargetService.getSavingTargetList(form);

		res.setMessage(SuccessMessage.SAVING_TARGET_LIST_GET_SUCCESSED);
		res.setSavingTarget(savingTargetList);
		return res;
	}

	/**
	 * 削除済み貯金目標を取得
	 * 
	 * @param form
	 * @param result
	 * @return
	 * @throws Throwable
	 */
	@PostMapping("/getDeletedSavingTarget")
	public GetSavingTargetListResponse getDeletedSavingTarget(@RequestBody @Validated GetSavingTargetListForm form,
			BindingResult result) throws Throwable {
		GetSavingTargetListResponse res = new GetSavingTargetListResponse();

		if (result.hasErrors()) {
			String errorMessage = validationService.getFirstErrorMessage(result);

			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
			return res;
		}

		List<SavingTarget> savingTargetList = savingTargetService.getDeletedSavingTargetList(form);

		res.setMessage(SuccessMessage.DELETED_SAVING_TARGET_LIST_GET_SUCCESSED);
		res.setSavingTarget(savingTargetList);
		return res;
	}

	/**
	 * 貯金目標を登録
	 * 
	 * @throws Throwable
	 */
	@PostMapping("/addSavingTarget")
	public AddSavingTargetResponse addSavingTarget(@RequestBody @Validated AddSavingTargetForm form,
			BindingResult result) throws Throwable {
		AddSavingTargetResponse res = new AddSavingTargetResponse();

		if (result.hasErrors()) {
			String errorMessage = validationService.getFirstErrorMessage(result);

			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
			return res;
		}

		// 既存検索・追加処理
		try {
			SavingTarget savingTarget = savingTargetService.searchByNameAndInsertSavingTarget(form, res);
			res.setSavingTarget(savingTarget);
			res.setMessage(SuccessMessage.SAVING_TARGET_INSERT_SUCCESSED);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(e.getMessage());
		}

		return res;
	}

	/**
	 * 貯金目標の編集
	 * 
	 * @param form
	 * @param result
	 * @return
	 * @throws Throwable
	 */
	@PostMapping("/editSavingTarget")
	public EditSavingTargetResponse editSavingTarget(@RequestBody @Validated EditSavingTargetForm form,
			BindingResult result) throws Throwable {
		EditSavingTargetResponse res = new EditSavingTargetResponse();

		if (result.hasErrors()) {
			String errorMessage = validationService.getFirstErrorMessage(result);

			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
			return res;
		}

		try {
			savingTargetService.editSavingTarget(form, res);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(e.getMessage());
			return res;
		}

		res.setMessage(SuccessMessage.SAVING_TARGET_EDIT_SUCCESSED);
		return res;
	}

	/**
	 * 貯金目標を削除(論理)
	 * 
	 * @param form
	 * @param result
	 * @return
	 * @throws Throwable
	 */
	@PostMapping("/deleteSavingTarget")
	public DeleteSavingTargetResponse deleteSavingTarget(@RequestBody @Validated DeleteSavingTargetForm form,
			BindingResult result) throws Throwable {
		DeleteSavingTargetResponse res = new DeleteSavingTargetResponse();

		if (result.hasErrors()) {
			String errorMessage = validationService.getFirstErrorMessage(result);

			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
			return res;
		}

		try {
			savingTargetService.deleteSavingTarget(form);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(e.getMessage());
			return res;
		}

		res.setMessage(SuccessMessage.SAVING_TARGET_DELETE_SUCCESSED);
		return res;
	}

	/**
	 * 貯金目標を戻す
	 * 
	 * @param form
	 * @param result
	 * @return
	 * @throws Throwable
	 */
	@PostMapping("/returnSavingTarget")
	public ReturnSavingTargetResponse returnSavingTargetTarget(@RequestBody @Validated ReturnSavingTargetForm form,
			BindingResult result) throws Throwable {
		ReturnSavingTargetResponse res = new ReturnSavingTargetResponse();

		if (result.hasErrors()) {
			String errorMessage = validationService.getFirstErrorMessage(result);

			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
			return res;
		}

		try {
			savingTargetService.returnSavingTarget(form);
		} catch (AlreadyExistsException e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(e.getMessage());
			return res;
		}

		res.setMessage(SuccessMessage.SAVING_TARGET_RETURN_SUCCESSED);
		return res;
	}

	/**
	 * 貯金目標を削除(物理)
	 * 
	 * @param form
	 * @param result
	 * @return
	 * @throws Throwable
	 */
	@PostMapping("/deleteSavingTargetFromTable")
	public DeleteSavingTargetResponse deleteSavingTargetFromTable(@RequestBody @Validated DeleteSavingTargetForm form,
			BindingResult result) throws Throwable {
		DeleteSavingTargetResponse res = new DeleteSavingTargetResponse();

		if (result.hasErrors()) {
			String errorMessage = validationService.getFirstErrorMessage(result);

			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
			return res;
		}

		try {
			savingTargetService.deleteSavingTargetFromTable(form);
		} catch (AlreadyExistsException e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(e.getMessage());
			return res;
		}

		res.setMessage(SuccessMessage.SAVING_TARGET_DELETE_SUCCESSED);
		return res;
	}
}
