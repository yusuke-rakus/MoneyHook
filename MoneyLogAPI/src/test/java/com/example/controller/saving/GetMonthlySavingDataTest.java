package com.example.controller.saving;

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
import com.example.common.message.SuccessMessage;
import com.example.form.GetTotalSavingForm;
import com.example.response.GetTotalSavingResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class GetMonthlySavingDataTest {
	final String URL = "/saving/getTotalSaving";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";

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

		String result = mvc
				.perform(post(URL).content(mapper.writeValueAsString(requestForm))
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString(Charset.defaultCharset());

		GetTotalSavingResponse response = mapper.readValue(result, GetTotalSavingResponse.class);
		/* 検証 */
		int savingDataCount = 6;
		int totalSavingAmount = 602322;

		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(SuccessMessage.SAVING_TOTAL_DATA_GET_SUCCESSED, response.getMessage());
		assertEquals(response.getSavingDataList().size(), savingDataCount);
		assertEquals(response.getTotalSavingAmount(), totalSavingAmount);
	}
}
