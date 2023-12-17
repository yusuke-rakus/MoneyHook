package com.example.controller.transaction;

import com.example.common.Status;
import com.example.common.message.Message;
import com.example.domain.Transaction;
import com.example.form.DeleteTransactionForm;
import com.example.form.GetTransactionForm;
import com.example.mapper.TransactionMapper;
import com.example.response.DeleteTransactionResponse;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DeleteTransactionTest {

	final String URL = "/transaction/deleteTransaction";
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
	private TransactionMapper transactionMapper;

	@Autowired
	private Message message;

	@Test
	@Transactional(readOnly = false)
	void deleteTransactionTest() throws Exception {

		Long transactionId = 1L;

		DeleteTransactionForm req = new DeleteTransactionForm();
		req.setUserId(USER_ID);
		req.setTransactionId(transactionId);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		DeleteTransactionResponse response = mapper.readValue(result, DeleteTransactionResponse.class);

		/* 検証 */
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(message.get("success-message.transaction-delete-successed"), response.getMessage());

		GetTransactionForm form = new GetTransactionForm();
		form.setUserNo(2L);
		form.setTransactionId(transactionId);
		Transaction transaction = transactionMapper.getTransaction(form);

		assertNull(transaction);
	}

	@Test
	@Transactional(readOnly = false)
	void deleteTransactionUserError01Test() throws Exception {

		Long transactionId = 1L;

		DeleteTransactionForm req = new DeleteTransactionForm();
		req.setUserId(FAIL_USER_ID);
		req.setTransactionId(transactionId);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		DeleteTransactionResponse response = mapper.readValue(result, DeleteTransactionResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.authentication-error"), response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void deleteTransactionUserError02Test() throws Exception {

		Long transactionId = 1L;

		DeleteTransactionForm req = new DeleteTransactionForm();
		req.setUserId(NULL_USER_ID);
		req.setTransactionId(transactionId);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		DeleteTransactionResponse response = mapper.readValue(result, DeleteTransactionResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.authentication-error"), response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void deleteTransactionTransactionIdError01Test() throws Exception {

		Long transactionId = null;

		DeleteTransactionForm req = new DeleteTransactionForm();
		req.setUserId(USER_ID);
		req.setTransactionId(transactionId);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		DeleteTransactionResponse response = mapper.readValue(result, DeleteTransactionResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("validating-message.transaction-id-not-select-error"), response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void deleteTransactionTransactionIdError02Test() throws Exception {

		Long transactionId = 9999L;

		DeleteTransactionForm req = new DeleteTransactionForm();
		req.setUserId(USER_ID);
		req.setTransactionId(transactionId);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		DeleteTransactionResponse response = mapper.readValue(result, DeleteTransactionResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.transaction-data-not-found"), response.getMessage());
	}
}
