package com.example.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.Status;
import com.example.common.exception.SystemException;
import com.example.common.message.ErrorMessage;
import com.example.domain.SubCategory;
import com.example.form.GetSubCategoryListForm;
import com.example.mapper.SubCategoryMapper;
import com.example.response.SubCategoryResponse;

@Service
@Transactional
public class SubCategoryService {

	@Autowired
	private SubCategoryMapper subCategoryMapper;

	@Autowired
	private AuthenticationService authenticationService;

	/** サブカテゴリ一覧の取得 */
	public SubCategoryResponse getSubCategoryList(GetSubCategoryListForm form) throws SystemException {
		SubCategoryResponse res = new SubCategoryResponse();

		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		try {
			List<SubCategory> subCategoryList = subCategoryMapper.getSubCategoryList(form);
			if (subCategoryList.size() == 0) {
				throw new Exception();
			}
			res.setSubCategoryList(subCategoryList);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.SUB_CATEGORY_GET_FAILED);
			return res;
		}

		return res;
	}

	/** サブカテゴリの登録 */
	public SubCategory insertSubCategory(SubCategory subCategory) {
		SubCategory returnSubCategory = subCategory;

		Long subCategoryId = subCategoryMapper.checkSubCategory(subCategory);

		// 存在しないサブカテゴリは新規追加
		if (Objects.isNull(subCategoryId)) {
			subCategoryMapper.addSubCategory(returnSubCategory);
		}
		// 存在する場合はオブジェクトにIDをセット
		else {
			returnSubCategory.setSubCategoryId(subCategoryId);
		}

		return returnSubCategory;
	}

}
