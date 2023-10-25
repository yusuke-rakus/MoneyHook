package com.example.controller.mt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.Status;
import com.example.common.message.ErrorMessage;
import com.example.form.GetFixedForm;
import com.example.response.GetFixedResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class MTControllerGetFixedTest {
	
	final String URL = "/fixed/getFixed";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	@Transactional(readOnly = true)
	void getFixedSuccessTest() throws Exception {
		
		GetFixedForm form = new GetFixedForm();
		form.setUserId(USER_ID);
		
		String result = mvc.perform(post(URL)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk()) 
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset());
		
		GetFixedResponse response = mapper.readValue(result, GetFixedResponse.class);
		int mtCount = 5;
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(mtCount, response.getMonthlyTransactionList().size());
	}
	
	@Test
	@Transactional(readOnly = false)
	void ggetFixedList0SuccessTest() throws Exception {
		
		GetFixedForm form = new GetFixedForm();
		form.setUserId(USER_ID);
		
		String sql = "UPDATE monthly_transaction SET include_flg = 0";
		jdbcTemplate.update(sql);
		
		String result = mvc.perform(post(URL)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk()) 
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset());
		
		GetFixedResponse response = mapper.readValue(result, GetFixedResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.MONTHLY_TRANSACTION_NOT_EXISTS, response.getMessage());
	}
}