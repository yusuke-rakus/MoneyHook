package com.example.controller.saving;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.Status;
import com.example.common.message.SuccessMessage;
import com.example.domain.Saving;
import com.example.form.GetSavingForm;
import com.example.response.GetSavingResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class GetSavingDataTest {
	final String URL = "/saving/getSavingData";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	@Transactional(readOnly = true)
	void getSavingDataTest() throws Exception {
		Integer savingId = 1;

		GetSavingForm requestForm = new GetSavingForm();
		requestForm.setUserId(USER_ID);
		requestForm.setSavingId(savingId);

		String result = mvc
				.perform(post(URL).content(mapper.writeValueAsString(requestForm))
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString(Charset.defaultCharset());

		GetSavingResponse response = mapper.readValue(result, GetSavingResponse.class);
		/* 検証 */
		Saving resSaving = response.getSaving();

		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(SuccessMessage.SAVING_DATA_GET_SUCCESSED, response.getMessage());
		assertThat(resSaving.getSavingId()).isNotNull();
		assertThat(resSaving.getSavingName()).isNotNull();
		assertThat(resSaving.getSavingAmount()).isNotNull();
		assertThat(resSaving.getSavingDate()).isNotNull();
	}
}