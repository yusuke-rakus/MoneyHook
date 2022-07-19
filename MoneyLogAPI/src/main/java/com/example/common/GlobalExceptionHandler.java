package com.example.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.common.exception.AuthenticationException;
import com.example.response.ExceptionResponse;

@ControllerAdvice
public class GlobalExceptionHandler  {

    @ExceptionHandler( Exception.class )
	@ResponseBody
    public ExceptionResponse handleSystemExceptionError() {
    	ExceptionResponse res = new ExceptionResponse();
        res.setStatus(Status.ERROR.getStatus());
		res.setMessage(Message.SYSTEM_ERROR.getMessage());
        return res;
    }

    @ExceptionHandler( AuthenticationException.class )
    @ResponseBody
    public ExceptionResponse handleAuthenticationError() {
    	ExceptionResponse res = new ExceptionResponse();
    	res.setStatus(Status.ERROR.getStatus());
    	res.setMessage(Message.AUTHENTICATION_ERROR.getMessage());
    	return res;
    }
    
}
