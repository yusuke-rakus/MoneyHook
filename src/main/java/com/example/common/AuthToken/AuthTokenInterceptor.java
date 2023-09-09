package com.example.common.AuthToken;

import com.example.form.GoogleSignInForm;
import com.example.mapper.UserTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthTokenInterceptor implements HandlerInterceptor {

	@Autowired
	UserTokenMapper userTokenMapper;

	/**
	 * コントローラ実行前のトークン認証処理
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		String token = request.getHeader("Authorization");
		String userId = request.getHeader("userId");

		GoogleSignInForm form = new GoogleSignInForm();
		form.setToken(token);
		form.setUserId(userId);
		return userTokenMapper.checkUserToken(form);
	}
}
