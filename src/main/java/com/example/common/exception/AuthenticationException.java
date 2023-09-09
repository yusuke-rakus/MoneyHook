package com.example.common.exception;

/**
 * ユーザー認証を行う際に発生するシステム例外クラスです。
 * @author cyjoh
 *
 */
public class AuthenticationException extends SystemException {
	
	private static final long serialVersionUID = 1L;

	public AuthenticationException(String msg) {
		super(msg);
	}
}
