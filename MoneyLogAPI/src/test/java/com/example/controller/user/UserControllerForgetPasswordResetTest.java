package com.example.controller.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.SHA256;
import com.example.common.Status;
import com.example.common.message.ErrorMessage;
import com.example.domain.User;
import com.example.form.ForgotPasswordResetForm;
import com.example.mapper.UserMapper;
import com.example.response.ForgotPasswordResetResponse;
import com.example.service.SendMailService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class UserControllerForgetPasswordResetTest {
	
	final String SEND_URL = "/user/forgotPasswordSendEmail";
	final String RESET_URL = "/user/forgotPasswordReset";
	final String PASSWORD = "password";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";
	final String EMAIL = "sample@sample.com";
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@SpyBean
	private UserMapper userMapper;
	
	@MockBean
	private SendMailService mockSendMailService;
	
	
	@Order(1)
	@Test
	@Transactional(readOnly = false)
	void passwordResetSuccessTest() throws Exception {
		/*準備*/
		String newPass = "junittest";
		ForgotPasswordResetForm form = new ForgotPasswordResetForm(); 
		form.setEmail(EMAIL);
		form.setPassword(newPass);
		
		String updateSql = "UPDATE user Set reset_password_param = ? WHERE user_id = ?";
		String hashEmail = SHA256.getHashedValue(EMAIL);
		jdbcTemplate.update(updateSql, hashEmail, USER_ID);
		
		//パスワードを忘れた場合の再設定の実行
		String result = mvc.perform(post(RESET_URL)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset());
		
		//DBに登録されているかを確認
		String sql = "SELECT * FROM user WHERE user_id = ?";
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		User user = jdbcTemplate.queryForObject(sql, rowMapper, USER_ID);
		
		String hashPass = SHA256.getHashedPassword(newPass);
		
		//検証
		ForgotPasswordResetResponse response = mapper.readValue(result, ForgotPasswordResetResponse.class);
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertNull(user.getResetPasswordParam());
		assertEquals(user.getPassword(), hashPass);
	}
	
	/*
	 * DBエラーによるパスワード再設定がエラーになった際のテスト
	 * */
	@Order(2)
	@Test
	@Transactional(readOnly = false)
	void passwordResetTErrorTest() throws Exception {
		/*準備*/
		String newPass = "junittest";
		ForgotPasswordResetForm form = new ForgotPasswordResetForm(); 
		form.setEmail(EMAIL);
		form.setPassword(newPass);
		
		String updateSql = "UPDATE user Set reset_password_param = ? WHERE user_id = ?";
		String hashEmail = SHA256.getHashedValue(EMAIL);
		jdbcTemplate.update(updateSql, hashEmail, USER_ID);
		
		//モック化
		doThrow(new RuntimeException()).when(userMapper).resetPassword(any());
		
		//パスワードを忘れた場合の再設定の実行
		String result = mvc.perform(post(RESET_URL)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset());
		
		//DBに登録されているかを確認
		String sql = "SELECT * FROM user WHERE user_id = ?";
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		User user = jdbcTemplate.queryForObject(sql, rowMapper, USER_ID);
		
		String hashPass = SHA256.getHashedPassword(PASSWORD);
		
		//検証
		ForgotPasswordResetResponse response = mapper.readValue(result, ForgotPasswordResetResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.FORGOT_RESET_PASSWORD_ERROR, response.getMessage());
		assertNotNull(user.getResetPasswordParam());
		assertEquals(user.getPassword(), hashPass);
	}
}
