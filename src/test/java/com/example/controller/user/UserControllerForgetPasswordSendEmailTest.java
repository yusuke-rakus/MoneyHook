package com.example.controller.user;

import com.example.common.SHA256;
import com.example.common.Status;
import com.example.common.message.ErrorMessage;
import com.example.common.message.SuccessMessage;
import com.example.domain.User;
import com.example.form.ForgotPasswordSendEmailForm;
import com.example.mapper.UserMapper;
import com.example.response.ForgotPasswordSendEmailResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
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

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class UserControllerForgetPasswordSendEmailTest {

	final String URL = "/user/forgotPasswordSendEmail";
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

	@AfterEach
	public void doAfter() {
		// 初期化
		Mockito.reset(userMapper);
	}


	@Order(1)
	@Test
	@Transactional(readOnly = false)
	void forgetPasswordSendEmailSuccessTest() throws Exception {
		/*準備*/
		ForgotPasswordSendEmailForm form = new ForgotPasswordSendEmailForm();
		form.setEmail(EMAIL);

		//モック化
		doNothing().when(mockSendMailService).sendMail(any(), anyString(), anyString(), anyString());

		//実行
		String result = mvc.perform(post(URL).content(mapper.writeValueAsString(form))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		//DBに登録されているかを確認
		String sql = "SELECT * FROM user WHERE user_id = ?";
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		User user = jdbcTemplate.queryForObject(sql, rowMapper, USER_ID);

		String hashPass = SHA256.getHashedValue(EMAIL);

		//検証
		verify(mockSendMailService, times(1)).sendMail(any(), anyString(), anyString(), anyString());

		ForgotPasswordSendEmailResponse response = mapper.readValue(result, ForgotPasswordSendEmailResponse.class);
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(SuccessMessage.FORGOT_PASSWORD_EMAIL_SUCCESS, response.getMessage());
		assertNotNull(user.getResetPasswordParam());
		assertEquals(user.getResetPasswordParam(), hashPass);
	}

	@Order(2)
	@Test
	@Transactional(readOnly = false)
	void forgetPasswordSendEmailErrorTest() throws Exception {
		/*準備*/
		String notEmail = "junittest01@example.com";

		ForgotPasswordSendEmailForm form = new ForgotPasswordSendEmailForm();
		form.setEmail(notEmail);

		//モック化
		doNothing().when(mockSendMailService).sendMail(any(), anyString(), anyString(), anyString());

		//実行
		String result = mvc.perform(post(URL).content(mapper.writeValueAsString(form))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		//検証
		/* メール送信が呼ばれていないか */
		verify(mockSendMailService, times(0)).sendMail(any(), anyString(), anyString(), anyString());

		ForgotPasswordSendEmailResponse response = mapper.readValue(result, ForgotPasswordSendEmailResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.EMAIL_NOT_EXIST_ERROR, response.getMessage());
	}

	/*
	 * DBエラー発生の際のテスト
	 * */
	@Order(3)
	@Test
	@Transactional(readOnly = false)
	void xdbErrorTest() throws Exception {
		/*準備*/
		ForgotPasswordSendEmailForm form = new ForgotPasswordSendEmailForm();
		form.setEmail(EMAIL);

		//モック化
		doThrow(new RuntimeException()).when(userMapper).setResetPasswordParam(any());
		doNothing().when(mockSendMailService).sendMail(any(), anyString(), anyString(), anyString());

		//実行
		String result = mvc.perform(post(URL).content(mapper.writeValueAsString(form))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		//DBに登録されていないかを確認
		String sql = "SELECT * FROM user WHERE user_id = ?";
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		User user = jdbcTemplate.queryForObject(sql, rowMapper, USER_ID);


		//検証
		/* メール送信が呼ばれていないか */
		verify(mockSendMailService, times(0)).sendMail(any(), anyString(), anyString(), anyString());

		ForgotPasswordSendEmailResponse response = mapper.readValue(result, ForgotPasswordSendEmailResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.SYSTEM_ERROR, response.getMessage());
		assertNull(user.getResetPasswordParam());
	}
}
