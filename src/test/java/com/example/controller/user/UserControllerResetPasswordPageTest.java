package com.example.controller.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.SHA256;
import com.example.common.Status;
import com.example.form.ResetPasswordPageForm;
import com.example.mapper.UserMapper;
import com.example.response.ResetPasswordPageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class UserControllerResetPasswordPageTest {
	
	final String URL = "/user/resetPasswordPage";
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

	@AfterEach
	public  void doAfter() {
		// 初期化
		Mockito.reset(userMapper);
	}
	
	@Order(1)
	@Test
	@Transactional(readOnly = false)
	void getPasswordResetPageTest() throws Exception {
		/*準備*/
		String updateSql = "UPDATE user Set reset_password_param = ? WHERE user_id = ?";
		String hashEmail = SHA256.getHashedValue(EMAIL);
		jdbcTemplate.update(updateSql, hashEmail, USER_ID);
		
		ResetPasswordPageForm form = new ResetPasswordPageForm(); 
		form.setParam(hashEmail);
		
		//パスワードを忘れた場合の再設定の実行
		String result = mvc.perform(post(URL)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset());
		

		//検証
		ResetPasswordPageResponse response = mapper.readValue(result, ResetPasswordPageResponse.class);
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(EMAIL, response.getEmail());
	}
	
	@Order(2)
	@Test
	@Transactional(readOnly = false)
	void getPasswordResetPageErrorTest() throws Exception {
		/*準備*/
		String updateSql = "UPDATE user Set reset_password_param = ? WHERE user_id = ?";
		String hashEmail = SHA256.getHashedValue(EMAIL);
		jdbcTemplate.update(updateSql, hashEmail, USER_ID);
		
		ResetPasswordPageForm form = new ResetPasswordPageForm(); 
		form.setParam(hashEmail);
		
		doThrow(new RuntimeException()).when(userMapper).resetPasswordPage(any());
		
		//パスワードを忘れた場合の再設定の実行
		String result = mvc.perform(post(URL)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset());
		

		//検証
		ResetPasswordPageResponse response = mapper.readValue(result, ResetPasswordPageResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
	}
}
