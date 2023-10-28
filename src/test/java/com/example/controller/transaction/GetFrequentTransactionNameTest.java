package com.example.controller.transaction;

import com.example.common.Status;
import com.example.common.message.ErrorMessage;
import com.example.domain.Transaction;
import com.example.form.FrequentTransactionNameForm;
import com.example.response.FrequentTransactionNameResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GetFrequentTransactionNameTest {

	final String URL = "/transaction/getFrequentTransactionName";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";
	final String FAIL_USER_ID = "fail_user_id";
	final String NULL_USER_ID = null;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	@Transactional(readOnly = true)
	void getFrequentTransactionNameTest() throws Exception {

		FrequentTransactionNameForm requestForm = new FrequentTransactionNameForm();
		requestForm.setUserId(USER_ID);

		String result = mvc.perform(post(URL).content(mapper.writeValueAsString(requestForm))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		FrequentTransactionNameResponse response = mapper.readValue(result, FrequentTransactionNameResponse.class);

		/* 検証 */
		int frequentMaxCount = 6;

		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertNull(response.getMessage());
		assertThat(frequentMaxCount).isGreaterThanOrEqualTo(response.getTransactionList().size());
		for (Transaction tran : response.getTransactionList()) {
			assertNotNull(tran.getTransactionName());
			assertNotNull(tran.getCategoryId());
			assertNotNull(tran.getCategoryName());
			assertNotNull(tran.getSubCategoryId());
			assertNotNull(tran.getSubCategoryName());
		}
	}

	@Test
	@Transactional(readOnly = true)
	void getFrequentTransactionNameUserError01Test() throws Exception {

		FrequentTransactionNameForm requestForm = new FrequentTransactionNameForm();
		requestForm.setUserId(FAIL_USER_ID);

		String result = mvc.perform(post(URL).content(mapper.writeValueAsString(requestForm))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		FrequentTransactionNameResponse response = mapper.readValue(result, FrequentTransactionNameResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.AUTHENTICATION_ERROR, response.getMessage());
	}

	@Test
	@Transactional(readOnly = true)
	void getFrequentTransactionNameUserError02Test() throws Exception {

		FrequentTransactionNameForm requestForm = new FrequentTransactionNameForm();
		requestForm.setUserId(NULL_USER_ID);

		String result = mvc
				.perform(post(URL).content(mapper.writeValueAsString(requestForm))
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString(Charset.defaultCharset());

		FrequentTransactionNameResponse response = mapper.readValue(result, FrequentTransactionNameResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.AUTHENTICATION_ERROR, response.getMessage());
	}
}
