package com.example.controller.savingTarget;

import com.example.common.Status;
import com.example.common.message.Message;
import com.example.domain.SavingTarget;
import com.example.form.EditSavingTargetForm;
import com.example.form.GetSavingTargetListForm;
import com.example.mapper.SavingTargetMapper;
import com.example.response.EditSavingTargetResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EditSavingTargetTest {

	final String URL = "/savingTarget/editSavingTarget";
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
	private SavingTargetMapper savingTargetMapper;

	@Autowired
	private Message message;

	EditSavingTargetTest() {
	}

	@Test
	@Transactional(readOnly = false)
	void editSavingTargetTest() throws Exception {

		Long savingTargetId = 1L;
		String savingTargetName = "貯金目標編集テスト";
		BigInteger targetAmount = BigInteger.valueOf(1000);

		EditSavingTargetForm req = new EditSavingTargetForm();
		req.setUserId(USER_ID);
		req.setSavingTargetId(savingTargetId);
		req.setSavingTargetName(savingTargetName);
		req.setTargetAmount(targetAmount);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		EditSavingTargetResponse response = mapper.readValue(result, EditSavingTargetResponse.class);

		/* 検証 */
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(message.get("success-message.saving-target-edit-successed"), response.getMessage());

		GetSavingTargetListForm form = new GetSavingTargetListForm();
		form.setUserNo(2L);
		SavingTarget savingTarget = savingTargetMapper.getSavingTargetList(form.getUserNo()).stream()
				.filter(i -> savingTargetId.equals(i.getSavingTargetId())).collect(Collectors.toList()).get(0);

		assertEquals(savingTargetName, savingTarget.getSavingTargetName());
		assertEquals(targetAmount, savingTarget.getTargetAmount());
	}

	@Test
	@Transactional(readOnly = false)
	void editSavingTargetUserError01Test() throws Exception {

		Long savingTargetId = 1L;
		String savingTargetName = "貯金目標編集テスト";
		BigInteger targetAmount = BigInteger.valueOf(1000);

		EditSavingTargetForm req = new EditSavingTargetForm();
		req.setUserId(FAIL_USER_ID);
		req.setSavingTargetId(savingTargetId);
		req.setSavingTargetName(savingTargetName);
		req.setTargetAmount(targetAmount);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		EditSavingTargetResponse response = mapper.readValue(result, EditSavingTargetResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.authentication-error"), response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void editSavingTargetUserError02Test() throws Exception {

		Long savingTargetId = 1L;
		String savingTargetName = "貯金目標編集テスト";
		BigInteger targetAmount = BigInteger.valueOf(1000);

		EditSavingTargetForm req = new EditSavingTargetForm();
		req.setUserId(NULL_USER_ID);
		req.setSavingTargetId(savingTargetId);
		req.setSavingTargetName(savingTargetName);
		req.setTargetAmount(targetAmount);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		EditSavingTargetResponse response = mapper.readValue(result, EditSavingTargetResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.authentication-error"), response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void editSavingTargetTargetIdError01Test() throws Exception {

		Long savingTargetId = null;
		String savingTargetName = "貯金目標編集テスト";
		BigInteger targetAmount = BigInteger.valueOf(1000);

		EditSavingTargetForm req = new EditSavingTargetForm();
		req.setUserId(USER_ID);
		req.setSavingTargetId(savingTargetId);
		req.setSavingTargetName(savingTargetName);
		req.setTargetAmount(targetAmount);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		EditSavingTargetResponse response = mapper.readValue(result, EditSavingTargetResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("validating-message.id-empty-error"), response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void editSavingTargetTargetNameError01Test() throws Exception {

		Long savingTargetId = 1L;
		String savingTargetName = null;
		BigInteger targetAmount = BigInteger.valueOf(1000);

		EditSavingTargetForm req = new EditSavingTargetForm();
		req.setUserId(USER_ID);
		req.setSavingTargetId(savingTargetId);
		req.setSavingTargetName(savingTargetName);
		req.setTargetAmount(targetAmount);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		EditSavingTargetResponse response = mapper.readValue(result, EditSavingTargetResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("validating-message.saving-target-name-empty-error"), response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void editSavingTargetTargetAmountError01Test() throws Exception {

		Long savingTargetId = 1L;
		String savingTargetName = "貯金目標編集テスト";
		BigInteger targetAmount = null;

		EditSavingTargetForm req = new EditSavingTargetForm();
		req.setUserId(USER_ID);
		req.setSavingTargetId(savingTargetId);
		req.setSavingTargetName(savingTargetName);
		req.setTargetAmount(targetAmount);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		EditSavingTargetResponse response = mapper.readValue(result, EditSavingTargetResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("validating-message.saving-target-amount-empty-error"), response.getMessage());
	}
}
