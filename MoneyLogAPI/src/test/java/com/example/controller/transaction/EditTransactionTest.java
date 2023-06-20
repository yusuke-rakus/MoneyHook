package com.example.controller.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.sql.Date;
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
import com.example.form.EditTransactionForm;
import com.example.form.GetTimelineDataForm;
import com.example.mapper.TransactionMapper;
import com.example.response.EditTransactionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class EditTransactionTest {

	final String URL = "/transaction/editTransaction";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private TransactionMapper transactionMapper;

	@Test
	@Transactional(readOnly = false)
	void editTransactionTest() throws Exception {

		Long transactionId = 1l;
		Date transactionDate = Date.valueOf("2023-06-20");
		Integer transactionAmount = 1000;
		Integer transactionSign = -1;
		String transactionName = "収支編集テスト";
		Long categoryId = 1l;
		Long subCategoryId = 1l;
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

		String result = mvc
				.perform(post(URL).content(mapper.writeValueAsString(req)).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString(Charset.defaultCharset());

		EditTransactionResponse response = mapper.readValue(result, EditTransactionResponse.class);

		/* 検証 */
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(SuccessMessage.TRANSACTION_EDIT_SUCCESSED, response.getMessage());

		GetTimelineDataForm form = new GetTimelineDataForm();
		form.setUserNo(2l);
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
}
