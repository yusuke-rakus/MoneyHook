package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.common.exception.SystemException;
import com.example.form.GetSubCategoryListForm;
import com.example.response.SubCategoryResponse;
import com.example.service.SubCategoryService;

@RestController
@RequestMapping("/subCategory")
public class SubCategoryController {

	@Autowired
	private SubCategoryService subCategoryService;

	/** カテゴリ一覧の取得 */
	@PostMapping("/getSubCategoryList")
	public SubCategoryResponse getSubCategoryList(@RequestBody GetSubCategoryListForm form) throws SystemException {
		return subCategoryService.getSubCategoryList(form);
	}

}
