package com.example.service;

import com.example.common.SHA256;
import com.example.common.Status;
import com.example.common.exception.AlreadyExistsException;
import com.example.common.exception.DataNotFoundException;
import com.example.common.exception.SystemException;
import com.example.common.message.ErrorMessage;
import com.example.common.message.SuccessMessage;
import com.example.domain.User;
import com.example.form.*;
import com.example.mapper.UserMapper;
import com.example.mapper.UserTokenMapper;
import com.example.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
	private SendMailService sendMailService;

	@Autowired
	private ScheduledTackService scheduledService;

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

			// メール送信
			Context context = new Context();
			context.setVariable("email", form.getEmail());
			String email = form.getEmail();
			sendMailService.sendMail(context, email, "【MoneyHook】会員登録が完了しました", "registerUser");

		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.MAIL_ADDRESS_ALREADY_REGISTERED);
		}
		return res;
	}

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
			res.setMessage(ErrorMessage.EMAIL_OR_PASSWORD_IS_WRONG);
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
			res.setMessage(ErrorMessage.AUTHENTICATION_ERROR);
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

			// メール送信
			Context context = new Context();
			context.setVariable("email", form.getEmail());
			String email = form.getEmail();
			sendMailService.sendMail(context, email, "【MoneyHook】メールアドレス変更完了", "changeEmail");
		}

		return res;
	}

	/**
	 * テーマカラー変更
	 */
	public EditThemeColorResponse editThemeColor(EditThemeColorForm form,
			EditThemeColorResponse res) throws SystemException {

		if (!userMapper.isThemeColorExist(form)) {
			throw new AlreadyExistsException(ErrorMessage.THEME_COLOR_NOT_FOUND);
		}

		boolean updateResult = userMapper.editThemeColor(form);
		if (!updateResult) {
			throw new SystemException(ErrorMessage.THEME_COLOR_NOT_FOUND);
		} else {
			res.setMessage(SuccessMessage.USER_THEME_COLOR_CHANGED);
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
			res.setMessage(ErrorMessage.USER_INFO_GET_FAILED);
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

	/**
	 * パスワードを忘れた場合の再設定メール送信
	 */
	public ForgotPasswordSendEmailResponse forgotPasswordSendEmail(ForgotPasswordSendEmailForm form,
			ForgotPasswordSendEmailResponse res) throws Exception {

		try {
			// メール存在チェック
			User user = userMapper.checkEmailExist(form);
			if (Objects.isNull(user)) {
				throw new DataNotFoundException(ErrorMessage.EMAIL_NOT_EXIST_ERROR);
			} else {
				// userテーブルのステータス更新
				String resetPasswordParam = SHA256.getHashedValue(form.getEmail());
				form.setResetPasswordParam(resetPasswordParam);
				userMapper.setResetPasswordParam(form);

				// メール送信
				String baseUrl = "https://money-hooks.com";
				Context context = new Context();
				context.setVariable("url", baseUrl + "/resetPassword?param=" + resetPasswordParam);
				String email = form.getEmail();
				sendMailService.sendMail(context, email, "【MoneyHook】パスワード再設定", "forgotPasswordEmail");

				// パスワードリセットパラメータの削除スケジュール実行
				scheduledService.scheduleDeleteParam(user);
			}
			res.setMessage(SuccessMessage.FORGOT_PASSWORD_EMAIL_SUCCESS);
		} catch (DataNotFoundException e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(e.getMessage());
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.SYSTEM_ERROR);
		}
		return res;
	}

	/**
	 * パスワードを忘れた場合の再設定
	 */
	public ForgotPasswordResetResponse forgotPasswordReset(ForgotPasswordResetForm form,
			ForgotPasswordResetResponse res) throws Exception {

		try {
			// パスワードをハッシュ化
			form.setPassword(SHA256.getHashedPassword(form.getPassword()));
			// パスワード設定とパラメータ削除
			userMapper.resetPassword(form);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
			res.setMessage(ErrorMessage.FORGOT_RESET_PASSWORD_ERROR);
			return res;
		}
		return res;
	}

	/**
	 * パスワードを忘れた場合の再設定画面表示
	 */
	public ResetPasswordPageResponse resetPasswordPage(ResetPasswordPageForm form, ResetPasswordPageResponse res) {

		try {
			User user = userMapper.resetPasswordPage(form);
			if (Objects.isNull(user)) {
				throw new Exception();
			} else {
				res.setEmail(user.getEmail());
			}
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
		}
		return res;
	}

}
