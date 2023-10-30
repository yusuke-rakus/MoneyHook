package com.example.controller.saving;

import com.example.common.Status;
import com.example.common.message.ErrorMessage;
import com.example.common.message.SuccessMessage;
import com.example.form.GetTotalSavingForm;
import com.example.response.GetTotalSavingResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GetTotalSavingTest {
	final String URL = "/saving/getTotalSaving";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";
	final String FAIL_USER_ID = "fail_user_id";
	final String NULL_USER_ID = null;
	final String TOKEN = "sample_token";
	final HttpHeaders HEADER = new HttpHeaders();

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	@Transactional(readOnly = true)
	void getTotalSavingTest() throws Exception {
		Date month = Date.valueOf("2023-06-01");

		GetTotalSavingForm requestForm = new GetTotalSavingForm();
		requestForm.setUserId(USER_ID);
		requestForm.setMonth(month);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(requestForm))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		GetTotalSavingResponse response = mapper.readValue(result, GetTotalSavingResponse.class);
		/* 検証 */
		int savingDataCount = 6;
		BigInteger totalSavingAmount = BigInteger.valueOf(602100);

		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(SuccessMessage.SAVING_TOTAL_DATA_GET_SUCCESSED, response.getMessage());
		assertEquals(response.getSavingDataList().size(), savingDataCount);
		assertEquals(response.getTotalSavingAmount(), totalSavingAmount);
	}

	@Test
	@Transactional(readOnly = true)
	void getTotalSavingUserError01Test() throws Exception {
		Date month = Date.valueOf("2023-06-01");

		GetTotalSavingForm requestForm = new GetTotalSavingForm();
		requestForm.setUserId(FAIL_USER_ID);
		requestForm.setMonth(month);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(requestForm))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		GetTotalSavingResponse response = mapper.readValue(result, GetTotalSavingResponse.class);
		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.AUTHENTICATION_ERROR, response.getMessage());
	}

	@Test
	@Transactional(readOnly = true)
	void getTotalSavingUserError02Test() throws Exception {
		Date month = Date.valueOf("2023-06-01");

		GetTotalSavingForm requestForm = new GetTotalSavingForm();
		requestForm.setUserId(NULL_USER_ID);
		requestForm.setMonth(month);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(requestForm))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		GetTotalSavingResponse response = mapper.readValue(result, GetTotalSavingResponse.class);
		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.AUTHENTICATION_ERROR, response.getMessage());
	}
}
