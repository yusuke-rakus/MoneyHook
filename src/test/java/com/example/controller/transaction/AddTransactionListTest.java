package com.example.controller.transaction;

import com.example.common.Status;
import com.example.common.message.Message;
import com.example.domain.Transaction;
import com.example.form.AddTransactionListForm;
import com.example.form.GetTimelineDataForm;
import com.example.mapper.TransactionMapper;
import com.example.response.AddTransactionListResponse;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AddTransactionListTest {

	final String URL = "/transaction/addTransactionList";
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
	void addTransactionListTest() throws Exception {

		Date transactionDate = Date.valueOf("2023-06-01");
		BigInteger transactionAmount = BigInteger.valueOf(1000);
		Integer transactionSign = -1;
		String transactionName = "収支リスト追加テスト";
		Long categoryId = 1L;
		Long subCategoryId = 1L;
		boolean fixedFlg = false;

		AddTransactionListForm req = new AddTransactionListForm();
		Transaction testTransaction = new Transaction();
		req.setUserId(USER_ID);
		setTestTransaction(testTransaction, transactionDate, transactionAmount, transactionSign, transactionName,
				categoryId, subCategoryId, fixedFlg);

		List<Transaction> testTransactionList = new ArrayList<>();
		testTransactionList.add(testTransaction);

		req.setTransactionList(testTransactionList);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		AddTransactionListResponse response = mapper.readValue(result, AddTransactionListResponse.class);

		/* 検証 */
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(message.get("success-message.transaction-insert-successed"), response.getMessage());

		GetTimelineDataForm form = new GetTimelineDataForm();
		form.setUserNo(2L);
		form.setMonth(Date.valueOf("2023-06-01"));
		Transaction transaction = transactionMapper.getTimelineData(form).stream()
				.filter(i -> transactionName.equals(i.getTransactionName())).collect(Collectors.toList()).get(0);

		assertEquals(transactionDate, transaction.getTransactionDate());
		assertEquals(transactionAmount, transaction.getTransactionAmount());
		assertEquals(transactionSign, transaction.getTransactionSign());
		assertEquals(categoryId, transaction.getCategoryId());
		assertEquals(subCategoryId, transaction.getSubCategoryId());
		assertEquals(fixedFlg, transaction.isFixedFlg());
	}

	@Test
	@Transactional(readOnly = false)
	void addTransactionListUserError01Test() throws Exception {

		Date transactionDate = Date.valueOf("2023-06-01");
		BigInteger transactionAmount = BigInteger.valueOf(1000);
		Integer transactionSign = -1;
		String transactionName = "収支リスト追加テスト";
		Long categoryId = 1L;
		Long subCategoryId = 1L;
		boolean fixedFlg = false;

		AddTransactionListForm req = new AddTransactionListForm();
		Transaction testTransaction = new Transaction();
		req.setUserId(FAIL_USER_ID);
		setTestTransaction(testTransaction, transactionDate, transactionAmount, transactionSign, transactionName,
				categoryId, subCategoryId, fixedFlg);

		List<Transaction> testTransactionList = new ArrayList<>();
		testTransactionList.add(testTransaction);

		req.setTransactionList(testTransactionList);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		AddTransactionListResponse response = mapper.readValue(result, AddTransactionListResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.authentication-error"), response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void addTransactionListUserError02Test() throws Exception {

		Date transactionDate = Date.valueOf("2023-06-01");
		BigInteger transactionAmount = BigInteger.valueOf(1000);
		Integer transactionSign = -1;
		String transactionName = "収支リスト追加テスト";
		Long categoryId = 1L;
		Long subCategoryId = 1L;
		boolean fixedFlg = false;

		AddTransactionListForm req = new AddTransactionListForm();
		Transaction testTransaction = new Transaction();
		req.setUserId(NULL_USER_ID);
		setTestTransaction(testTransaction, transactionDate, transactionAmount, transactionSign, transactionName,
				categoryId, subCategoryId, fixedFlg);

		List<Transaction> testTransactionList = new ArrayList<>();
		testTransactionList.add(testTransaction);

		req.setTransactionList(testTransactionList);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		AddTransactionListResponse response = mapper.readValue(result, AddTransactionListResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.authentication-error"), response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void addTransactionListTransactionDateErrorTest() throws Exception {

		Date transactionDate = null;
		BigInteger transactionAmount = BigInteger.valueOf(1000);
		Integer transactionSign = -1;
		String transactionName = "収支リスト追加テスト";
		Long categoryId = 1L;
		Long subCategoryId = 1L;
		boolean fixedFlg = false;

		AddTransactionListForm req = new AddTransactionListForm();
		Transaction testTransaction = new Transaction();
		req.setUserId(USER_ID);
		setTestTransaction(testTransaction, transactionDate, transactionAmount, transactionSign, transactionName,
				categoryId, subCategoryId, fixedFlg);

		List<Transaction> testTransactionList = new ArrayList<>();
		testTransactionList.add(testTransaction);

		req.setTransactionList(testTransactionList);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		AddTransactionListResponse response = mapper.readValue(result, AddTransactionListResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.transaction-error-data-exist"), response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void addTransactionListTransactionAmountErrorTest() throws Exception {

		Date transactionDate = Date.valueOf("2023-06-01");
		BigInteger transactionAmount = null;
		Integer transactionSign = -1;
		String transactionName = "収支リスト追加テスト";
		Long categoryId = 1L;
		Long subCategoryId = 1L;
		boolean fixedFlg = false;

		AddTransactionListForm req = new AddTransactionListForm();
		Transaction testTransaction = new Transaction();
		req.setUserId(USER_ID);
		setTestTransaction(testTransaction, transactionDate, transactionAmount, transactionSign, transactionName,
				categoryId, subCategoryId, fixedFlg);

		List<Transaction> testTransactionList = new ArrayList<>();
		testTransactionList.add(testTransaction);

		req.setTransactionList(testTransactionList);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		AddTransactionListResponse response = mapper.readValue(result, AddTransactionListResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.transaction-error-data-exist"), response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void addTransactionListTransactionSignErrorTest() throws Exception {

		Date transactionDate = Date.valueOf("2023-06-01");
		BigInteger transactionAmount = BigInteger.valueOf(1000);
		Integer transactionSign = null;
		String transactionName = "収支リスト追加テスト";
		Long categoryId = 1L;
		Long subCategoryId = 1L;
		boolean fixedFlg = false;

		AddTransactionListForm req = new AddTransactionListForm();
		Transaction testTransaction = new Transaction();
		req.setUserId(USER_ID);
		setTestTransaction(testTransaction, transactionDate, transactionAmount, transactionSign, transactionName,
				categoryId, subCategoryId, fixedFlg);

		List<Transaction> testTransactionList = new ArrayList<>();
		testTransactionList.add(testTransaction);

		req.setTransactionList(testTransactionList);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		AddTransactionListResponse response = mapper.readValue(result, AddTransactionListResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.transaction-error-data-exist"), response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void addTransactionListTransactionNameErrorTest() throws Exception {

		Date transactionDate = Date.valueOf("2023-06-01");
		BigInteger transactionAmount = BigInteger.valueOf(1000);
		Integer transactionSign = -1;
		String transactionName = "";
		Long categoryId = 1L;
		Long subCategoryId = 1L;
		boolean fixedFlg = false;

		AddTransactionListForm req = new AddTransactionListForm();
		Transaction testTransaction = new Transaction();
		req.setUserId(USER_ID);
		setTestTransaction(testTransaction, transactionDate, transactionAmount, transactionSign, transactionName,
				categoryId, subCategoryId, fixedFlg);

		List<Transaction> testTransactionList = new ArrayList<>();
		testTransactionList.add(testTransaction);

		req.setTransactionList(testTransactionList);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		AddTransactionListResponse response = mapper.readValue(result, AddTransactionListResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.transaction-error-data-exist"), response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void addTransactionListCategoryIdErrorTest() throws Exception {

		Date transactionDate = Date.valueOf("2023-06-01");
		BigInteger transactionAmount = BigInteger.valueOf(1000);
		Integer transactionSign = -1;
		String transactionName = "収支リスト追加テスト";
		Long categoryId = null;
		Long subCategoryId = 1L;
		boolean fixedFlg = false;

		AddTransactionListForm req = new AddTransactionListForm();
		Transaction testTransaction = new Transaction();
		req.setUserId(USER_ID);
		setTestTransaction(testTransaction, transactionDate, transactionAmount, transactionSign, transactionName,
				categoryId, subCategoryId, fixedFlg);

		List<Transaction> testTransactionList = new ArrayList<>();
		testTransactionList.add(testTransaction);

		req.setTransactionList(testTransactionList);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		AddTransactionListResponse response = mapper.readValue(result, AddTransactionListResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.transaction-error-data-exist"), response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void addTransactionListSubCategoryIdErrorTest() throws Exception {

		Date transactionDate = Date.valueOf("2023-06-01");
		BigInteger transactionAmount = BigInteger.valueOf(1000);
		Integer transactionSign = -1;
		String transactionName = "収支リスト追加テスト";
		Long categoryId = 1L;
		Long subCategoryId = null;
		boolean fixedFlg = false;

		AddTransactionListForm req = new AddTransactionListForm();
		Transaction testTransaction = new Transaction();
		req.setUserId(USER_ID);
		setTestTransaction(testTransaction, transactionDate, transactionAmount, transactionSign, transactionName,
				categoryId, subCategoryId, fixedFlg);

		List<Transaction> testTransactionList = new ArrayList<>();
		testTransactionList.add(testTransaction);

		req.setTransactionList(testTransactionList);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		AddTransactionListResponse response = mapper.readValue(result, AddTransactionListResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.transaction-error-data-exist"), response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void addTransactionListMultiErrorTest() throws Exception {

		List<Transaction> transactionList =
				new ArrayList<Transaction>(Arrays.asList(createTestTransaction(Date.valueOf("2023-06-01"),
						BigInteger.valueOf(1000), -1, "収支リスト追加テスト", 1L, 1L, false), createTestTransaction(null,
						BigInteger.valueOf(1000), -1, "収支リスト追加テスト", 1L, 1L, false),
						createTestTransaction(Date.valueOf("2023-06-01"), null, -1, "収支リスト追加テスト", 1L, 1L, false),
						createTestTransaction(Date.valueOf("2023-06-01"), BigInteger.valueOf(1000), null, "収支リスト追加テスト"
								, 1L, 1L, false), createTestTransaction(Date.valueOf("2023-06-01"),
								BigInteger.valueOf(1000), -1, "", 1L, 1L, false), createTestTransaction(Date.valueOf(
										"2023-06-01"), BigInteger.valueOf(1000), -1, "収支リスト追加テスト", null, 1L, false),
						createTestTransaction(Date.valueOf("2023-06-01"), BigInteger.valueOf(1000), -1, "収支リスト追加テスト",
								1L, null, false), createTestTransaction(Date.valueOf("2023-06-01"),
								BigInteger.valueOf(1000), -1, "収支リスト追加テスト", 99L, 1L, false),
						createTestTransaction(Date.valueOf("2023-06-01"), BigInteger.valueOf(1000), -1, "収支リスト追加テスト",
								1L, 99L, false)));

		AddTransactionListForm req = new AddTransactionListForm();
		req.setUserId(USER_ID);
		req.setTransactionList(transactionList);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		AddTransactionListResponse response = mapper.readValue(result, AddTransactionListResponse.class);

		/* 検証 */
		int errorListSize = 8;

		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.transaction-error-data-exist"), response.getMessage());
		assertEquals(errorListSize, response.getErrorTransaction().size());
	}

	private void setTestTransaction(Transaction tran, Date date, BigInteger amount, Integer sign, String name,
			Long categoryId, Long subCategoryId, boolean fixedFlg) {
		tran.setTransactionDate(date);
		tran.setTransactionAmount(amount);
		tran.setTransactionSign(sign);
		tran.setTransactionName(name);
		tran.setCategoryId(categoryId);
		tran.setSubCategoryId(subCategoryId);
		tran.setFixedFlg(fixedFlg);
	}

	private Transaction createTestTransaction(Date date, BigInteger amount, Integer sign, String name, Long categoryId,
			Long subCategoryId, boolean fixedFlg) {
		Transaction tran = new Transaction();

		tran.setTransactionDate(date);
		tran.setTransactionAmount(amount);
		tran.setTransactionSign(sign);
		tran.setTransactionName(name);
		tran.setCategoryId(categoryId);
		tran.setSubCategoryId(subCategoryId);
		tran.setFixedFlg(fixedFlg);
		return tran;
	}
}
