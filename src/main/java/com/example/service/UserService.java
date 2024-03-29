package com.example.service;

import com.example.common.SHA256;
import com.example.common.Status;
import com.example.common.exception.AlreadyExistsException;
import com.example.common.exception.SystemException;
import com.example.common.message.Message;
import com.example.domain.User;
import com.example.form.*;
import com.example.mapper.UserMapper;
import com.example.mapper.UserTokenMapper;
import com.example.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserTokenMapper userTokenMapper;

	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	private Message message;

	/** ログイン */
	public LoginResponse googleSignIn(LoginForm form, LoginResponse res) {

		try {
			// パスワードをハッシュ化
			String hashedPassword = SHA256.getHashedPassword(form.getPassword());
			form.setPassword(hashedPassword);

			// emailをもとに検索実行
			User user = userMapper.login(form);

			if (!user.getPassword().equals(hashedPassword) || Objects.isNull(user)) {
				throw new Exception();
			}
			user.setPassword(null);
			res.setUser(user);

		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			// パスワードが不一致の場合のエラー
			res.setMessage(message.get("error-message.email-or-password-is-wrong"));
		}

		return res;
	}

	/** Googleログイン */
	public GoogleSignInResponse googleSignIn(GoogleSignInForm form, GoogleSignInResponse res) {

		try {
			// ユーザIDがuserテーブルに存在するかチェック
			if (userMapper.isUserExist(form)) {
				// アカウント登録済の場合

				Long userNo = authenticationService.authUser(form);
				form.setUserNo(userNo);

				// トークンを更新
				userTokenMapper.updateToken(form);
			} else {
				// アカウント未登録の場合 新規登録処理
				userMapper.signInWithGoogle(form);
				userTokenMapper.insertToken(form);
			}
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(message.get("error-message.authentication-error"));
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
			res.setMessage(message.get("error-message.user-info-get-failed"));
		}

		return res;
	}

	/**
	 * テーマカラー変更
	 */
	public EditThemeColorResponse editThemeColor(EditThemeColorForm form,
			EditThemeColorResponse res) throws SystemException {

		if (!userMapper.isThemeColorExist(form)) {
			throw new AlreadyExistsException(message.get("error-message.theme-color-not-found"));
		}

		boolean updateResult = userMapper.editThemeColor(form);
		if (!updateResult) {
			throw new SystemException(message.get("error-message.theme-color-not-found"));
		} else {
			res.setMessage(message.get("success-message.user-theme-color-changed"));
		}

		return res;
	}

	/**
	 * テーマカラー取得
	 */
	public GetThemeColorResponse getThemeColor(GetThemeColorForm form, GetThemeColorResponse res) throws Exception {

		try {
			List<User> themeColorList = userMapper.getThemeColor(form);
			if (Objects.isNull(themeColorList)) {
				throw new Exception();
			}
			res.setThemeColorList(themeColorList);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(message.get("error-message.user-info-get-failed"));
		}

		return res;
	}

	/**
	 * お問い合わせ・ご意見のチェック
	 */
	public SendInquiryResponse checkInquiry(SendInquiryForm form, SendInquiryResponse res) throws Exception {

		try {

			Date date = new Date();
			form.setInquiryDate(new java.sql.Date(date.getTime()));

			// 当日に問い合わせをしていないことを確認
			List<SendInquiryForm> inquiryFormList = userMapper.getInquiry(form);

			// 当日に問い合わせがあればエラー
			if (!inquiryFormList.isEmpty()) {
				throw new Exception();
			}
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(message.get("error-message.inquiry-over-times"));
		}

		return res;
	}

	/**
	 * お問い合わせ・ご意見
	 */
	public SendInquiryResponse sendInquiry(SendInquiryForm form, SendInquiryResponse res) throws Exception {

		try {

			Date date = new Date();
			form.setInquiryDate(new java.sql.Date(date.getTime()));

			// 当日に問い合わせをしていないことを確認
			List<SendInquiryForm> inquiryFormList = userMapper.getInquiry(form);

			// 当日に問い合わせがあればエラー
			if (!inquiryFormList.isEmpty()) {
				throw new Exception();
			}

			// 問い合わせテーブルに登録
			userMapper.insertInquiry(form);
			res.setMessage(message.get("success-message.send-inquiry-success"));

		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(message.get("error-message.inquiry-over-times"));
		}

		return res;
	}

}
