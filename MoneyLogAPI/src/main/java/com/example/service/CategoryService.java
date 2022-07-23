package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.Status;
import com.example.common.message.ErrorMessage;
import com.example.domain.Category;
import com.example.mapper.CategoryMapper;
import com.example.response.CategoryResponse;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryMapper categoryMapper;

	/** カテゴリ一覧の取得 */
	public CategoryResponse getCategoryList() {
		CategoryResponse res = new CategoryResponse();

		try {
			List<Category> categoryList = categoryMapper.getCategoryList();
			if (categoryList.size() == 0) {
				throw new Exception();
			}
			res.setCategoryList(categoryList);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.CATEGORY_GET_FAILED);
			return res;
		}

		return res;
	}

}
