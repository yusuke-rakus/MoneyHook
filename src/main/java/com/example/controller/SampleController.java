package com.example.controller;

import com.example.response.CategoryResponse;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/")
	public String getCategory() {
		CategoryResponse response = categoryService.getCategoryList();
		System.out.println(response.getCategoryList().get(0).getCategoryName());
		return "OKOKOKOK";
	}
}
