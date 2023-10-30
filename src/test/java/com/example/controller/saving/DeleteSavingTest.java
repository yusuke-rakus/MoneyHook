package com.example.controller.saving;

import com.example.common.Status;
import com.example.common.message.ErrorMessage;
import com.example.common.message.SuccessMessage;
import com.example.common.message.ValidatingMessage;
import com.example.domain.Saving;
import com.example.form.DeleteSavingForm;
import com.example.form.GetMonthlySavingListForm;
import com.example.mapper.SavingMapper;
import com.example.response.DeleteSavingResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DeleteSavingTest {

	final String URL = "/saving/deleteSaving";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";
	final String FAIL_USER_ID = "fail_user_id";
	final String NULL_USER_ID = null;
	final String TOKEN = "sample_token";
	final HttpHeaders HEADER = new HttpHeaders();

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private SavingMapper savingMapper;

	@Test
	@Transactional(readOnly = false)
	void deleteSavingTest() throws Exception {

		Long savingId = 1L;

		DeleteSavingForm req = new DeleteSavingForm();
		req.setUserId(USER_ID);
		req.setSavingId(savingId);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		DeleteSavingResponse response = mapper.readValue(result, DeleteSavingResponse.class);

		/* 検証 */
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(SuccessMessage.SAVING_DATA_DELETE_SUCCESSED, response.getMessage());

		GetMonthlySavingListForm form = new GetMonthlySavingListForm();
		form.setUserNo(2L);
		form.setMonth(Date.valueOf("2023-07-01"));
		Saving saving = savingMapper.getMonthlySavingList(form).stream().filter(i -> savingId.equals(i.getSavingId()))
				.findFirst().orElse(null);

		assertNull(saving);
	}

	@Test
	@Transactional(readOnly = false)
	void deleteSavingUserError01Test() throws Exception {

		Long savingId = 1L;

		DeleteSavingForm req = new DeleteSavingForm();
		req.setUserId(FAIL_USER_ID);
		req.setSavingId(savingId);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		DeleteSavingResponse response = mapper.readValue(result, DeleteSavingResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.AUTHENTICATION_ERROR, response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void deleteSavingUserError02Test() throws Exception {

		Long savingId = 1L;

		DeleteSavingForm req = new DeleteSavingForm();
		req.setUserId(NULL_USER_ID);
		req.setSavingId(savingId);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);
		HEADER.add("UserId", USER_ID);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		DeleteSavingResponse response = mapper.readValue(result, DeleteSavingResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.AUTHENTICATION_ERROR, response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void deleteSavingSavingIdError01Test() throws Exception {

		Long savingId = 9999L;

		DeleteSavingForm req = new DeleteSavingForm();
		req.setUserId(USER_ID);
		req.setSavingId(savingId);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		DeleteSavingResponse response = mapper.readValue(result, DeleteSavingResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.SAVING_DATA_NOT_FOUND, response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void deleteSavingSavingIdError02Test() throws Exception {

		Long savingId = null;

		DeleteSavingForm req = new DeleteSavingForm();
		req.setUserId(USER_ID);
		req.setSavingId(savingId);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		DeleteSavingResponse response = mapper.readValue(result, DeleteSavingResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ValidatingMessage.ID_EMPTY_ERROR, response.getMessage());
	}
}
