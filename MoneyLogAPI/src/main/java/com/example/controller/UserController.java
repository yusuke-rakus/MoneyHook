package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.common.Status;
import com.example.form.ChangeEmailForm;
import com.example.form.ChangePasswordForm;
import com.example.form.EditThemeColorForm;
import com.example.form.GetThemeColorForm;
import com.example.form.GetUserInfoForm;
import com.example.form.LoginForm;
import com.example.form.RegistUserForm;
import com.example.response.ChangeEmailResponse;
import com.example.response.ChangePasswordResponse;
import com.example.response.EditThemeColorResponse;
import com.example.response.GetThemeColorResponse;
import com.example.response.GetUserInfoResponse;
import com.example.response.LoginResponse;
import com.example.response.RegistUserResponse;
import com.example.service.AuthenticationService;
import com.example.service.UserService;
import com.example.service.ValidationService;

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

		return userService.login(form, res);
	}

	/** ユーザー情報の取得 */
	@PostMapping("/getUserInfo")
	public GetUserInfoResponse getUserInfo(@RequestBody GetUserInfoForm form) {
		return userService.getUserInfo(form);
	}

	/**
	 * パスワード変更
	 * 
	 * @throws Exception
	 */
	@PostMapping("/changePassword")
	public ChangePasswordResponse changePassword(@RequestBody @Validated ChangePasswordForm form, BindingResult result)
			throws Exception {

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
	 * 
	 * @throws Exception
	 */
	@PostMapping("/changeEmail")
	public ChangeEmailResponse changeEmail(@RequestBody @Validated ChangeEmailForm form, BindingResult result)
			throws Exception {
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
	 * 
	 * @throws Exception
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
	 * 
	 * @throws Exception
	 */
	@PostMapping("/getThemeColor")
	public GetThemeColorResponse getThemeColor(@RequestBody GetThemeColorForm form) throws Exception {
		GetThemeColorResponse res = new GetThemeColorResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		return userService.getThemeColor(form, res);
	}

}
