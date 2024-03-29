package com.example.common;

import com.example.common.exception.AuthenticationException;
import com.example.common.message.Message;
import com.example.response.ExceptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * 本アプリケーションの例外を処理するクラスです。
 * 例外発生時はレスポンスを返します。
 *
 * @author cyjoh
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@Autowired
	private Message message;

	/**
	 * 定義されていない例外を処理します。
	 *
	 * @param 各Exceptionクラス
	 * @return 失敗結果
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ExceptionResponse handleSystemExceptionError(Exception e) {
		ExceptionResponse res = new ExceptionResponse();
		res.setStatus(Status.ERROR.getStatus());
		/* エラーメッセージがセットされている場合の分岐処理を追加*/
		if (!Objects.isNull(e.getMessage())) {
			res.setMessage(e.getMessage());
		} else {
			res.setMessage(message.get("error-message.system-error"));
		}
		return res;
	}

	/**
	 * ユーザー認証エラーを処理します。
	 *
	 * @return 失敗結果
	 */
	@ExceptionHandler(AuthenticationException.class)
	@ResponseBody
	public ExceptionResponse handleAuthenticationError() {
		ExceptionResponse res = new ExceptionResponse();
		res.setStatus(Status.ERROR.getStatus());
		res.setMessage(message.get("error-message.authentication-error"));
		return res;
	}

}
