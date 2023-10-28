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

	/** ユーザー登録 */
	@PostMapping("/registUser")
	public RegistUserResponse registUser(@RequestBody @Validated RegistUserForm form, BindingResult result) {
		RegistUserResponse res = new RegistUserResponse();

		if (result.hasErrors()) {
			String errorMessage = validationService.getFirstErrorMessage(result);
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
			return res;
		}

		return userService.registUser(form, res);
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
	 * パスワード変更
	 */
	@PostMapping("/changePassword")
	public ChangePasswordResponse changePassword(@RequestBody @Validated ChangePasswordForm form,
			BindingResult result) throws Exception {

		ChangePasswordResponse res = new ChangePasswordResponse();

		if (result.hasErrors()) {
			String errorMessage = validationService.getFirstErrorMessage(result);
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
			return res;
		}

		return userService.changePassword(form, res);
	}

	/**
	 * メールアドレスを変更
	 */
	@PostMapping("/changeEmail")
	public ChangeEmailResponse changeEmail(@RequestBody @Validated ChangeEmailForm form,
			BindingResult result) throws Exception {
		ChangeEmailResponse res = new ChangeEmailResponse();

		if (result.hasErrors()) {
			String errorMessage = validationService.getFirstErrorMessage(result);
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(errorMessage);
			return res;
		}

		return userService.changeEmail(form, res);
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

	/**
	 * パスワードを忘れた場合の再設定メール送信
	 */
	@PostMapping("/forgotPasswordSendEmail")
	public ForgotPasswordSendEmailResponse forgotPasswordSendEmail(
			@RequestBody ForgotPasswordSendEmailForm form) throws Exception {
		ForgotPasswordSendEmailResponse res = new ForgotPasswordSendEmailResponse();

		return userService.forgotPasswordSendEmail(form, res);
	}

	/**
	 * パスワードを忘れた場合の再設定
	 */
	@PostMapping("/forgotPasswordReset")
	public ForgotPasswordResetResponse forgotPasswordReset(@RequestBody ForgotPasswordResetForm form) throws Exception {
		ForgotPasswordResetResponse res = new ForgotPasswordResetResponse();

		return userService.forgotPasswordReset(form, res);
	}

	/**
	 * パスワードを忘れた場合の再設定画面表示
	 */
	@PostMapping("/resetPasswordPage")
	public ResetPasswordPageResponse resetPasswordPage(@RequestBody ResetPasswordPageForm form) throws Exception {
		ResetPasswordPageResponse res = new ResetPasswordPageResponse();

		return userService.resetPasswordPage(form, res);
	}

}
