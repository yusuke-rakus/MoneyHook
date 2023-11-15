package com.example.controller.user;

import com.example.common.Status;
import com.example.common.message.Message;
import com.example.form.SendInquiryForm;
import com.example.response.SendInquiryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import java.nio.charset.Charset;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerSendInquiryTest {


	final String URL = "/user/sendInquiry";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";
	final String TOKEN = "sample_token";
	final HttpHeaders HEADER = new HttpHeaders();

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private Validator validator;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private Message message;

	private BindingResult bindingResult;

	@BeforeEach
	void setUp() {
		bindingResult = new BindException(new SendInquiryForm(), "testForm");
	}

	@ParameterizedTest
	@ValueSource(strings = {"", " ", "\t"})
	void validateInquiryTest(String s) throws Exception {
		SendInquiryForm form = new SendInquiryForm();
		form.setUserId(USER_ID);
		form.setInquiry(s);

		validator.validate(form, bindingResult);

		assertEquals(message.get("error-message.inquiry-blank-error"), bindingResult.getFieldError()
				.getDefaultMessage());

	}

	@Test
	void validateInquiryByNullTest() throws Exception {
		SendInquiryForm form = new SendInquiryForm();
		form.setUserId(USER_ID);
		form.setInquiry(null);

		validator.validate(form, bindingResult);

		assertEquals(message.get("error-message.inquiry-blank-error"), bindingResult.getFieldError()
				.getDefaultMessage());
	}

	@Test
	@Sql("classpath:sql/inquiry/inquiry.sql")
	@Transactional(readOnly = false)
	void sendInquiryTest() throws Exception {
		/*準備*/
		String inquiry = "問い合わせサンプル";
		SendInquiryForm form = new SendInquiryForm();
		form.setUserId(USER_ID);
		form.setInquiry(inquiry);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		//実行
		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(form))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		//検証
		SendInquiryResponse response = mapper.readValue(result, SendInquiryResponse.class);
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(message.get("success-message.send-inquiry-success"), response.getMessage());
	}

	@Test
	@Sql("classpath:sql/inquiry/inquiry.sql")
	@Transactional(readOnly = false)
	void inquirySendErrorTest() throws Exception {
		/*準備*/
		String setInquiry = "問い合わせサンプル";
		SendInquiryForm form = new SendInquiryForm();
		form.setUserId(USER_ID);
		form.setInquiry(setInquiry);

		int userNo = 2;
		String inquiry = "テスト";
		LocalDate data = LocalDate.now();
		String sql = "INSERT INTO inquiry_data values (?, ?, ?)";
		jdbcTemplate.update(sql, userNo, inquiry, data);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		//実行
		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(form))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		//検証
		SendInquiryResponse response = mapper.readValue(result, SendInquiryResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.inquiry-over-times"), response.getMessage());
	}

}
