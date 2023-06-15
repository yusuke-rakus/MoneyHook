package com.example.controller.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.Status;
import com.example.form.GetMonthlySpendingDataForm;
import com.example.response.GetMonthlySpendingDataResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class GetTotalSpendingTest {

	final String URL = "/transaction/getMonthlySpendingData";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	@Transactional(readOnly = true)
	void getHomeTest() throws Exception {

		Date month = Date.valueOf("2023-06-01");

		GetMonthlySpendingDataForm requestForm = new GetMonthlySpendingDataForm();
		requestForm.setMonth(month);
		requestForm.setUserId(USER_ID);

		String result = mvc
				.perform(post(URL).content(mapper.writeValueAsString(requestForm))
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString(Charset.defaultCharset());

		GetMonthlySpendingDataResponse response = mapper.readValue(result, GetMonthlySpendingDataResponse.class);

		/* 検証 */
		int dataCount = 6;

		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(null, response.getMessage());
		assertEquals(response.getMonthlyTotalAmountList().size(), dataCount);
	}
}
