package com.example.common;

public enum Message {

	MAIL_ADDRESS_ALREADY_REGISTERED("入力されたメールアドレスは既に登録済みです"), 
	UNREGISTERED_MAIL_ADDRESS("登録されていないメールアドレスです"),
	PASSWORD_INCORRECT("パスワードが間違えています"),
	TRANSACTION_DATE_EMPTY_ERROR("日付を入力してください"),
	AMOUNT_EMPTY_ERROR("金額を入力してください"),
	TRANSACTION_NAME_EMPTY_ERROR("取引名を入力してください"),
	SUB_CATEGORY_ALREADY_REGISTERED("登録されているサブカテゴリです"),
	AUTHENTICATION_ERROR("認証エラーが発生しました"),
	TRANSACTION_DATA_SELECT_FAILED("収支データの取得に失敗しました"),
	CATEGORY_GET_FAILED("カテゴリの取得に失敗しました");

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
