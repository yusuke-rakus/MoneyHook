package com.example.common.exception;

/**
 * ユーザー認証を行う際に発生するシステム例外クラスです。
 *
 * @author cyjoh
 */
public class HasErrorTransactionException extends SystemException {

	private static final long serialVersionUID = 1L;

	public HasErrorTransactionException(String msg) {
		super(msg);
	}
}
