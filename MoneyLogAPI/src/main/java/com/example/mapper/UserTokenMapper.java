package com.example.mapper;

import com.example.form.GoogleSignInForm;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserTokenMapper {
	/** トークンを更新 */
	public boolean updateToken(GoogleSignInForm form);

	/** トークンを登録 */
	public boolean insertToken(GoogleSignInForm form);

}
