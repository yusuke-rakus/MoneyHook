package com.example.service;

import com.example.common.exception.AuthenticationException;
import com.example.common.message.Message;
import com.example.form.form;
import com.example.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthenticationService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private Message message;

	public Long authUser(form form) throws AuthenticationException {

		Long userNo = userMapper.getUserNoFromUserId(form.getUserId());

		if (userNo == null) {
			throw new AuthenticationException(message.get("error-message.authentication-error"));
		}

		return userNo;
	}

}
