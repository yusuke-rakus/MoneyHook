package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.ChangePasswordResponse;
import com.example.form.ChangeEmailForm;
import com.example.form.ChangePasswordForm;
import com.example.form.EditThemeColorForm;
import com.example.form.GetUserInfoForm;
import com.example.form.LoginForm;
import com.example.form.RegistUserForm;
import com.example.response.ChangeEmailResponse;
import com.example.response.EditThemeColorResponse;
import com.example.response.GetUserInfoResponse;
import com.example.response.LoginResponse;
import com.example.response.RegistUserResponse;
import com.example.service.AuthenticationService;
import com.example.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationService authenticationService;

	/** ユーザー登録 */
	@PostMapping("/registUser")
	public RegistUserResponse registUser(@RequestBody RegistUserForm form) {
		return userService.registUser(form);
	}

	/** ログイン */
	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginForm form) {
		return userService.login(form);
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
	public ChangePasswordResponse changePassword(@RequestBody ChangePasswordForm form) throws Exception {
		return userService.changePassword(form);
	}

	/**
	 * メールアドレスを変更
	 * 
	 * @throws Exception
	 */
	@PostMapping("/changeEmail")
	public ChangeEmailResponse changeEmail(@RequestBody ChangeEmailForm form) throws Exception {
		return userService.changeEmail(form);
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

}
