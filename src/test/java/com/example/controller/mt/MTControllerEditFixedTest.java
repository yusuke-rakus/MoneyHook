package com.example.controller.mt;

import com.example.common.Status;
import com.example.common.message.ErrorMessage;
import com.example.common.message.SuccessMessage;
import com.example.domain.MonthlyTransaction;
import com.example.form.EditFixedForm;
import com.example.form.GetFixedForm;
import com.example.form.MonthlyTransactionList;
import com.example.mapper.MonthlyTransactionMapper;
import com.example.response.EditFixedResponse;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class MTControllerEditFixedTest {

	final String URL = "/fixed/editFixed";
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
	void editFixedSuccessTest() throws Exception {

		List<MonthlyTransactionList> mtlist = new ArrayList<>();
		String editName = "junit1";
		String addName = "jnuit2";
		mtlist.add(createList(false, null, editName, 1000));
		mtlist.add(createList(false, null, addName, 2000));


		EditFixedForm form = new EditFixedForm();
		form.setMonthlyTransactionList(mtlist);
		form.setUserId(USER_ID);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(form))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		GetFixedForm getForm = new GetFixedForm();
		getForm.setUserNo(2l);
		List<MonthlyTransaction> list = mtMapper.getFixed(getForm);
		Map<String, MonthlyTransaction> map = list.stream()
				.collect(Collectors.toMap(e -> e.getMonthlyTransactionName(), e -> e));

		EditFixedResponse response = mapper.readValue(result, EditFixedResponse.class);
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(SuccessMessage.MONTHLY_TRANSACTION_EDIT_SUCCESSED, response.getMessage());
		assertEquals(editName, map.get(editName).getMonthlyTransactionName());
		assertNotNull(map.get(addName));
	}

	@Order(2)
	@Test
	@Transactional(readOnly = false)
	void editFixedUnauthorizeTest() throws Exception {

		List<MonthlyTransactionList> mtlist = new ArrayList<>();
		String editName = "junit1";
		String addName = "jnuit2";
		mtlist.add(createList(false, 2l, editName, 1000));
		mtlist.add(createList(true, null, addName, 2000));

		EditFixedForm form = new EditFixedForm();
		form.setMonthlyTransactionList(mtlist);
		form.setUserId("aaa");
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(form))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		EditFixedResponse response = mapper.readValue(result, EditFixedResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.AUTHENTICATION_ERROR, response.getMessage());

	}

	/*TODO
	 * 片方失敗した場合、全件ロールバックするか
	 * */
	@Order(3)
	@Test
	@Transactional(readOnly = false)
	void editFixedDBErrorTest() throws Exception {

		List<MonthlyTransactionList> mtlist = new ArrayList<>();
		String editName = "junit1";
		String addName = "jnuit2";
		mtlist.add(createList(false, 2l, editName, 1000));
		mtlist.add(createList(true, null, addName, 2000));

		EditFixedForm form = new EditFixedForm();
		form.setMonthlyTransactionList(mtlist);
		form.setUserId(USER_ID);

		doThrow(new RuntimeException()).when(mtMapper).registerFixed(any());
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(form))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		GetFixedForm getForm = new GetFixedForm();
		getForm.setUserNo(2l);
		List<MonthlyTransaction> list = mtMapper.getFixed(getForm);
		Map<String, MonthlyTransaction> map = list.stream()
				.collect(Collectors.toMap(e -> e.getMonthlyTransactionName(), e -> e));

		EditFixedResponse response = mapper.readValue(result, EditFixedResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.MONTHLY_TRANSACTION_EDIT_ERROR, response.getMessage());
		assertEquals(editName, map.get(editName).getMonthlyTransactionName());
	}
}