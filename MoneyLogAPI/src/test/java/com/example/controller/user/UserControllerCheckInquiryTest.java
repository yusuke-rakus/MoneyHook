package com.example.controller.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.Status;
import com.example.common.message.ErrorMessage;
import com.example.form.SendInquiryForm;
import com.example.response.SendInquiryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerCheckInquiryTest {

	
	final String URL = "/user/checkInquiry";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	@Sql("classpath:sql/inquiry/inquiry.sql")
	@Transactional(readOnly = false)
	void chackInquiryTodayTest() throws Exception {
		/*準備*/
		SendInquiryForm form = new SendInquiryForm(); 
		form.setUserId(USER_ID);
		
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
	}
	
	@Test
	@Sql("classpath:sql/inquiry/inquiry.sql")
	@Transactional(readOnly = false)
	void inquiryTodayTest() throws Exception {
		/*準備*/
		SendInquiryForm form = new SendInquiryForm(); 
		form.setUserId(USER_ID);
		
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
