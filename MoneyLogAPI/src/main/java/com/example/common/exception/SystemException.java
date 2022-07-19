package com.example.common.exception;

/**
 * アプリケーション全体におけるシステム例外の基底クラスです。
 * システム例外を実装する際はこのクラスを継承してください。
 * 
 * @author cyjoh
 *
 */
public class SystemException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public SystemException(String msg) {
		super(msg);
	}
}
