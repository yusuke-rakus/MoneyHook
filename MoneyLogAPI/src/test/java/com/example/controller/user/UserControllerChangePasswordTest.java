package com.example.controller.user;

import static org.junit.jupiter.api.Assertions.*;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import com.example.common.SHA256;
import com.example.common.Status;
import com.example.common.message.ErrorMessage;
import com.example.common.message.SuccessMessage;
import com.example.common.message.ValidatingMessage;
import com.example.domain.User;
import com.example.form.ChangePasswordForm;
import com.example.form.LoginForm;
import com.example.mapper.UserMapper;
import com.example.response.ChangePasswordResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerChangePasswordTest {
	
	final String URL = "/user/changePassword";
	final String PASSWORD = "password";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private Validator validator;
	
	private BindingResult bindingResult;
	
	@BeforeEach
	void setUp() {
		bindingResult = new BindException(new ChangePasswordForm(), "testForm");
	}
	
	
	@ParameterizedTest
	@ValueSource(strings = {"        "})
	void notBlankPasswordValidationTest(String s) throws Exception {
		ChangePasswordForm testForm = new ChangePasswordForm(); 
		testForm.setNewPassword(s);
		
		validator.validate(testForm, bindingResult);
		assertEquals(ValidatingMessage.PASSWORD_EMPTY_ERROR, bindingResult.getFieldError().getDefaultMessage());
		
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"aaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"})
	void lengthPasswordValidationTest(String s) throws Exception {
		ChangePasswordForm testForm = new ChangePasswordForm(); 
		testForm.setNewPassword(s);
		
		validator.validate(testForm, bindingResult);
		assertEquals(ValidatingMessage.PASSWORD_RANGE_ERROR, bindingResult.getFieldError().getDefaultMessage());
	}
	
	@Test
	@Transactional(readOnly = false)
	void changePasswordTest() throws Exception {
		/*準備*/
		String newPassword = "junitPass";
		
		ChangePasswordForm form = new ChangePasswordForm();
		form.setUserId(USER_ID);
		form.setPassword(PASSWORD);
		form.setNewPassword(newPassword);
		
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
		String email = "sample@sample.com";
		LoginForm loginForm = new LoginForm();
		loginForm.setEmail(email);
		User user = userMapper.login(loginForm);
		
		String hashedPassword = SHA256.getHashedPassword(newPassword);

		
		ChangePasswordResponse response = mapper.readValue(result, ChangePasswordResponse.class);
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(SuccessMessage.USER_PASSWORD_CHANGED, response.getMessage());
		assertEquals(hashedPassword, user.getPassword());
	}
	
	@Test
	@Transactional(readOnly = false)
	void errorPasswordChangeLoginTest() throws Exception {
		/*準備*/
		String notPassword = "abcdabcd";
		String newPassword = "junitPass";
		
		ChangePasswordForm form = new ChangePasswordForm();
		form.setUserId(USER_ID);
		form.setPassword(notPassword);
		form.setNewPassword(newPassword);
		
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
		String email = "sample@sample.com";
		LoginForm loginForm = new LoginForm();
		loginForm.setEmail(email);
		User user = userMapper.login(loginForm);
		
		/*変更前のパスワードをハッシュ化*/
		String hashedPassword = SHA256.getHashedPassword(PASSWORD);
		
		ChangePasswordResponse response = mapper.readValue(result, ChangePasswordResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.USER_PASSWORD_CHANGE_FAILED, response.getMessage());
		/*　パスワードが変更されていないかを確認　*/
		assertEquals(hashedPassword, user.getPassword());
	}
	
}
