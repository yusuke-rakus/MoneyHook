package com.example.service;

import com.example.common.Status;
import com.example.common.exception.SystemException;
import com.example.common.message.Message;
import com.example.domain.Category;
import com.example.form.CategoryWithSubCategoryListForm;
import com.example.mapper.CategoryMapper;
import com.example.response.CategoryResponse;
import com.example.response.CategoryWithSubCategoryListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryMapper categoryMapper;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private Message message;

	/** カテゴリ一覧の取得 */
	public CategoryResponse getCategoryList() {
		CategoryResponse res = new CategoryResponse();

		try {
			List<Category> categoryList = categoryMapper.getCategoryList();
			if (categoryList.isEmpty()) {
				throw new Exception();
			}
			res.setCategoryList(categoryList);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(message.get("error-message.category-get-failed"));
			return res;
		}

		return res;
	}

	/** カテゴリ・サブカテゴリのリレーションチェック */
	public boolean isCategoryRelational(Category param, Long subCategoryId) {
		List<Category> categoryList = categoryMapper.getCategoryWithSubCategory(param);
		boolean isCategoryRelational = categoryList.stream().filter(i -> subCategoryId.equals(i.getSubCategoryId()))
				.collect(Collectors.toList()).isEmpty();
		if (isCategoryRelational) {
			return false;
		} else {
			return true;
		}
	}

	/** 　カテゴリ・サブカテゴリの複数取得 */
	public List<Category> getList(List<Long> categoryIds, Long userNo) {
		return categoryMapper.getCategoryWithSubCategoryByIds(categoryIds, userNo);
	}

	/** カテゴリ一覧の取得 */
	public CategoryWithSubCategoryListResponse getCategoryWithSubCategoryList(CategoryWithSubCategoryListForm form,
			CategoryWithSubCategoryListResponse res) throws SystemException {
		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		try {
			List<Category> categoryList = categoryMapper.getCategoryWithSubCategoryList(form);
			if (categoryList.isEmpty()) {
				throw new Exception();
			}
			res.setCategoryList(categoryList);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(message.get("error-message.category-get-failed"));
			return res;
		}

		return res;
	}

}
