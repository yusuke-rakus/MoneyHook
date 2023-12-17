package com.example.service;

import com.example.common.Status;
import com.example.common.exception.SystemException;
import com.example.common.message.Message;
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

	@Autowired
	private Message message;

	/** サブカテゴリ一覧の取得 */
	public SubCategoryResponse getSubCategoryList(GetSubCategoryListForm form) throws SystemException {
		SubCategoryResponse res = new SubCategoryResponse();

		// ユーザーIDからユーザーNoを取得
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		try {
			List<SubCategory> subCategoryList = subCategoryMapper.getSubCategoryList(form);
			if (subCategoryList.isEmpty()) {
				throw new Exception();
			}
			res.setSubCategoryList(subCategoryList);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(message.get("error-message.sub-category-get-failed"));
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
			res.setMessage(message.get("success-message.transaction-edit-successed"));
		} catch (Exception e) {
			String errorMessage = message.get("error-message.system-error");
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
		}

		return res;
	}
}
