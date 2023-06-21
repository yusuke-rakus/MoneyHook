package com.example.controller.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.Status;
import com.example.common.message.ErrorMessage;
import com.example.form.GetUserInfoForm;
import com.example.response.GetUserInfoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerInfoTest {
	
	final String URL = "/user/getUserInfo";
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	
	
	@Test
	@Transactional(readOnly = false)
	void getUserInfoTest() throws Exception {
		/*準備*/
		String setUserId = "a77a6e94-6aa2-47ea-87dd-129f580fb669";
		GetUserInfoForm form = new GetUserInfoForm();
		form.setUserId(setUserId);
		
		/* 実行 */
		String result = mvc.perform(post(URL)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset());
		
		GetUserInfoResponse response = mapper.readValue(result, GetUserInfoResponse.class);
		
		/* 検証 */
		String email = "sample@sample.com";
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(email, response.getUserInfo().getEmail());
		
	}
	
	@Test
	@Transactional(readOnly = false)
	void getUserByNotUserIdTest() throws Exception {
		/*準備*/
		String setUserId = "b77a6e94-6aa2-47ea-87dd-129f580fb669";
		GetUserInfoForm form = new GetUserInfoForm();
		form.setUserId(setUserId);
		
		/* 実行 */
		String result = mvc.perform(post(URL)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset());
		
		GetUserInfoResponse response = mapper.readValue(result, GetUserInfoResponse.class);
		
		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.USER_INFO_GET_FAILED, response.getMessage());
		assertNull(response.getUserInfo());
		
	}

}
