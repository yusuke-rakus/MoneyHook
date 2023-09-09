package com.example.controller.mt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.Status;
import com.example.common.message.ErrorMessage;
import com.example.common.message.SuccessMessage;
import com.example.domain.MonthlyTransaction;
import com.example.form.DeleteFixedForm;
import com.example.form.GetFixedForm;
import com.example.mapper.MonthlyTransactionMapper;
import com.example.response.DeleteFixedResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class MTControllerDeleteFixedFromTableTest {
	
	final String URL = "/fixed/deleteFixedFromTable";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	
	@SpyBean
	private MonthlyTransactionMapper mtMapper;

	@Order(1)
	@Test
	@Transactional(readOnly = false)
	void deleteFixedFromTableSuccessTest() throws Exception {
		
		Long mtId = 2l;
		DeleteFixedForm form = new DeleteFixedForm();
		form.setUserId(USER_ID);
		form.setMonthlyTransactionId(mtId);
		
		String result = mvc.perform(post(URL)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk()) 
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset());
		
		GetFixedForm getForm = new GetFixedForm();
		getForm.setUserNo(2L);
		List<MonthlyTransaction> mtList = mtMapper.getFixed(getForm);
		
		DeleteFixedResponse response = mapper.readValue(result, DeleteFixedResponse.class);
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(SuccessMessage.MONTHLY_TRANSACTION_DELETE_FROM_TABLE_SUCCESSED, response.getMessage());
		
		boolean check = mtList.stream().anyMatch(m -> m.getMonthlyTransactionId() == 2);
		assertEquals(false, check);
	}
	
	@Order(2)
	@Test
	@Transactional(readOnly = false)
	void deleteFixedFromTableUnAuthorizeTest() throws Exception {
		
		Long mtId = 2l;
		String nonUserId = "b77a6e94-6aa2-47ea-87dd-129f580fb669";
		DeleteFixedForm form = new DeleteFixedForm();
		form.setUserId(nonUserId);
		form.setMonthlyTransactionId(mtId);
		
		String result = mvc.perform(post(URL)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk()) 
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset());
		
		GetFixedForm getForm = new GetFixedForm();
		getForm.setUserNo(2L);
		List<MonthlyTransaction> mtList = mtMapper.getFixed(getForm);
		
		DeleteFixedResponse response = mapper.readValue(result, DeleteFixedResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.AUTHENTICATION_ERROR, response.getMessage());
		
		boolean check = mtList.stream().anyMatch(m -> m.getMonthlyTransactionId() == 2);
		assertEquals(true, check);
	}
	
	@Order(3)
	@Test
	@Transactional(readOnly = false)
	void deleteFixedFromTableDbErrorTest() throws Exception {
		//準備
		Long mtId = 2l;
		DeleteFixedForm form = new DeleteFixedForm();
		form.setUserId(USER_ID);
		form.setMonthlyTransactionId(mtId);
		
		//モック化
		doThrow(new RuntimeException()).when(mtMapper).deleteFixedFromTable(any());
		
		//実行
		String result = mvc.perform(post(URL)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk()) 
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset());
		
		GetFixedForm getForm = new GetFixedForm();
		getForm.setUserNo(2L);
		List<MonthlyTransaction> mtList = mtMapper.getFixed(getForm);
		
		//検証
		DeleteFixedResponse response = mapper.readValue(result, DeleteFixedResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.DELETE_FIXED_ERROR, response.getMessage());
		
		boolean check = mtList.stream().anyMatch(m -> m.getMonthlyTransactionId() == 2);
		assertEquals(true, check);
	}
}