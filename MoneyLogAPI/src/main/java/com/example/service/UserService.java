package com.example.service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.common.SHA256;
import com.example.common.Status;
import com.example.common.message.ErrorMessage;
import com.example.common.message.SuccessMessage;
import com.example.domain.User;
import com.example.form.ChangeEmailForm;
import com.example.form.ChangePasswordForm;
import com.example.form.EditThemeColorForm;
import com.example.form.GetThemeColorForm;
import com.example.form.GetUserInfoForm;
import com.example.form.LoginForm;
import com.example.form.RegistUserForm;
import com.example.form.SendInquiryForm;
import com.example.mapper.UserMapper;
import com.example.response.ChangeEmailResponse;
import com.example.response.ChangePasswordResponse;
import com.example.response.EditThemeColorResponse;
import com.example.response.GetThemeColorResponse;
import com.example.response.GetUserInfoResponse;
import com.example.response.LoginResponse;
import com.example.response.RegistUserResponse;
import com.example.response.SendInquiryResponse;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private AuthenticationService authenticationService;

	/** ユーザー登録 */
	public RegistUserResponse registUser(RegistUserForm form, RegistUserResponse res) {

		// UUIDを生成
		UUID uuid = UUID.randomUUID();
		String userId = uuid.toString();
		User user = new User();
		user.setUserId(userId);
		form.setUserId(userId);

		// ユーザー登録処理
		try {

			// パスワードをハッシュ化
			form.setPassword(SHA256.getHashedPassword(form.getPassword()));

			userMapper.registUser(form);
			res.setUser(user);
			res.setMessage(SuccessMessage.CREATE_USER_REGISTERD_SUCCESS);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.MAIL_ADDRESS_ALREADY_REGISTERED);
		}
		return res;
	}

	/** ログイン */
	public LoginResponse login(LoginForm form, LoginResponse res) {

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
			res.setMessage(ErrorMessage.EMAIL_OR_PASSWORD_IS_WRONG);
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
	public ChangePasswordResponse changePassword(ChangePasswordForm form, ChangePasswordResponse res) throws Exception {

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		// 現パスワード・新パスワードのハッシュ化
		try {
			form.setPassword(SHA256.getHashedPassword(form.getPassword()));
			form.setNewPassword(SHA256.getHashedPassword(form.getNewPassword()));
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.USER_PASSWORD_CHANGE_FAILED);
		}

		boolean updateResult = userMapper.changePassword(form);
		if (!updateResult) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.USER_PASSWORD_CHANGE_FAILED);
		} else {
			res.setMessage(SuccessMessage.USER_PASSWORD_CHANGED);
		}

		return res;
	}

	/**
	 * メールアドレスを変更
	 * 
	 * @throws Exception
	 */
	public ChangeEmailResponse changeEmail(ChangeEmailForm form, ChangeEmailResponse res) throws Exception {

		// ユーザー認証
		Long userNo = authenticationService.authUser(form);
		form.setUserNo(userNo);

		// パスワードのハッシュ化
		try {
			form.setPassword(SHA256.getHashedPassword(form.getPassword()));
		} catch (Exception e) {
			throw new Exception();
		}

		boolean updateResult = userMapper.changeEmail(form);
		if (!updateResult) {
			throw new Exception();
		} else {
			res.setMessage(SuccessMessage.USER_EMAIL_CHANGED);
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
		} else {
			res.setMessage(SuccessMessage.USER_THEME_COLOR_CHANGED);
		}

		return res;
	}

	/**
	 * テーマカラー取得
	 * 
	 * @throws Exception
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
			res.setMessage(ErrorMessage.USER_INFO_GET_FAILED);
		}

		return res;
	}

	/**
	 * お問い合わせ・ご意見のチェック
	 * 
	 * @throws Exception
	 */
	public SendInquiryResponse checkInquiry(SendInquiryForm form, SendInquiryResponse res) throws Exception {

		try {

			Date date = new Date();
			form.setInquiryDate(new java.sql.Date(date.getTime()));

			// 当日に問い合わせをしていないことを確認
			List<SendInquiryForm> inquiryFormList = userMapper.getInquiry(form);

			// 当日に問い合わせがあればエラー
			if (inquiryFormList.size() > 0) {
				throw new Exception();
			}
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.INQUIRY_OVER_TIMES);
		}

		return res;
	}

	/**
	 * お問い合わせ・ご意見
	 * 
	 * @throws Exception
	 */
	public SendInquiryResponse sendInquiry(SendInquiryForm form, SendInquiryResponse res) throws Exception {

		try {

			Date date = new Date();
			form.setInquiryDate(new java.sql.Date(date.getTime()));

			// 当日に問い合わせをしていないことを確認
			List<SendInquiryForm> inquiryFormList = userMapper.getInquiry(form);

			// 当日に問い合わせがあればエラー
			if (inquiryFormList.size() > 0) {
				throw new Exception();
			}

			// 問い合わせテーブルに登録
			userMapper.insertInquiry(form);
			res.setMessage(SuccessMessage.SEND_INQUIRY_SUCCESS);

		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.INQUIRY_OVER_TIMES);
		}

		return res;
	}

}
