package com.example.common;

public enum Message {

	MAIL_ADDRESS_ALREADY_REGISTERED("入力されたメールアドレスは既に登録済みです");

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
