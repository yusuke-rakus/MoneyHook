package com.example.mapper;

import com.example.domain.User;
import com.example.form.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

	/** ユーザーIDからユーザーNoを取得 */
	public Long getUserNoFromUserId(String UserId);

	/** Googleで登録 */
	public void signInWithGoogle(GoogleSignInForm form);

	/** ユーザ存在チェック */
	public boolean isUserExist(GoogleSignInForm form);

	/** ログイン */
	public User login(LoginForm form);

	/** ユーザー情報の取得 */
	public User getUserInfo(GetUserInfoForm form);

	/** テーマカラーの変更 */
	public boolean editThemeColor(EditThemeColorForm form);

	/** テーマカラーの取得 */
	public List<User> getThemeColor(GetThemeColorForm form);

	/** 当日に問い合わせをしたかチェック */
	public List<SendInquiryForm> getInquiry(SendInquiryForm form);

	/** 問い合わせを登録 */
	public void insertInquiry(SendInquiryForm form);

	/** テーマカラーの存在チェック */
	public boolean isThemeColorExist(EditThemeColorForm form);

}
