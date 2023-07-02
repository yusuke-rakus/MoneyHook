package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * バリデーション結果を処理するサービスです
 */
@Service
@Transactional
public class ValidationService {

	public String getFirstErrorMessage(BindingResult result) {

		List<ObjectError> errorList = result.getAllErrors();

		return errorList.get(0).getDefaultMessage();
	}
}
