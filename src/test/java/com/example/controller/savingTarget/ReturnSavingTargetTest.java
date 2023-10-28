package com.example.controller.savingTarget;

import com.example.common.Status;
import com.example.common.message.ErrorMessage;
import com.example.common.message.SuccessMessage;
import com.example.common.message.ValidatingMessage;
import com.example.domain.SavingTarget;
import com.example.form.GetSavingTargetListForm;
import com.example.form.ReturnSavingTargetForm;
import com.example.mapper.SavingTargetMapper;
import com.example.response.ReturnSavingTargetResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReturnSavingTargetTest {

	final String URL = "/savingTarget/returnSavingTarget";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";
	final String FAIL_USER_ID = "fail_user_id";
	final String NULL_USER_ID = null;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private SavingTargetMapper savingTargetMapper;

	@Test
	@Transactional(readOnly = false)
	void returnSavingTargetTest() throws Exception {

		Long savingTargetId = 1L;

		ReturnSavingTargetForm req = new ReturnSavingTargetForm();
		req.setUserId(USER_ID);
		req.setSavingTargetId(savingTargetId);

		String result = mvc.perform(post(URL).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		ReturnSavingTargetResponse response = mapper.readValue(result, ReturnSavingTargetResponse.class);

		/* 検証 */
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(SuccessMessage.SAVING_TARGET_RETURN_SUCCESSED, response.getMessage());

		GetSavingTargetListForm form = new GetSavingTargetListForm();
		form.setUserNo(2L);
		SavingTarget savingTarget = savingTargetMapper.getSavingTargetList(form.getUserNo()).stream()
				.filter(i -> savingTargetId.equals(i.getSavingTargetId())).collect(Collectors.toList()).get(0);

		assertFalse(savingTarget.isDeleteFlg());
	}

	@Test
	@Transactional(readOnly = false)
	void returnSavingTargetUserError01Test() throws Exception {

		Long savingTargetId = 1L;

		ReturnSavingTargetForm req = new ReturnSavingTargetForm();
		req.setUserId(FAIL_USER_ID);
		req.setSavingTargetId(savingTargetId);

		String result = mvc.perform(post(URL).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		ReturnSavingTargetResponse response = mapper.readValue(result, ReturnSavingTargetResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.AUTHENTICATION_ERROR, response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void returnSavingTargetUserError02Test() throws Exception {

		Long savingTargetId = 1L;

		ReturnSavingTargetForm req = new ReturnSavingTargetForm();
		req.setUserId(NULL_USER_ID);
		req.setSavingTargetId(savingTargetId);

		String result = mvc.perform(post(URL).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		ReturnSavingTargetResponse response = mapper.readValue(result, ReturnSavingTargetResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.AUTHENTICATION_ERROR, response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void returnSavingTargetTargetIdError01Test() throws Exception {

		Long savingTargetId = 99L;

		ReturnSavingTargetForm req = new ReturnSavingTargetForm();
		req.setUserId(USER_ID);
		req.setSavingTargetId(savingTargetId);

		String result = mvc.perform(post(URL).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		ReturnSavingTargetResponse response = mapper.readValue(result, ReturnSavingTargetResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.SAVING_TARGET_NOT_FOUND, response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void returnSavingTargetTargetIdError02Test() throws Exception {

		Long savingTargetId = null;

		ReturnSavingTargetForm req = new ReturnSavingTargetForm();
		req.setUserId(USER_ID);
		req.setSavingTargetId(savingTargetId);

		String result = mvc.perform(post(URL).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		ReturnSavingTargetResponse response = mapper.readValue(result, ReturnSavingTargetResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ValidatingMessage.ID_EMPTY_ERROR, response.getMessage());
	}
}
