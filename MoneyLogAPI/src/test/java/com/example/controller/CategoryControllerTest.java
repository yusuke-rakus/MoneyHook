package com.example.controller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.mapper.CategoryMapper;

class CategoryControllerTest {

	CategoryController controller = new CategoryController();

	@Autowired
	private CategoryMapper categoryMapper;

	@Test
	void getCategoryList() {
	}
}