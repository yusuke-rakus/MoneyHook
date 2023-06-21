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
import com.example.form.GetTotalSpendingForm;
import com.example.response.GetTotalSpendingResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class GetTotalSpendingTest {

	final String URL = "/transaction/getTotalSpending";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	@Transactional(readOnly = true)
	void getTotalSpendingTest() throws Exception {

		Long categoryId = Long.valueOf("5");
		Long subCategoryId = Long.valueOf("9");
		Date startMonth = Date.valueOf("2022-12-01");
		Date endMonth = Date.valueOf("2023-06-01");

		GetTotalSpendingForm req = new GetTotalSpendingForm();
		req.setCategoryId(categoryId);
		req.setSubCategoryId(subCategoryId);
		req.setStartMonth(startMonth);
		req.setEndMonth(endMonth);
		req.setUserId(USER_ID);

		String result = mvc
				.perform(post(URL).content(mapper.writeValueAsString(req)).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString(Charset.defaultCharset());

		GetTotalSpendingResponse response = mapper.readValue(result, GetTotalSpendingResponse.class);

		/* 検証 */
		Integer totalSpending = -130697;

		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(null, response.getMessage());
		assertEquals(response.getTotalSpending(), totalSpending);
	}
}
