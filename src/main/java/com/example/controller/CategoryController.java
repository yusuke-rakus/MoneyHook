package com.example.controller;

import com.example.common.Status;
import com.example.common.exception.SystemException;
import com.example.form.CategoryWithSubCategoryListForm;
import com.example.response.CategoryResponse;
import com.example.response.CategoryWithSubCategoryListResponse;
import com.example.service.CategoryService;
import com.example.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ValidationService validationService;

	@GetMapping("/")
	public String getCategory() {
		CategoryResponse response = categoryService.getCategoryList();
		return response.getCategoryList().get(0).getCategoryName();
	}

	/** カテゴリ一覧の取得 */
	@PostMapping("/getCategoryList")
	public CategoryResponse getCategoryList() {
		return categoryService.getCategoryList();
	}

	/** カテゴリ・サブカテゴリ一覧の取得 */
	@PostMapping("/getCategoryWithSubCategoryList")
	public CategoryWithSubCategoryListResponse getCategoryWithSubCategoryList(
			@RequestBody @Validated CategoryWithSubCategoryListForm form, BindingResult result) throws SystemException {
		CategoryWithSubCategoryListResponse res = new CategoryWithSubCategoryListResponse();
		if (result.hasErrors()) {
			String errorMessage = validationService.getFirstErrorMessage(result);
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
			return res;
		}
		return categoryService.getCategoryWithSubCategoryList(form, res);
	}

}
