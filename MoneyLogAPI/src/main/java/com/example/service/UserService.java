package com.example.service;

import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.AuthenticationException;
import com.example.common.Message;
import com.example.common.Status;
import com.example.domain.User;
import com.example.form.GetUserInfoForm;
import com.example.form.LoginForm;
import com.example.form.RegistUserForm;
import com.example.mapper.UserMapper;
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
			res.setMessage(Message.MAIL_ADDRESS_ALREADY_REGISTERED.getMessage());
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
			res.setMessage(Message.UNREGISTERED_MAIL_ADDRESS.getMessage());
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			// パスワードが不一致の場合のエラー
			res.setMessage(Message.PASSWORD_INCORRECT.getMessage());
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
			res.setMessage(Message.USER_INFO_GET_FAILED.getMessage());
		}

		return res;
	}

}
