package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.domain.User;
import com.example.form.LoginForm;
import com.example.form.RegistUserForm;

@Mapper
public interface UserMapper {

	/** ユーザー登録 */
	public void registUser(RegistUserForm form);

	/** ログイン */
	public User login(LoginForm form);

}
