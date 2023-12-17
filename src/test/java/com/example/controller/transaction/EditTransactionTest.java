package com.example.controller.transaction;

import com.example.common.Status;
import com.example.common.message.Message;
import com.example.domain.Transaction;
import com.example.form.EditTransactionForm;
import com.example.form.GetTimelineDataForm;
import com.example.mapper.TransactionMapper;
import com.example.response.EditTransactionResponse;
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
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EditTransactionTest {

	final String URL = "/transaction/editTransaction";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";
	final String FAIL_USER_ID = "fail-user-id";
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
	void editTransactionTest() throws Exception {

		Long transactionId = 1L;
		Date transactionDate = Date.valueOf("2023-06-20");
		BigInteger transactionAmount = BigInteger.valueOf(1000);
		Integer transactionSign = -1;
		String transactionName = "収支編集テスト";
		Long categoryId = 1L;
		Long subCategoryId = 1L;
		boolean fixedFlg = false;

		EditTransactionForm req = new EditTransactionForm();
		req.setUserId(USER_ID);
		req.setTransactionId(transactionId);
		req.setTransactionDate(transactionDate);
		req.setTransactionAmount(transactionAmount);
		req.setTransactionSign(transactionSign);
		req.setTransactionName(transactionName);
		req.setCategoryId(categoryId);
		req.setSubCategoryId(subCategoryId);
		req.setFixedFlg(fixedFlg);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		EditTransactionResponse response = mapper.readValue(result, EditTransactionResponse.class);

		/* 検証 */
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(message.get("success-message.transaction-edit-successed"), response.getMessage());

		GetTimelineDataForm form = new GetTimelineDataForm();
		form.setUserNo(2L);
		form.setMonth(Date.valueOf("2023-06-01"));
		Transaction transaction = transactionMapper.getTimelineData(form).stream()
				.filter(i -> transactionName.equals(i.getTransactionName())).collect(Collectors.toList()).get(0);

		assertEquals(transactionId, transaction.getTransactionId());
		assertEquals(transactionDate, transaction.getTransactionDate());
		assertEquals(transactionAmount, transaction.getTransactionAmount());
		assertEquals(transactionSign, transaction.getTransactionSign());
		assertEquals(categoryId, transaction.getCategoryId());
		assertEquals(subCategoryId, transaction.getSubCategoryId());
		assertEquals(fixedFlg, transaction.isFixedFlg());
	}

	@Test
	@Transactional(readOnly = false)
	void editTransactionUserError01Test() throws Exception {

		Long transactionId = 1L;
		Date transactionDate = Date.valueOf("2023-06-20");
		BigInteger transactionAmount = BigInteger.valueOf(1000);
		Integer transactionSign = -1;
		String transactionName = "収支編集テスト";
		Long categoryId = 1L;
		Long subCategoryId = 1L;
		boolean fixedFlg = false;

		EditTransactionForm req = new EditTransactionForm();
		req.setUserId(FAIL_USER_ID);
		req.setTransactionId(transactionId);
		req.setTransactionDate(transactionDate);
		req.setTransactionAmount(transactionAmount);
		req.setTransactionSign(transactionSign);
		req.setTransactionName(transactionName);
		req.setCategoryId(categoryId);
		req.setSubCategoryId(subCategoryId);
		req.setFixedFlg(fixedFlg);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		EditTransactionResponse response = mapper.readValue(result, EditTransactionResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.authentication-error"), response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void editTransactionUserError02Test() throws Exception {

		Long transactionId = 1L;
		Date transactionDate = Date.valueOf("2023-06-20");
		BigInteger transactionAmount = BigInteger.valueOf(1000);
		Integer transactionSign = -1;
		String transactionName = "収支編集テスト";
		Long categoryId = 1L;
		Long subCategoryId = 1L;
		boolean fixedFlg = false;

		EditTransactionForm req = new EditTransactionForm();
		req.setUserId(NULL_USER_ID);
		req.setTransactionId(transactionId);
		req.setTransactionDate(transactionDate);
		req.setTransactionAmount(transactionAmount);
		req.setTransactionSign(transactionSign);
		req.setTransactionName(transactionName);
		req.setCategoryId(categoryId);
		req.setSubCategoryId(subCategoryId);
		req.setFixedFlg(fixedFlg);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		EditTransactionResponse response = mapper.readValue(result, EditTransactionResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.authentication-error"), response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void editTransactionTransactionIdErrorTest() throws Exception {

		Long transactionId = 9999L;
		Date transactionDate = Date.valueOf("2023-06-20");
		BigInteger transactionAmount = BigInteger.valueOf(1000);
		Integer transactionSign = -1;
		String transactionName = "収支編集テスト";
		Long categoryId = 1L;
		Long subCategoryId = 1L;
		boolean fixedFlg = false;

		EditTransactionForm req = new EditTransactionForm();
		req.setUserId(USER_ID);
		req.setTransactionId(transactionId);
		req.setTransactionDate(transactionDate);
		req.setTransactionAmount(transactionAmount);
		req.setTransactionSign(transactionSign);
		req.setTransactionName(transactionName);
		req.setCategoryId(categoryId);
		req.setSubCategoryId(subCategoryId);
		req.setFixedFlg(fixedFlg);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		EditTransactionResponse response = mapper.readValue(result, EditTransactionResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.transaction-data-not-found"), response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void editTransactionTransactionDateErrorTest() throws Exception {

		Long transactionId = 1L;
		Date transactionDate = null;
		BigInteger transactionAmount = BigInteger.valueOf(1000);
		Integer transactionSign = -1;
		String transactionName = "収支編集テスト";
		Long categoryId = 1L;
		Long subCategoryId = 1L;
		boolean fixedFlg = false;

		EditTransactionForm req = new EditTransactionForm();
		req.setUserId(USER_ID);
		req.setTransactionId(transactionId);
		req.setTransactionDate(transactionDate);
		req.setTransactionAmount(transactionAmount);
		req.setTransactionSign(transactionSign);
		req.setTransactionName(transactionName);
		req.setCategoryId(categoryId);
		req.setSubCategoryId(subCategoryId);
		req.setFixedFlg(fixedFlg);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		EditTransactionResponse response = mapper.readValue(result, EditTransactionResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("validating-message.date-empty-error"), response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void editTransactionTransactionAmountErrorTest() throws Exception {

		Long transactionId = 1L;
		Date transactionDate = Date.valueOf("2023-06-20");
		BigInteger transactionAmount = null;
		Integer transactionSign = -1;
		String transactionName = "収支編集テスト";
		Long categoryId = 1L;
		Long subCategoryId = 1L;
		boolean fixedFlg = false;

		EditTransactionForm req = new EditTransactionForm();
		req.setUserId(USER_ID);
		req.setTransactionId(transactionId);
		req.setTransactionDate(transactionDate);
		req.setTransactionAmount(transactionAmount);
		req.setTransactionSign(transactionSign);
		req.setTransactionName(transactionName);
		req.setCategoryId(categoryId);
		req.setSubCategoryId(subCategoryId);
		req.setFixedFlg(fixedFlg);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		EditTransactionResponse response = mapper.readValue(result, EditTransactionResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("validating-message.transaction-amount-empty-error"), response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void editTransactionTransactionSignErrorTest() throws Exception {

		Long transactionId = 1L;
		Date transactionDate = Date.valueOf("2023-06-20");
		BigInteger transactionAmount = BigInteger.valueOf(1000);
		Integer transactionSign = null;
		String transactionName = "収支編集テスト";
		Long categoryId = 1L;
		Long subCategoryId = 1L;
		boolean fixedFlg = false;

		EditTransactionForm req = new EditTransactionForm();
		req.setUserId(USER_ID);
		req.setTransactionId(transactionId);
		req.setTransactionDate(transactionDate);
		req.setTransactionAmount(transactionAmount);
		req.setTransactionSign(transactionSign);
		req.setTransactionName(transactionName);
		req.setCategoryId(categoryId);
		req.setSubCategoryId(subCategoryId);
		req.setFixedFlg(fixedFlg);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		EditTransactionResponse response = mapper.readValue(result, EditTransactionResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("validating-message.transaction-amount-empty-error"), response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void editTransactionTransactionNameErrorTest() throws Exception {

		Long transactionId = 1L;
		Date transactionDate = Date.valueOf("2023-06-20");
		BigInteger transactionAmount = BigInteger.valueOf(1000);
		Integer transactionSign = -1;
		String transactionName = "";
		Long categoryId = 1L;
		Long subCategoryId = 1L;
		boolean fixedFlg = false;

		EditTransactionForm req = new EditTransactionForm();
		req.setUserId(USER_ID);
		req.setTransactionId(transactionId);
		req.setTransactionDate(transactionDate);
		req.setTransactionAmount(transactionAmount);
		req.setTransactionSign(transactionSign);
		req.setTransactionName(transactionName);
		req.setCategoryId(categoryId);
		req.setSubCategoryId(subCategoryId);
		req.setFixedFlg(fixedFlg);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		EditTransactionResponse response = mapper.readValue(result, EditTransactionResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("validating-message.transaction-name-empty-error"), response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void editTransactionCategoryIdError01Test() throws Exception {

		Long transactionId = 1L;
		Date transactionDate = Date.valueOf("2023-06-20");
		BigInteger transactionAmount = BigInteger.valueOf(1000);
		Integer transactionSign = -1;
		String transactionName = "収支編集テスト";
		Long categoryId = null;
		Long subCategoryId = 1L;
		boolean fixedFlg = false;

		EditTransactionForm req = new EditTransactionForm();
		req.setUserId(USER_ID);
		req.setTransactionId(transactionId);
		req.setTransactionDate(transactionDate);
		req.setTransactionAmount(transactionAmount);
		req.setTransactionSign(transactionSign);
		req.setTransactionName(transactionName);
		req.setCategoryId(categoryId);
		req.setSubCategoryId(subCategoryId);
		req.setFixedFlg(fixedFlg);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		EditTransactionResponse response = mapper.readValue(result, EditTransactionResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("validating-message.category-not-select-error"), response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void editTransactionCategoryIdError02Test() throws Exception {

		Long transactionId = 1L;
		Date transactionDate = Date.valueOf("2023-06-20");
		BigInteger transactionAmount = BigInteger.valueOf(1000);
		Integer transactionSign = -1;
		String transactionName = "収支編集テスト";
		Long categoryId = 99L;
		Long subCategoryId = 1L;
		boolean fixedFlg = false;

		EditTransactionForm req = new EditTransactionForm();
		req.setUserId(USER_ID);
		req.setTransactionId(transactionId);
		req.setTransactionDate(transactionDate);
		req.setTransactionAmount(transactionAmount);
		req.setTransactionSign(transactionSign);
		req.setTransactionName(transactionName);
		req.setCategoryId(categoryId);
		req.setSubCategoryId(subCategoryId);
		req.setFixedFlg(fixedFlg);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		EditTransactionResponse response = mapper.readValue(result, EditTransactionResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.category-not-found-error"), response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void editTransactionSubCategoryIdError01Test() throws Exception {

		Long transactionId = 1L;
		Date transactionDate = Date.valueOf("2023-06-20");
		BigInteger transactionAmount = BigInteger.valueOf(1000);
		Integer transactionSign = -1;
		String transactionName = "収支編集テスト";
		Long categoryId = 1L;
		Long subCategoryId = 9999L;
		boolean fixedFlg = false;

		EditTransactionForm req = new EditTransactionForm();
		req.setUserId(USER_ID);
		req.setTransactionId(transactionId);
		req.setTransactionDate(transactionDate);
		req.setTransactionAmount(transactionAmount);
		req.setTransactionSign(transactionSign);
		req.setTransactionName(transactionName);
		req.setCategoryId(categoryId);
		req.setSubCategoryId(subCategoryId);
		req.setFixedFlg(fixedFlg);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		EditTransactionResponse response = mapper.readValue(result, EditTransactionResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.transaction-data-insert-failed"), response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void editTransactionSubCategoryIdError02Test() throws Exception {

		Long transactionId = 1L;
		Date transactionDate = Date.valueOf("2023-06-20");
		BigInteger transactionAmount = BigInteger.valueOf(1000);
		Integer transactionSign = -1;
		String transactionName = "収支編集テスト";
		Long categoryId = 1L;
		Long subCategoryId = null;
		String subCategoryName = "";
		boolean fixedFlg = false;

		EditTransactionForm req = new EditTransactionForm();
		req.setUserId(USER_ID);
		req.setTransactionId(transactionId);
		req.setTransactionDate(transactionDate);
		req.setTransactionAmount(transactionAmount);
		req.setTransactionSign(transactionSign);
		req.setTransactionName(transactionName);
		req.setCategoryId(categoryId);
		req.setSubCategoryId(subCategoryId);
		req.setSubCategoryName(subCategoryName);
		req.setFixedFlg(fixedFlg);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		EditTransactionResponse response = mapper.readValue(result, EditTransactionResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("validating-message.sub-category-no-select-and-input-error"), response.getMessage());
	}
}
