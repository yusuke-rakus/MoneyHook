package com.example.controller.user;

import com.example.common.Status;
import com.example.common.message.ErrorMessage;
import com.example.common.message.ValidatingMessage;
import com.example.form.LoginForm;
import com.example.form.RegistUserForm;
import com.example.response.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerLoginTest {

	final String URL = "/user/login";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private Validator validator;

	private BindingResult bindingResult;

	@BeforeEach
	void setUp() {
		bindingResult = new BindException(new LoginForm(), "testForm");
	}

	@ParameterizedTest
	@ValueSource(strings = {"aaa", "あああ", "aa＠"})
	void emailValidationTest(String s) throws Exception {
		LoginForm testForm = new LoginForm();
		testForm.setEmail(s);
		testForm.setPassword("aaa");

		validator.validate(testForm, bindingResult);
		assertEquals(ValidatingMessage.EMAIL_EMPTY_ERROR, bindingResult.getFieldError().getDefaultMessage());

	}

	@ParameterizedTest
	@ValueSource(strings = {" ", "", "\t"})
	void notBlankEmailValidationTest(String s) throws Exception {
		LoginForm testForm = new LoginForm();
		testForm.setEmail(s);
		testForm.setPassword("aaa");

		validator.validate(testForm, bindingResult);
		assertEquals(ValidatingMessage.EMAIL_EMPTY_ERROR, bindingResult.getFieldError().getDefaultMessage());

	}

	@ParameterizedTest
	@ValueSource(strings = {" ", "", "\t"})
	void notBlankPasswordValidationTest(String s) throws Exception {
		LoginForm testForm = new LoginForm();
		testForm.setEmail("sample@sample.com");
		testForm.setPassword(s);

		validator.validate(testForm, bindingResult);
		assertEquals(ValidatingMessage.PASSWORD_EMPTY_ERROR, bindingResult.getFieldError().getDefaultMessage());

	}

	@Test
	@Transactional(readOnly = true)
	void loginTest() throws Exception {
		/*準備*/
		String createEmail = "sample@sample.com";
		String createPassword = "password";
		RegistUserForm form = new RegistUserForm();
		form.setEmail(createEmail);
		form.setPassword(createPassword);

		String result = mvc.perform(post(URL)
						.content(mapper.writeValueAsString(form))
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset());

		LoginResponse response = mapper.readValue(result, LoginResponse.class);
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(USER_ID, response.getUser().getUserId());
		assertNotNull(response.getUser().getThemeColorCode());
		assertNull(response.getUser().getThemeColorGradientCode());
	}

	@Test
	@Transactional(readOnly = true)
	void notEmailLoginTest() throws Exception {
		/*準備*/
		String setEmail = "sample@example.com";
		String setPassword = "password";
		RegistUserForm form = new RegistUserForm();
		form.setEmail(setEmail);
		form.setPassword(setPassword);

		String result = mvc.perform(post(URL)
						.content(mapper.writeValueAsString(form))
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset());

		LoginResponse response = mapper.readValue(result, LoginResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.EMAIL_OR_PASSWORD_IS_WRONG, response.getMessage());
		assertNull(response.getUser());
	}

	@Test
	@Transactional(readOnly = true)
	void notPasswordLoginTest() throws Exception {
		/*準備*/
		String setEmail = "sample@example.com";
		String setPassword = "Password";
		RegistUserForm form = new RegistUserForm();
		form.setEmail(setEmail);
		form.setPassword(setPassword);

		String result = mvc.perform(post(URL)
						.content(mapper.writeValueAsString(form))
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset());

		LoginResponse response = mapper.readValue(result, LoginResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.EMAIL_OR_PASSWORD_IS_WRONG, response.getMessage());
		assertNull(response.getUser());
	}
}
