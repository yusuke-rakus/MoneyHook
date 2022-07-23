package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * バリデーション結果を処理するサービスです
 * 
 */
@Service
@Transactional
public class ValidationService {

	public String getFirstErrorMessage(BindingResult result) {

		List<ObjectError> errorList = result.getAllErrors();
		String firstErrorMessage = errorList.get(0).getDefaultMessage();

		return firstErrorMessage;
	}
}
