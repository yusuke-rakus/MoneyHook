package com.example.controller.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import com.example.common.Status;
import com.example.common.message.ErrorMessage;
import com.example.common.message.SuccessMessage;
import com.example.common.message.ValidatingMessage;
import com.example.domain.User;
import com.example.form.ChangeEmailForm;
import com.example.form.GetUserInfoForm;
import com.example.mapper.UserMapper;
import com.example.response.ChangeEmailResponse;
import com.example.response.ChangePasswordResponse;
import com.example.service.SendMailService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerChangeEmailTest {
	
	final String URL = "/user/changeEmail";
	final String PASSWORD = "password";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";
	final String EMAIL = "sample@sample.com";
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@MockBean
	private SendMailService mockSendMailService;
	
	@Autowired
	private Validator validator;
	
	private BindingResult bindingResult;
	
	@BeforeEach
	void setUp() {
		bindingResult = new BindException(new ChangeEmailForm(), "testForm");
	}
	
	
	@ParameterizedTest
	@ValueSource(strings = {" ", "", "\t"})
	void notBlankEmailValidationTest(String s) throws Exception {
		ChangeEmailForm testForm = new ChangeEmailForm(); 
		testForm.setUserId(USER_ID);
		testForm.setEmail(s);
		testForm.setPassword(PASSWORD);
		
		validator.validate(testForm, bindingResult);
		assertEquals(ValidatingMessage.EMAIL_EMPTY_ERROR, bindingResult.getFieldError().getDefaultMessage());
		
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"aaa", "aaa＠aaa", "あああ"})
	void typeEmailValidationTest(String s) throws Exception {
		ChangeEmailForm testForm = new ChangeEmailForm(); 
		testForm.setUserId(USER_ID);
		testForm.setEmail(s);
		testForm.setPassword(PASSWORD);
		
		validator.validate(testForm, bindingResult);
		assertEquals(ValidatingMessage.EMAIL_EMPTY_ERROR, bindingResult.getFieldError().getDefaultMessage());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq@com"})
	void lengthEmailValidationTest(String s) throws Exception {
		ChangeEmailForm testForm = new ChangeEmailForm(); 
		testForm.setUserId(USER_ID);
		testForm.setEmail(s);
		testForm.setPassword(PASSWORD);
		
		validator.validate(testForm, bindingResult);
		assertEquals(ValidatingMessage.EMAIL_OVER_LIMIT_ERROR, bindingResult.getFieldError().getDefaultMessage());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"        "})
	void notBlankPasswordValidationTest(String s) throws Exception {
		ChangeEmailForm testForm = new ChangeEmailForm(); 
		testForm.setUserId(USER_ID);
		testForm.setEmail(EMAIL);
		testForm.setPassword(s);
		
		validator.validate(testForm, bindingResult);
		assertEquals(ValidatingMessage.PASSWORD_EMPTY_ERROR, bindingResult.getFieldError().getDefaultMessage());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"1234567", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
	void lengthPasswordValidationTest(String s) throws Exception {
		ChangeEmailForm testForm = new ChangeEmailForm(); 
		testForm.setUserId(USER_ID);
		testForm.setEmail(EMAIL);
		testForm.setPassword(s);
		
		validator.validate(testForm, bindingResult);
		assertEquals(ValidatingMessage.PASSWORD_RANGE_ERROR, bindingResult.getFieldError().getDefaultMessage());
	}
	
	@Test
	@Transactional(readOnly = false)
	void changeEmailTest() throws Exception {
		/*準備*/
		String newEmail = "junittest@example.com";
		
		ChangeEmailForm form = new ChangeEmailForm(); 
		form.setUserId(USER_ID);
		form.setEmail(newEmail);
		form.setPassword(PASSWORD);
		
		//モック化
		doNothing().when(mockSendMailService).sendMail(any(), anyString(), anyString(), anyString());
		
		//実行
		String result = mvc.perform(post(URL)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset());
		
		//検証
		GetUserInfoForm infoForm = new GetUserInfoForm();
		infoForm.setUserId(USER_ID);
		User user = userMapper.getUserInfo(infoForm);

		verify(mockSendMailService, times(1)).sendMail(any(), anyString(), anyString(), anyString());
		
		ChangeEmailResponse response = mapper.readValue(result, ChangeEmailResponse.class);
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(SuccessMessage.USER_EMAIL_CHANGED, response.getMessage());
		assertEquals(newEmail, user.getEmail());
	}
	
	@Test
	@Transactional(readOnly = false)
	void changeEmailErrorTest() throws Exception {
		/*準備*/
		String newEmail = "junittest@example.com";
		String errorPassword = "aaaaaaaa";
		
		ChangeEmailForm form = new ChangeEmailForm(); 
		form.setUserId(USER_ID);
		form.setEmail(newEmail);
		form.setPassword(errorPassword);
				
		//実行
		String result = mvc.perform(post(URL)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset());
		
		//検証
		GetUserInfoForm infoForm = new GetUserInfoForm();
		infoForm.setUserId(USER_ID);
		User user = userMapper.getUserInfo(infoForm);

		//メール送信処理が行われていないか
		verify(mockSendMailService, times(0)).sendMail(any(), anyString(), anyString(), anyString());
		
		ChangePasswordResponse response = mapper.readValue(result, ChangePasswordResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.SYSTEM_ERROR, response.getMessage());
		//メールアドレスが変更されていないか
		assertEquals(EMAIL, user.getEmail());
	}
	
	/* TODO 
	 * メール送信が失敗した場合はロールバックするか
	 * */
	@Test
	@Transactional(readOnly = false)
	void sendEmailErrorTest() throws Exception {
		/*準備*/
		String newEmail = "junittest@example.com";
		
		ChangeEmailForm form = new ChangeEmailForm(); 
		form.setUserId(USER_ID);
		form.setEmail(newEmail);
		form.setPassword(PASSWORD);
		
		//モック化
		doThrow(new RuntimeException()).when(mockSendMailService).sendMail(any(), anyString(), anyString(), anyString());
				
		//実行
		String result = mvc.perform(post(URL)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset());
		
		//検証
		GetUserInfoForm infoForm = new GetUserInfoForm();
		infoForm.setUserId(USER_ID);
		User user = userMapper.getUserInfo(infoForm);

		//メール送信処理が行われていないか
		verify(mockSendMailService, times(1)).sendMail(any(), anyString(), anyString(), anyString());
		
		ChangePasswordResponse response = mapper.readValue(result, ChangePasswordResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.SYSTEM_ERROR, response.getMessage());
		//メールアドレスが変更されている
		assertEquals(newEmail, user.getEmail());
	}
	
}
