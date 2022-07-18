package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.common.AuthenticationException;
import com.example.common.Message;
import com.example.form.form;
import com.example.mapper.UserMapper;

public class AuthenticationService {

	@Autowired
	private UserMapper userMapper;

	public Long authUser(form form) throws AuthenticationException {

		Long userNo = userMapper.getUserNoFromUserId(form.getUserId());

		if (userNo == null) {
			throw new AuthenticationException(Message.AUTHENTICATION_ERROR.getMessage());
		}

		return userNo;
	}

}
