package com.example.controller;

import com.example.common.Status;
import com.example.common.exception.AlreadyExistsException;
import com.example.common.message.SuccessMessage;
import com.example.domain.SavingTarget;
import com.example.form.*;
import com.example.response.*;
import com.example.service.SavingTargetService;
import com.example.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
	 * @param form 貯金目標検索フォーム
	 * @return res 貯金目標取得結果
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
	 * @param form 削除済み目標検索フォーム
	 * @return res 削除済み目標取得結果
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
	 * @param form 貯金目標登録フォーム
	 * @return res 貯金目標登録結果
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
			SavingTarget savingTarget = savingTargetService.searchByNameAndInsertSavingTarget(form);
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
	 * @param form 貯金目標編集フォーム
	 * @return res 貯金目標編集結果
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
			savingTargetService.editSavingTarget(form);
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
	 * @param form 貯金目標削除フォーム
	 * @return res 貯金目標削除結果
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
	 * @param form 貯金目標戻しフォーム
	 * @return 貯金目標戻し結果
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

		// 貯金目標の有効化
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
	 * @param form 貯金目標削除フォーム
	 * @return result 貯金目標削除結果
	 */
	@PostMapping("/deleteSavingTargetFromTable")
	public DeleteSavingTargetResponse deleteSavingTargetFromTable(@RequestBody @Validated DeleteSavingTargetForm form
			, BindingResult result) throws Throwable {
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

	/**
	 * 貯金目標の並び替え
	 *
	 * @param form 貯金目標並び替えフォーム
	 * @return 貯金目標並び替え結果
	 * @throws Throwable error
	 */
	@PostMapping("/sortSavingTarget")
	public SortSavingTargetResponse sortSavingTarget(@RequestBody SortSavingTargetForm form) throws Throwable {
		SortSavingTargetResponse response = new SortSavingTargetResponse();
		response = savingTargetService.sortNewSavingTarget(form, response);
		return response;
	}
}
