package com.example.controller.mt;

import com.example.common.Status;
import com.example.common.message.ErrorMessage;
import com.example.common.message.SuccessMessage;
import com.example.domain.MonthlyTransaction;
import com.example.form.GetFixedForm;
import com.example.form.ReturnTargetForm;
import com.example.mapper.MonthlyTransactionMapper;
import com.example.response.ReturnTargetResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class MTControllerReturnTargetTest {

	final String URL = "/fixed/returnTarget";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";
	final String TOKEN = "sample_token";
	final HttpHeaders HEADER = new HttpHeaders();

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@SpyBean
	private MonthlyTransactionMapper mtMapper;

	@AfterEach
	public void doAfter() {
		// 初期化
		Mockito.reset(mtMapper);
	}

	@Order(1)
	@Test
	@Transactional(readOnly = false)
	void returnTargetSuccessTest() throws Exception {
		//準備
		Long mtId = 6l;
		ReturnTargetForm form = new ReturnTargetForm();
		form.setUserId(USER_ID);
		form.setMonthlyTransactionId(mtId);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		//実行
		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(form))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		GetFixedForm getForm = new GetFixedForm();
		getForm.setUserNo(2L);
		List<MonthlyTransaction> mtList = mtMapper.getFixed(getForm);

		//検証　
		ReturnTargetResponse response = mapper.readValue(result, ReturnTargetResponse.class);
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(SuccessMessage.MONTHLY_TRANSACTION_BACK_SUCCESSED, response.getMessage());
		assertEquals(true, mtList.stream().anyMatch(mt -> mt.getMonthlyTransactionId() == 6));
	}

	@Order(2)
	@Test
	@Transactional(readOnly = false)
	void returnTargetUnauthororizedTest() throws Exception {
		//準備
		Long mtId = 6l;
		String nonUserId = "aaa";
		ReturnTargetForm form = new ReturnTargetForm();
		form.setUserId(nonUserId);
		form.setMonthlyTransactionId(mtId);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		//実行
		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(form))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		GetFixedForm getForm = new GetFixedForm();
		getForm.setUserNo(2L);
		List<MonthlyTransaction> mtList = mtMapper.getFixed(getForm);

		//検証　
		ReturnTargetResponse response = mapper.readValue(result, ReturnTargetResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.AUTHENTICATION_ERROR, response.getMessage());
		assertEquals(false, mtList.stream().anyMatch(mt -> mt.getMonthlyTransactionId() == 6));
	}

	@Order(3)
	@Test
	@Transactional(readOnly = false)
	void returnTargetDBErrorTest() throws Exception {
		//準備
		Long mtId = 6l;
		ReturnTargetForm form = new ReturnTargetForm();
		form.setUserId(USER_ID);
		form.setMonthlyTransactionId(mtId);

		//returnTargetメソッドをモック化
		doThrow(new RuntimeException()).when(mtMapper).returnTarget(any());
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		//実行
		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(form))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		GetFixedForm getForm = new GetFixedForm();
		getForm.setUserNo(2L);
		List<MonthlyTransaction> mtList = mtMapper.getFixed(getForm);

		//検証　
		ReturnTargetResponse response = mapper.readValue(result, ReturnTargetResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.SYSTEM_ERROR, response.getMessage());
		assertEquals(false, mtList.stream().anyMatch(mt -> mt.getMonthlyTransactionId() == 6));
	}

}