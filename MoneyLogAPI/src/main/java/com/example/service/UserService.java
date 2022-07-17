package com.example.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.Status;
import com.example.domain.User;
import com.example.form.RegistUserForm;
import com.example.mapper.UserMapper;
import com.example.response.RegistUserResponse;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserMapper userMapper;

	/** ユーザー登録 */
	public RegistUserResponse registUser(RegistUserForm form) {
		RegistUserResponse res = new RegistUserResponse();

		// UUIDを生成
		UUID uuid = UUID.randomUUID();
		String userId = uuid.toString();
		User user = new User();
		user.setUserId(userId);
		res.setUser(user);
		form.setUserId(userId);

		// ユーザー登録処理
		try {
			userMapper.registUser(form);
		} catch (Exception e) {
			res.setStatus(Status.ERROR.getStatus());
		}
		return res;
	}

}
