package com.example.service;

import com.example.common.Status;
import com.example.common.exception.SystemException;
import com.example.common.message.ErrorMessage;
import com.example.common.message.SuccessMessage;
import com.example.domain.SubCategory;
import com.example.form.EditSubCategoryForm;
import com.example.form.GetSubCategoryListForm;
import com.example.mapper.SubCategoryMapper;
import com.example.response.EditSubCategoryResponse;
import com.example.response.SubCategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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

		Long subCategoryId = subCategoryMapper.checkSubCategory(subCategory);

		// 存在しないサブカテゴリは新規追加
		if (Objects.isNull(subCategoryId)) {
			subCategoryMapper.addSubCategory(subCategory);
		}
		// 存在する場合はオブジェクトにIDをセット
		else {
			subCategory.setSubCategoryId(subCategoryId);
		}

		return subCategory;
	}

	/** サブカテゴリの編集 */
	public EditSubCategoryResponse editSubCategory(EditSubCategoryForm form,
			EditSubCategoryResponse res) throws SystemException {
		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		try {
			if (form.isEnable()) {
				// サブカテゴリを表示対象にする
				subCategoryMapper.enableSubCategory(form);
			} else {
				// サブカテゴリを非表示にする
				subCategoryMapper.disableSubCategory(form);
			}
			res.setMessage(SuccessMessage.TRANSACTION_EDIT_SUCCESSED);
		} catch (Exception e) {
			String errorMessage = ErrorMessage.SYSTEM_ERROR;
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
		}

		return res;
	}
}
