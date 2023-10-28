package com.example.controller.savingTarget;

import com.example.common.Status;
import com.example.common.message.ErrorMessage;
import com.example.common.message.SuccessMessage;
import com.example.form.DeleteSavingTargetForm;
import com.example.response.DeleteSavingTargetResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DeleteSavingTargetFromTableTest {

	final String URL = "/savingTarget/deleteSavingTargetFromTable";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";
	final String FAIL_USER_ID = "fail_user_id";
	final String NULL_USER_ID = null;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;
	
	@Test
	@Transactional(readOnly = false)
	void deleteSavingTargetFromTableTest() throws Exception {

		Long savingTargetId = 3L;

		DeleteSavingTargetForm req = new DeleteSavingTargetForm();
		req.setUserId(USER_ID);
		req.setSavingTargetId(savingTargetId);

		String result = mvc.perform(post(URL).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		DeleteSavingTargetResponse response = mapper.readValue(result, DeleteSavingTargetResponse.class);

		/* 検証 */
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(SuccessMessage.SAVING_TARGET_DELETE_SUCCESSED, response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void deleteSavingTargetFromTableUserError01Test() throws Exception {

		Long savingTargetId = 1L;

		DeleteSavingTargetForm req = new DeleteSavingTargetForm();
		req.setUserId(FAIL_USER_ID);
		req.setSavingTargetId(savingTargetId);

		String result = mvc.perform(post(URL).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		DeleteSavingTargetResponse response = mapper.readValue(result, DeleteSavingTargetResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.AUTHENTICATION_ERROR, response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void deleteSavingTargetFromTableUserError02Test() throws Exception {

		Long savingTargetId = 1L;

		DeleteSavingTargetForm req = new DeleteSavingTargetForm();
		req.setUserId(NULL_USER_ID);
		req.setSavingTargetId(savingTargetId);

		String result = mvc.perform(post(URL).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		DeleteSavingTargetResponse response = mapper.readValue(result, DeleteSavingTargetResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.AUTHENTICATION_ERROR, response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void deleteSavingTargetFromTableTargetIdError01Test() throws Exception {

		Long savingTargetId = 99L;

		DeleteSavingTargetForm req = new DeleteSavingTargetForm();
		req.setUserId(USER_ID);
		req.setSavingTargetId(savingTargetId);

		String result = mvc.perform(post(URL).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		DeleteSavingTargetResponse response = mapper.readValue(result, DeleteSavingTargetResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.SAVING_TARGET_NOT_FOUND, response.getMessage());
	}

	@Test
	@Transactional(readOnly = false)
	void deleteSavingTargetFromTableTargetIdError02Test() throws Exception {

		Long savingTargetId = 1L;

		DeleteSavingTargetForm req = new DeleteSavingTargetForm();
		req.setUserId(USER_ID);
		req.setSavingTargetId(savingTargetId);

		String result = mvc.perform(post(URL).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		DeleteSavingTargetResponse response = mapper.readValue(result, DeleteSavingTargetResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.SAVING_TARGET_HAS_TOTAL_SAVED, response.getMessage());
	}
}