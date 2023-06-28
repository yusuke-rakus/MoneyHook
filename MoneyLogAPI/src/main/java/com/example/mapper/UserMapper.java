package com.example.mapper;

import com.example.domain.User;
import com.example.form.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

	/** ユーザーIDからユーザーNoを取得 */
	public Long getUserNoFromUserId(String UserId);

	/** ユーザー登録 */
	public void registUser(RegistUserForm form);

	/** Googleで登録 */
	public void signInWithGoogle(GoogleSignInForm form);

	/** ユーザ存在チェック */
	public boolean isUserExist(GoogleSignInForm form);

	/** ログイン */
	public User login(LoginForm form);

	/** ユーザー情報の取得 */
	public User getUserInfo(GetUserInfoForm form);

	/** パスワード変更 */
	public boolean changePassword(ChangePasswordForm form);

	/** メールアドレスを変更 */
	public boolean changeEmail(ChangeEmailForm form);

	/** テーマカラーの変更 */
	public boolean editThemeColor(EditThemeColorForm form);

	/** テーマカラーの取得 */
	public List<User> getThemeColor(GetThemeColorForm form);

	/** 当日に問い合わせをしたかチェック */
	public List<SendInquiryForm> getInquiry(SendInquiryForm form);

	/** 問い合わせを登録 */
	public void insertInquiry(SendInquiryForm form);

	/** パスワードを忘れた場合の再設定メール送信 */
	public User checkEmailExist(ForgotPasswordSendEmailForm form);

	/** パスワード再設定パラメータの設定 */
	public void setResetPasswordParam(ForgotPasswordSendEmailForm form);

	/** パスワードを忘れた場合の再設定画面表示 */
	public User resetPasswordPage(ResetPasswordPageForm form);

	/** パスワードを忘れた場合の再設定実行 */
	public void resetPassword(ForgotPasswordResetForm form);

	/** リセットパスワードパラメータのリセット */
	public void deletePasswordParam(User form);

	/** テーマカラーの存在チェック */
	public boolean isThemeColorExist(EditThemeColorForm form);

}
