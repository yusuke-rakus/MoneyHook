package com.example.controller.mt;

import com.example.common.Status;
import com.example.common.message.Message;
import com.example.form.DeleteFixedForm;
import com.example.form.GetDeletedFixedForm;
import com.example.mapper.MonthlyTransactionMapper;
import com.example.response.GetDeletedFixedResponse;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class MTControllerGrtDeletedFixedTest {

	final String URL = "/fixed/getDeletedFixed";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";
	final String TOKEN = "sample_token";
	final HttpHeaders HEADER = new HttpHeaders();

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private JdbcTemplate template;

	@Autowired
	private Message message;

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
	void getDeletedFixesSuccessTest() throws Exception {
		//準備
		GetDeletedFixedForm form = new GetDeletedFixedForm();
		form.setUserId(USER_ID);

		//1件計算対象外に変更
		DeleteFixedForm deleteForm = new DeleteFixedForm();
		deleteForm.setUserNo(2L);
		deleteForm.setMonthlyTransactionId(1L);
		mtMapper.deleteFixed(deleteForm);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);


		//実行
		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(form))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());


		//検証　
		GetDeletedFixedResponse response = mapper.readValue(result, GetDeletedFixedResponse.class);
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(2, response.getMonthlyTransactionList().size());

	}

	@Order(2)
	@Test
	@Transactional(readOnly = false)
	void getDeletedFixesUnauthorizedTest() throws Exception {
		//準備
		String userId = "aaa";
		GetDeletedFixedForm form = new GetDeletedFixedForm();
		form.setUserId(userId);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		//実行
		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(form))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());


		//検証　
		GetDeletedFixedResponse response = mapper.readValue(result, GetDeletedFixedResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.authentication-error"), response.getMessage());

	}

	@Order(3)
	@Test
	@Transactional(readOnly = false)
	void getDeletedFixesList0Test() throws Exception {
		//準備
		GetDeletedFixedForm form = new GetDeletedFixedForm();
		form.setUserId(USER_ID);

		//1件計算対象外に変更に全件対称に変更
		String sql = "UPDATE monthly_transaction SET include_flg = 1";
		template.update(sql);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		//実行
		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(form))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());


		//検証　
		GetDeletedFixedResponse response = mapper.readValue(result, GetDeletedFixedResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.monthly-transaction-not-exists"), response.getMessage());

	}

	@Order(4)
	@Test
	@Transactional(readOnly = false)
	void getDeletedFixesDBErrorTest() throws Exception {
		//準備
		GetDeletedFixedForm form = new GetDeletedFixedForm();
		form.setUserId(USER_ID);

		//モック化
		doThrow(new RuntimeException()).when(mtMapper).getDeletedFixed(any());
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		//実行
		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(form))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());


		//検証　
		GetDeletedFixedResponse response = mapper.readValue(result, GetDeletedFixedResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.system-error"), response.getMessage());

	}

}