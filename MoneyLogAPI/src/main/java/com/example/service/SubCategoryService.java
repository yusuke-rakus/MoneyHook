package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.AuthenticationException;
import com.example.common.Message;
import com.example.common.Status;
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

	/** カテゴリ一覧の取得 */
	public SubCategoryResponse getSubCategoryList(GetSubCategoryListForm form) {
		SubCategoryResponse res = new SubCategoryResponse();

		// ユーザーIDからユーザーNoを取得
		try {
			Long userNo = authenticationService.authUser(form);
			form.setUserNo(userNo);
		} catch (AuthenticationException e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(Message.AUTHENTICATION_ERROR.getMessage());
			return res;
		}

		try {
			List<SubCategory> subCategoryList = subCategoryMapper.getSubCategoryList(form);
			if (subCategoryList.size() == 0) {
				throw new Exception();
			}
			res.setSubCategoryList(subCategoryList);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(Message.SUB_CATEGORY_GET_FAILED.getMessage());
			return res;
		}

		return res;
	}

}
