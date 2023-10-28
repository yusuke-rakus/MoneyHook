package com.example.controller.saving;

import com.example.common.Status;
import com.example.common.message.ErrorMessage;
import com.example.common.message.SuccessMessage;
import com.example.common.message.ValidatingMessage;
import com.example.domain.Saving;
import com.example.form.AddSavingForm;
import com.example.form.GetMonthlySavingListForm;
import com.example.mapper.SavingMapper;
import com.example.response.AddSavingResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.sql.Date;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AddSavingTest {

	final String URL = "/saving/addSaving";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";
	final String FAIL_USER_ID = "fail_user_id";
	final String NULL_USER_ID = null;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private SavingMapper savingMapper;

	@Test
	@Transactional(readOnly = false)
	void addSavingTest() throws Exception {

		Date savingDate = Date.valueOf("2023-06-01");
		String savingName = "貯金追加テスト";
		BigInteger savingAmount = BigInteger.valueOf(1000);
		Long savingTargetId = null;

		AddSavingForm req = new AddSavingForm();
		req.setUserId(USER_ID);
		req.setSavingDate(savingDate);
		req.setSavingName(savingName);
		req.setSavingAmount(savingAmount);
		req.setSavingTargetId(savingTargetId);

		String result = mvc
				.perform(post(URL).content(mapper.writeValueAsString(req)).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString(Charset.defaultCharset());

		AddSavingResponse response = mapper.readValue(result, AddSavingResponse.class);

		/* 検証 */
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(SuccessMessage.SAVING_INSERT_SUCCESSED, response.getMessage());

		GetMonthlySavingListForm form = new GetMonthlySavingListForm();
		form.setUserNo(2L);
		form.setMonth(Date.valueOf("2023-06-01"));
		Saving saving =
				savingMapper.getMonthlySavingList(form).stream().filter(i -> savingName.equals(i.getSavingName())).collect(Collectors.toList()).get(0);

		assertEquals(savingDate, saving.getSavingDate());
		assertEquals(savingName, saving.getSavingName());
		assertEquals(savingAmount, saving.getSavingAmount());
		assertEquals(savingTargetId, saving.getSavingTargetId());
	}

	@Test
	@Transactional(readOnly = false)
	void addSavingUserError01Test() throws Exception {

		Date savingDate = Date.valueOf("2023-06-01");
		String savingName = "貯金追加テスト";
		BigInteger savingAmount = BigInteger.valueOf(1000);
		Long savingTargetId = null;

		AddSavingForm req = new AddSavingForm();
		req.setUserId(FAIL_USER_ID);
		req.setSavingDate(savingDate);
		req.setSavingName(savingName);
		req.setSavingAmount(savingAmount);
		req.setSavingTargetId(savingTargetId);

		String result = mvc
				.perform(post(URL).content(mapper.writeValueAsString(req)).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString(Charset.defaultCharset());

		AddSavingResponse response = mapper.readValue(result, AddSavingResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.AUTHENTICATION_ERROR, response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void addSavingUserError02Test() throws Exception {

		Date savingDate = Date.valueOf("2023-06-01");
		String savingName = "貯金追加テスト";
		BigInteger savingAmount = BigInteger.valueOf(1000);
		Long savingTargetId = null;

		AddSavingForm req = new AddSavingForm();
		req.setUserId(NULL_USER_ID);
		req.setSavingDate(savingDate);
		req.setSavingName(savingName);
		req.setSavingAmount(savingAmount);
		req.setSavingTargetId(savingTargetId);

		String result = mvc
				.perform(post(URL).content(mapper.writeValueAsString(req)).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString(Charset.defaultCharset());

		AddSavingResponse response = mapper.readValue(result, AddSavingResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.AUTHENTICATION_ERROR, response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void addSavingSavingDateErrorTest() throws Exception {

		Date savingDate = null;
		String savingName = "貯金追加テスト";
		BigInteger savingAmount = BigInteger.valueOf(1000);
		Long savingTargetId = null;

		AddSavingForm req = new AddSavingForm();
		req.setUserId(USER_ID);
		req.setSavingDate(savingDate);
		req.setSavingName(savingName);
		req.setSavingAmount(savingAmount);
		req.setSavingTargetId(savingTargetId);

		String result = mvc
				.perform(post(URL).content(mapper.writeValueAsString(req)).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString(Charset.defaultCharset());

		AddSavingResponse response = mapper.readValue(result, AddSavingResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ValidatingMessage.DATE_EMPTY_ERROR, response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void addSavingSavingNameErrorTest() throws Exception {

		Date savingDate = Date.valueOf("2023-06-01");
		String savingName = "";
		BigInteger savingAmount = BigInteger.valueOf(1000);
		Long savingTargetId = null;

		AddSavingForm req = new AddSavingForm();
		req.setUserId(USER_ID);
		req.setSavingDate(savingDate);
		req.setSavingName(savingName);
		req.setSavingAmount(savingAmount);
		req.setSavingTargetId(savingTargetId);

		String result = mvc
				.perform(post(URL).content(mapper.writeValueAsString(req)).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString(Charset.defaultCharset());

		AddSavingResponse response = mapper.readValue(result, AddSavingResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ValidatingMessage.SAVING_NAME_EMPTY_ERROR, response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void addSavingSavingAmountErrorTest() throws Exception {

		Date savingDate = Date.valueOf("2023-06-01");
		String savingName = "貯金追加テスト";
		BigInteger savingAmount = null;
		Long savingTargetId = null;

		AddSavingForm req = new AddSavingForm();
		req.setUserId(USER_ID);
		req.setSavingDate(savingDate);
		req.setSavingName(savingName);
		req.setSavingAmount(savingAmount);
		req.setSavingTargetId(savingTargetId);

		String result = mvc
				.perform(post(URL).content(mapper.writeValueAsString(req)).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString(Charset.defaultCharset());

		AddSavingResponse response = mapper.readValue(result, AddSavingResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ValidatingMessage.SAVING_AMOUNT_EMPTY_ERROR, response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void addSavingSavingTargetIdErrorTest() throws Exception {

		Date savingDate = Date.valueOf("2023-06-01");
		String savingName = "貯金追加テスト";
		BigInteger savingAmount = BigInteger.valueOf(1000);
		Long savingTargetId = 99L;

		AddSavingForm req = new AddSavingForm();
		req.setUserId(USER_ID);
		req.setSavingDate(savingDate);
		req.setSavingName(savingName);
		req.setSavingAmount(savingAmount);
		req.setSavingTargetId(savingTargetId);

		String result = mvc
				.perform(post(URL).content(mapper.writeValueAsString(req)).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString(Charset.defaultCharset());

		AddSavingResponse response = mapper.readValue(result, AddSavingResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.SAVING_TARGET_NOT_FOUND, response.getMessage());
	}
}
