package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.form.RegistUserForm;

@Mapper
public interface UserMapper {

	/** ユーザー登録 */
	public void registUser(RegistUserForm form);

}
