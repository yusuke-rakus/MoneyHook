package com.example.controller.mt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;
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
import com.example.form.EditOneFixedForm;
import com.example.form.GetFixedForm;
import com.example.form.MonthlyTransactionList;
import com.example.mapper.MonthlyTransactionMapper;
import com.example.response.EditOneFixedResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class MTControllerEditFixedOneTest {
	
	final String URL = "/fixed/editOneFixed";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";
	
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@SpyBean
	private MonthlyTransactionMapper mtMapper;
	
	
	private MonthlyTransactionList createList(
			boolean create,
			Long mtId,
			String mtName,
			int mtAmount
			) {
		MonthlyTransactionList content = new MonthlyTransactionList();
		if(!create) {
			content.setMonthlyTransactionId(mtId);
		}
		content.setMonthlyTransactionName(mtName);
		content.setMonthlyTransactionAmount(mtAmount);
		content.setMonthlyTransactionSign(-1);
		content.setMonthlyTransactionDate(20);
		content.setCategoryId(3l);
		content.setSubCategoryId(7l);
		content.setSubCategoryName("");
		return content;
	}

	@Order(1)
	@Test
	@Transactional(readOnly = false)
	void updateEditOneFixedSuccessTest() throws Exception {
		String editName = "junit";
		int editAmount = 980;
		MonthlyTransactionList editMt = createList(false, 2l, editName, editAmount);
		
		EditOneFixedForm form = new EditOneFixedForm();
		form.setUserId(USER_ID);
		form.setMonthlyTransaction(editMt);
		
		String result = mvc.perform(post(URL)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk()) 
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset());
		
		GetFixedForm getForm = new GetFixedForm();
		getForm.setUserNo(2l);
		List<MonthlyTransaction> list =  mtMapper.getFixed(getForm);
		
		EditOneFixedResponse response = mapper.readValue(result, EditOneFixedResponse.class);
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(SuccessMessage.MONTHLY_TRANSACTION_EDIT_SUCCESSED, response.getMessage());
		BigInteger bi = BigInteger.valueOf(editAmount);
		assertEquals(true, list.stream()
				.anyMatch(e -> e.getMonthlyTransactionName().equals(editName)
						&& e.getMonthlyTransactionAmount().equals(bi)));
	}
	
	@Order(2)
	@Test
	@Transactional(readOnly = false)
	void newEditOneFixedSuccessTest() throws Exception {
		String editName = "junit2";
		int editAmount = 8000;
		MonthlyTransactionList editMt = createList(true, null, editName, editAmount);
		
		EditOneFixedForm form = new EditOneFixedForm();
		form.setUserId(USER_ID);
		form.setMonthlyTransaction(editMt);
		
		String result = mvc.perform(post(URL)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk()) 
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset());
		
		GetFixedForm getForm = new GetFixedForm();
		getForm.setUserNo(2l);
		List<MonthlyTransaction> list =  mtMapper.getFixed(getForm);
		
		EditOneFixedResponse response = mapper.readValue(result, EditOneFixedResponse.class);
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(SuccessMessage.MONTHLY_TRANSACTION_EDIT_SUCCESSED, response.getMessage());
		BigInteger bi = BigInteger.valueOf(editAmount);
		assertEquals(true, list.stream()
				.anyMatch(e -> e.getMonthlyTransactionName().equals(editName) 
						&& e.getMonthlyTransactionAmount().equals(bi)));
	}
	
	@Order(3)
	@Test
	@Transactional(readOnly = false)
	void editOneFixedUnauthorizeTest() throws Exception {
		String editName = "junit2";
		int editAmount = 8000;
		MonthlyTransactionList editMt = createList(true, null, editName, editAmount);
		
		EditOneFixedForm form = new EditOneFixedForm();
		form.setUserId("aaa");
		form.setMonthlyTransaction(editMt);
		
		String result = mvc.perform(post(URL)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk()) 
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset());
		
		
		EditOneFixedResponse response = mapper.readValue(result, EditOneFixedResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.AUTHENTICATION_ERROR, response.getMessage());
		
	}
	
	@Order(4)
	@Test
	@Transactional(readOnly = false)
	void editOneFixedRelationErrorTest() throws Exception {
		String editName = "junit2";
		int editAmount = 8000;
		MonthlyTransactionList editMt = createList(false, 2l, editName, editAmount);
		editMt.setSubCategoryId(1l);
		
		EditOneFixedForm form = new EditOneFixedForm();
		form.setUserId(USER_ID);
		form.setMonthlyTransaction(editMt);
		
		String result = mvc.perform(post(URL)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk()) 
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset());
		
		
		EditOneFixedResponse response = mapper.readValue(result, EditOneFixedResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.CATEGORY_IS_NOT_RELATIONAL, response.getMessage());
		
	}
	
	@Order(5)
	@Test
	@Transactional(readOnly = false)
	void editOneFixedDbErrorTest() throws Exception {
		String editName = "junit2";
		int editAmount = 8000;
		MonthlyTransactionList editMt = createList(true, null, editName, editAmount);
		
		EditOneFixedForm form = new EditOneFixedForm();
		form.setUserId(USER_ID);
		form.setMonthlyTransaction(editMt);
		
		doThrow(new RuntimeException()).when(mtMapper).registerFixed(any());
		
		String result = mvc.perform(post(URL)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk()) 
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset());
		
		
		EditOneFixedResponse response = mapper.readValue(result, EditOneFixedResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.MONTHLY_TRANSACTION_EDIT_ERROR, response.getMessage());
		
	}
	
}