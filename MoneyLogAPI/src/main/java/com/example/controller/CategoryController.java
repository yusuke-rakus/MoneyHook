package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.response.CategoryResponse;
import com.example.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	/** カテゴリ一覧の取得 */
	@PostMapping("/getCategoryList")
	public CategoryResponse getCategoryList() {
		return categoryService.getCategoryList();
	}

}
