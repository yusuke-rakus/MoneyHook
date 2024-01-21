package com.example.controller;

import com.example.common.Status;
import com.example.form.*;
import com.example.response.*;
import com.example.service.AuthenticationService;
import com.example.service.UserService;
import com.example.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private ValidationService validationService;

	@PostMapping("/hello")
	public String hello() {
		return "Hello!";
	}

	/** ログイン */
	@PostMapping("/login")
	public LoginResponse login(@RequestBody @Validated LoginForm form, BindingResult result) {

		LoginResponse res = new LoginResponse();

		if (result.hasErrors()) {
			String errorMessage = validationService.getFirstErrorMessage(result);
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
			return res;
		}

		return userService.googleSignIn(form, res);
	}

	/** Googleログイン */
	@PostMapping("/googleSignIn")
	public GoogleSignInResponse googleSignIn(@RequestHeader(name = "Authorization") String token,
			@RequestBody @Validated GoogleSignInForm form, BindingResult result) {

		GoogleSignInResponse res = new GoogleSignInResponse();
		form.setToken(token);

		if (result.hasErrors()) {
			String errorMessage = validationService.getFirstErrorMessage(result);
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
			return res;
		}

		return userService.googleSignIn(form, res);
	}

	/** ユーザー情報の取得 */
	@PostMapping("/getUserInfo")
	public GetUserInfoResponse getUserInfo(@RequestBody GetUserInfoForm form) {
		return userService.getUserInfo(form);
	}


	/**
	 * テーマカラーの変更
	 */
	@PostMapping("/editThemeColor")
	public EditThemeColorResponse editThemeColor(@RequestBody EditThemeColorForm form) throws Exception {
		EditThemeColorResponse res = new EditThemeColorResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		return userService.editThemeColor(form, res);
	}

	/**
	 * テーマカラーの取得
	 */
	@PostMapping("/getThemeColor")
	public GetThemeColorResponse getThemeColor(@RequestBody GetThemeColorForm form) throws Exception {
		GetThemeColorResponse res = new GetThemeColorResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		return userService.getThemeColor(form, res);
	}

	/**
	 * お問い合わせ・ご意見のチェック
	 */
	@PostMapping("/checkInquiry")
	public SendInquiryResponse checkInquiry(@Validated @RequestBody SendInquiryForm form,
			BindingResult result) throws Exception {
		SendInquiryResponse res = new SendInquiryResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		return userService.checkInquiry(form, res);
	}

	/**
	 * お問い合わせ・ご意見
	 */
	@PostMapping("/sendInquiry")
	public SendInquiryResponse sendInquiry(@Validated @RequestBody SendInquiryForm form,
			BindingResult result) throws Exception {
		SendInquiryResponse res = new SendInquiryResponse();

		if (result.hasErrors()) {
			String errorMessage = validationService.getFirstErrorMessage(result);
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
			return res;
		}

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		return userService.sendInquiry(form, res);
	}
}
