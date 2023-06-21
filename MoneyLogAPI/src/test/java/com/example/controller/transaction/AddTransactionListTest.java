package com.example.controller.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.Status;
import com.example.common.message.SuccessMessage;
import com.example.domain.Transaction;
import com.example.form.AddTransactionListForm;
import com.example.form.GetTimelineDataForm;
import com.example.mapper.TransactionMapper;
import com.example.response.AddTransactionListResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class AddTransactionListTest {

	final String URL = "/transaction/addTransactionList";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private TransactionMapper transactionMapper;

	@Test
	@Transactional(readOnly = false)
	void addTransactionListTest() throws Exception {

		Date transactionDate = Date.valueOf("2023-06-01");
		Integer transactionAmount = 1000;
		Integer transactionSign = -1;
		String transactionName = "収支リスト追加テスト";
		Long categoryId = 1l;
		Long subCategoryId = 1l;
		boolean fixedFlg = false;

		AddTransactionListForm req = new AddTransactionListForm();
		Transaction testTransaction = new Transaction();
		req.setUserId(USER_ID);
		testTransaction.setTransactionDate(transactionDate);
		testTransaction.setTransactionAmount(transactionAmount);
		testTransaction.setTransactionSign(transactionSign);
		testTransaction.setTransactionName(transactionName);
		testTransaction.setCategoryId(categoryId);
		testTransaction.setSubCategoryId(subCategoryId);
		testTransaction.setFixedFlg(fixedFlg);

		List<Transaction> testTransactionList = new ArrayList<Transaction>();
		testTransactionList.add(testTransaction);

		req.setTransactionList(testTransactionList);

		String result = mvc
				.perform(post(URL).content(mapper.writeValueAsString(req)).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString(Charset.defaultCharset());

		AddTransactionListResponse response = mapper.readValue(result, AddTransactionListResponse.class);

		/* 検証 */
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(SuccessMessage.TRANSACTION_INSERT_SUCCESSED, response.getMessage());

		GetTimelineDataForm form = new GetTimelineDataForm();
		form.setUserNo(2l);
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
}
