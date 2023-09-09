package com.example.response;

import com.example.common.Status;

public class response {

	/** 初期値にSuccessを設定 */
	private String status = Status.SUCCESS.getStatus();
	private String message;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/** エラーを設定する処理 */
	public response(Boolean result) {
		this.status = Status.ERROR.getStatus();
	}

	public response() {
	}

}
