package com.example.common;

public enum Message {

	/** 入力されたメールアドレスは既に登録済み */
	MAIL_ADDRESS_ALREADY_REGISTERED("入力されたメールアドレスは既に登録済みです"),
	/** メールアドレス未登録 */
	UNREGISTERED_MAIL_ADDRESS("登録されていないメールアドレスです"),
	/** パスワード不一致 */
	PASSWORD_INCORRECT("パスワードが間違えています"),
	/** 日付未入力 */
	TRANSACTION_DATE_EMPTY_ERROR("日付を入力してください"),
	/** 金額未入力 */
	AMOUNT_EMPTY_ERROR("金額を入力してください"),
	/** 取引金額未入力 */
	TRANSACTION_NAME_EMPTY_ERROR("取引名を入力してください"),
	/** サブカテゴリ登録済み */
	SUB_CATEGORY_ALREADY_REGISTERED("登録されているサブカテゴリです"),

	// エラーメッセージ
	/** システムエラー */
	SYSTEM_ERROR("システムエラーが発生しました"),
	/** ユーザー認証エラー */
	AUTHENTICATION_ERROR("認証エラーが発生しました"),
	/** 収支データの取得失敗 */
	TRANSACTION_DATA_SELECT_FAILED("収支データの取得に失敗しました"),
	/** カテゴリデータの取得失敗 */
	CATEGORY_GET_FAILED("カテゴリの取得に失敗しました"),
	/** サブカテゴリデータの取得失敗 */
	SUB_CATEGORY_GET_FAILED("サブカテゴリの取得に失敗しました"),
	/** 月次固定費が存在しない */
	MONTHLY_TRANSACTION_NOT_EXISTS("月次固定費が存在しませんでした"),
	/** ユーザー情報の取得失敗 */
	USER_INFO_GET_FAILED("ユーザー情報の取得に失敗しました"),
	/** 月次取引データの失敗 */
	DELETE_FIXED_ERROR("月次取引データの削除に失敗しました"),
	/** 固定収支の取得に失敗しました */
	MONTHLY_FIXED_SPENDING_GET_FAILED("固定収支の取得に失敗しました"),
	/** タイムラインデータの取得に失敗 */
	TIMELINE_DATA_GET_FAILED("タイムラインデータの取得に失敗");

	private String message;

	private Message(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
