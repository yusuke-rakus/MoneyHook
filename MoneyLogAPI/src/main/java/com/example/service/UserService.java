package com.example.service;

import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.Status;
import com.example.common.message.ErrorMessage;
import com.example.domain.ChangePasswordResponse;
import com.example.domain.User;
import com.example.form.ChangeEmailForm;
import com.example.form.ChangePasswordForm;
import com.example.form.EditThemeColorForm;
import com.example.form.GetUserInfoForm;
import com.example.form.LoginForm;
import com.example.form.RegistUserForm;
import com.example.mapper.UserMapper;
import com.example.response.ChangeEmailResponse;
import com.example.response.EditThemeColorResponse;
import com.example.response.GetUserInfoResponse;
import com.example.response.LoginResponse;
import com.example.response.RegistUserResponse;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private AuthenticationService authenticationService;

	/** ユーザー登録 */
	public RegistUserResponse registUser(RegistUserForm form) {
		RegistUserResponse res = new RegistUserResponse();

		// UUIDを生成
		UUID uuid = UUID.randomUUID();
		String userId = uuid.toString();
		User user = new User();
		user.setUserId(userId);
		form.setUserId(userId);

		// ユーザー登録処理
		try {
			userMapper.registUser(form);
			res.setUser(user);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.MAIL_ADDRESS_ALREADY_REGISTERED);
		}
		return res;
	}

	/** ログイン */
	public LoginResponse login(LoginForm form) {
		LoginResponse res = new LoginResponse();
		String inputPassword = form.getPassword();

		try {
			User user = userMapper.login(form);

			if (Objects.isNull(user)) {
				throw new NullPointerException();
			} else if (!user.getPassword().equals(inputPassword)) {
				throw new Exception();
			}
			user.setPassword(null);
			res.setUser(user);

		} catch (NullPointerException e) {
			res.setStatus(Status.ERROR.getStatus());
			// メールアドレスをもとにユーザー情報が取得できなかった場合のエラー
			res.setMessage(ErrorMessage.UNREGISTERED_MAIL_ADDRESS);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			// パスワードが不一致の場合のエラー
			res.setMessage(ErrorMessage.PASSWORD_INCORRECT);
		}

		return res;
	}

	/** ユーザー情報の取得 */
	public GetUserInfoResponse getUserInfo(GetUserInfoForm form) {
		GetUserInfoResponse res = new GetUserInfoResponse();

		try {
			User user = userMapper.getUserInfo(form);
			if (Objects.isNull(user)) {
				throw new Exception();
			}
			res.setUserInfo(user);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.USER_INFO_GET_FAILED);
		}

		return res;
	}

	/**
	 * パスワード変更
	 * 
	 * @throws Exception
	 */
	public ChangePasswordResponse changePassword(ChangePasswordForm form) throws Exception {
		ChangePasswordResponse res = new ChangePasswordResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		boolean updateResult = userMapper.changePassword(form);
		if (!updateResult) {
			throw new Exception();
		}

		return res;
	}

	/**
	 * メールアドレスを変更
	 * 
	 * @throws Exception
	 */
	public ChangeEmailResponse changeEmail(ChangeEmailForm form) throws Exception {
		ChangeEmailResponse res = new ChangeEmailResponse();

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		boolean updateResult = userMapper.changeEmail(form);
		if (!updateResult) {
			throw new Exception();
		}

		return res;
	}

	/**
	 * テーマカラー変更
	 * 
	 * @throws Exception
	 */
	public EditThemeColorResponse editThemeColor(EditThemeColorForm form, EditThemeColorResponse res) throws Exception {

		boolean updateResult = userMapper.editThemeColor(form);
		if (!updateResult) {
			throw new Exception();
		}

		return res;
	}

}
