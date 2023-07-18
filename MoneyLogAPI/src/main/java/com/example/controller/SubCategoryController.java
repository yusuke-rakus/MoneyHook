package com.example.controller;

import com.example.common.Status;
import com.example.common.exception.SystemException;
import com.example.form.EditSubCategoryForm;
import com.example.form.GetSubCategoryListForm;
import com.example.response.EditSubCategoryResponse;
import com.example.response.SubCategoryResponse;
import com.example.service.SubCategoryService;
import com.example.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subCategory")
public class SubCategoryController {

	@Autowired
	private SubCategoryService subCategoryService;

	@Autowired
	private ValidationService validationService;

	/** サブカテゴリ一覧の取得 */
	@PostMapping("/getSubCategoryList")
	public SubCategoryResponse getSubCategoryList(@RequestBody GetSubCategoryListForm form) throws SystemException {
		return subCategoryService.getSubCategoryList(form);
	}

	/** サブカテゴリの編集 */
	@PostMapping("/editSubCategory")
	public EditSubCategoryResponse editSubCategory(@RequestBody @Validated EditSubCategoryForm form,
			BindingResult result) throws SystemException {
		EditSubCategoryResponse res = new EditSubCategoryResponse();
		if (result.hasErrors()) {
			String errorMessage = validationService.getFirstErrorMessage(result);
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
			return res;
		}
		return subCategoryService.editSubCategory(form, res);
	}
}
