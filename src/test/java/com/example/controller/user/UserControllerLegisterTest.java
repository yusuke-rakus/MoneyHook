package com.example.controller.user;

import com.example.common.SHA256;
import com.example.common.Status;
import com.example.common.message.ErrorMessage;
import com.example.common.message.SuccessMessage;
import com.example.domain.User;
import com.example.form.LoginForm;
import com.example.form.RegistUserForm;
import com.example.mapper.UserMapper;
import com.example.response.RegistUserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerLegisterTest {

	final String URL = "/user/registUser";

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private UserMapper userMapper;

	/* SendMailServiceクラス全体をモック化 */
	@MockBean
	private SendMailService mockSendMailService;

	@Test
	@Transactional(readOnly = false)
	void createUserTest() throws Exception {
		/*準備*/
		String createEmail = "junit-test@example.com";
		String createPassword = "junittest0001";
		RegistUserForm form = new RegistUserForm();
		form.setEmail(createEmail);
		form.setPassword(createPassword);

		/* テスト実施時にメール送信回避のためモック化*/
		doNothing().when(mockSendMailService).sendMail(any(), anyString(), anyString(), anyString());

		String result = mvc.perform(post(URL).content(mapper.writeValueAsString(form))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		RegistUserResponse response = mapper.readValue(result, RegistUserResponse.class);

		/* 検証
		 * モック化されたSendMailServiceが正しく呼ばれているかを判定 */
		verify(mockSendMailService, times(1)).sendMail(any(), anyString(), anyString(), anyString());

		/*DB登録チェックのため登録したuser検索を実施*/
		LoginForm loginForm = new LoginForm();
		loginForm.setEmail(createEmail);
		User user = userMapper.login(loginForm);

		String hashedPassword = SHA256.getHashedPassword(createPassword);

		/* レスポンスに正しく値がセットされているかの判定*/
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(SuccessMessage.CREATE_USER_REGISTERD_SUCCESS, response.getMessage());
		assertEquals(false, response.getUser().getUserId().isEmpty());

		/* DBに正しく登録されているかを判定 */
		assertEquals(user.getUserId(), response.getUser().getUserId());
		assertEquals(user.getPassword(), hashedPassword);
	}

	@Test
	@Transactional(readOnly = false)
	void createUserByIdenticalMailTest() throws Exception {
		/*準備*/
		String identicalEmail = "sample@sample.com";
		String createPassword = "junittest0001";
		RegistUserForm form = new RegistUserForm();
		form.setEmail(identicalEmail);
		form.setPassword(createPassword);

		/* テスト実施時にメール送信回避のためモック化*/
		doNothing().when(mockSendMailService).sendMail(any(), anyString(), anyString(), anyString());

		String result = mvc.perform(post(URL).content(mapper.writeValueAsString(form))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		RegistUserResponse response = mapper.readValue(result, RegistUserResponse.class);

		/* 検証
		 * モック化されたSendMailServiceが正しく呼ばれているかを判定 */
		verify(mockSendMailService, times(0)).sendMail(any(), anyString(), anyString(), anyString());

		/* レスポンスに正しく値がセットされているかの判定*/
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.MAIL_ADDRESS_ALREADY_REGISTERED, response.getMessage());
	}

}
