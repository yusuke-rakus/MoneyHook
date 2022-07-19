package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.Message;
import com.example.common.exception.AuthenticationException;
import com.example.form.form;
import com.example.mapper.UserMapper;

@Service
@Transactional
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
