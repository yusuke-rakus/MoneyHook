package com.example.controller.savingTarget;

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
import com.example.form.GetSavingTargetListForm;
import com.example.response.GetSavingTargetListResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class GetSavingTargetListTest {
	final String URL = "/savingTarget/getSavingTargetList";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	@Transactional(readOnly = true)
	void getSavingTargetListTest() throws Exception {

		GetSavingTargetListForm requestForm = new GetSavingTargetListForm();
		requestForm.setUserId(USER_ID);

		String result = mvc
				.perform(post(URL).content(mapper.writeValueAsString(requestForm))
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString(Charset.defaultCharset());

		GetSavingTargetListResponse response = mapper.readValue(result, GetSavingTargetListResponse.class);
		/* 検証 */
		int savingTargetListCount = 2;

		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(SuccessMessage.SAVING_TARGET_LIST_GET_SUCCESSED, response.getMessage());
		assertEquals(response.getSavingTarget().size(), savingTargetListCount);
	}
}