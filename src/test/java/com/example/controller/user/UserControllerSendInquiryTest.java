package com.example.controller.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import com.example.common.Status;
import com.example.common.message.ErrorMessage;
import com.example.common.message.SuccessMessage;
import com.example.form.SendInquiryForm;
import com.example.response.SendInquiryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerSendInquiryTest {

	
	final String URL = "/user/sendInquiry";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
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
		
		assertEquals(ErrorMessage.INQUIRY_BLANK_ERROR, bindingResult.getFieldError().getDefaultMessage());
		
	}
	
	@Test
	void validateInquiryByNullTest() throws Exception {
		SendInquiryForm form = new SendInquiryForm();
		form.setUserId(USER_ID);
		form.setInquiry(null);
		
		validator.validate(form, bindingResult);
		
		assertEquals(ErrorMessage.INQUIRY_BLANK_ERROR, bindingResult.getFieldError().getDefaultMessage());
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
		SendInquiryResponse response = mapper.readValue(result, SendInquiryResponse.class);
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(SuccessMessage.SEND_INQUIRY_SUCCESS, response.getMessage());
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
		SendInquiryResponse response = mapper.readValue(result, SendInquiryResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.INQUIRY_OVER_TIMES, response.getMessage());
	}
	
}
