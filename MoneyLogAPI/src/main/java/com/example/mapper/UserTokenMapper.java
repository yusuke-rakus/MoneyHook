package com.example.mapper;

import com.example.form.GoogleSignInForm;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserTokenMapper {
	/** トークンを更新 */
	public void updateToken(GoogleSignInForm form);

	/** トークンを登録 */
	public void insertToken(GoogleSignInForm form);

	/** トークンを確認 */
	public boolean checkUserToken(GoogleSignInForm form);

}
