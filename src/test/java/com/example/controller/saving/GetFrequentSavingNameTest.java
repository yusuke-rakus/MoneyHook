package com.example.controller.saving;

import com.example.common.Status;
import com.example.common.message.Message;
import com.example.form.FrequentSavingNameForm;
import com.example.response.FrequentSavingNameResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GetFrequentSavingNameTest {
	final String URL = "/saving/getFrequentSavingName";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";
	final String FAIL_USER_ID = "fail_user_id";
	final String NULL_USER_ID = null;
	final String TOKEN = "sample_token";
	final HttpHeaders HEADER = new HttpHeaders();

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private Message message;

	@Test
	@Transactional(readOnly = true)
	void getFrequentSavingNameTest() throws Exception {

		FrequentSavingNameForm requestForm = new FrequentSavingNameForm();
		requestForm.setUserId(USER_ID);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(requestForm))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		FrequentSavingNameResponse response = mapper.readValue(result, FrequentSavingNameResponse.class);
		/* 検証 */
		int frequentMaxCount = 5;

		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertNull(response.getMessage());
		assertThat(response.getSavingList().size()).isLessThanOrEqualTo(frequentMaxCount);
	}

	@Test
	@Transactional(readOnly = true)
	void getFrequentSavingNameUserError01Test() throws Exception {

		FrequentSavingNameForm requestForm = new FrequentSavingNameForm();
		requestForm.setUserId(FAIL_USER_ID);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(requestForm))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		FrequentSavingNameResponse response = mapper.readValue(result, FrequentSavingNameResponse.class);
		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.authentication-error"), response.getMessage());
	}

	@Test
	@Transactional(readOnly = true)
	void getFrequentSavingNameUserError02Test() throws Exception {

		FrequentSavingNameForm requestForm = new FrequentSavingNameForm();
		requestForm.setUserId(NULL_USER_ID);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(requestForm))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		FrequentSavingNameResponse response = mapper.readValue(result, FrequentSavingNameResponse.class);
		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.authentication-error"), response.getMessage());
	}
}
