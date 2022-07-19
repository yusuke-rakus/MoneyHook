package com.example.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.common.exception.AuthenticationException;
import com.example.response.ExceptionResponse;

/**
 * 本アプリケーションの例外を処理するクラスです。
 * 例外発生時はレスポンスを返します。
 * 
 * @author cyjoh
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler  {

    /**
     * 定義されていない例外を処理します。
     * 
     * @return 失敗結果
     */
    @ExceptionHandler( Exception.class )
	@ResponseBody
    public ExceptionResponse handleSystemExceptionError() {
    	ExceptionResponse res = new ExceptionResponse();
        res.setStatus(Status.ERROR.getStatus());
		res.setMessage(Message.SYSTEM_ERROR.getMessage());
        return res;
    }

    /**
     * ユーザー認証エラーを処理します。
     * 
     * @return 失敗結果
     */
    @ExceptionHandler( AuthenticationException.class )
    @ResponseBody
    public ExceptionResponse handleAuthenticationError() {
    	ExceptionResponse res = new ExceptionResponse();
    	res.setStatus(Status.ERROR.getStatus());
    	res.setMessage(Message.AUTHENTICATION_ERROR.getMessage());
    	return res;
    }
    
}
