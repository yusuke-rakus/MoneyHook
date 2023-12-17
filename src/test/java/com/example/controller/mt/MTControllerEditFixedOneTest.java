package com.example.controller.mt;

import com.example.common.Status;
import com.example.common.message.Message;
import com.example.domain.MonthlyTransaction;
import com.example.form.EditOneFixedForm;
import com.example.form.GetFixedForm;
import com.example.form.MonthlyTransactionList;
import com.example.mapper.MonthlyTransactionMapper;
import com.example.response.EditOneFixedResponse;
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

import java.math.BigInteger;
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
class MTControllerEditFixedOneTest {

	final String URL = "/fixed/editOneFixed";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";
	final String TOKEN = "sample_token";
	final HttpHeaders HEADER = new HttpHeaders();


	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private Message message;

	@SpyBean
	private MonthlyTransactionMapper mtMapper;

	@AfterEach
	public void doAfter() {
		// 初期化
		Mockito.reset(mtMapper);
	}


	private MonthlyTransactionList createList(boolean create, Long mtId, String mtName, int mtAmount) {
		MonthlyTransactionList content = new MonthlyTransactionList();
		if (!create) {
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
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(form))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		GetFixedForm getForm = new GetFixedForm();
		getForm.setUserNo(2l);
		List<MonthlyTransaction> list = mtMapper.getFixed(getForm);

		EditOneFixedResponse response = mapper.readValue(result, EditOneFixedResponse.class);
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(message.get("success-message.monthly-transaction-edit-successed"), response.getMessage());
		BigInteger bi = BigInteger.valueOf(editAmount);
		assertEquals(true, list.stream()
				.anyMatch(e -> e.getMonthlyTransactionName().equals(editName) && e.getMonthlyTransactionAmount()
						.equals(bi)));
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
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(form))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		GetFixedForm getForm = new GetFixedForm();
		getForm.setUserNo(2l);
		List<MonthlyTransaction> list = mtMapper.getFixed(getForm);

		EditOneFixedResponse response = mapper.readValue(result, EditOneFixedResponse.class);
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(message.get("success-message.monthly-transaction-edit-successed"), response.getMessage());
		BigInteger bi = BigInteger.valueOf(editAmount);
		assertEquals(true, list.stream()
				.anyMatch(e -> e.getMonthlyTransactionName().equals(editName) && e.getMonthlyTransactionAmount()
						.equals(bi)));
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
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(form))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());


		EditOneFixedResponse response = mapper.readValue(result, EditOneFixedResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.authentication-error"), response.getMessage());

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
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(form))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());


		EditOneFixedResponse response = mapper.readValue(result, EditOneFixedResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.category-is-not-relational"), response.getMessage());

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
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(form))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());


		EditOneFixedResponse response = mapper.readValue(result, EditOneFixedResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.monthly-transaction-edit-error"), response.getMessage());

	}

}