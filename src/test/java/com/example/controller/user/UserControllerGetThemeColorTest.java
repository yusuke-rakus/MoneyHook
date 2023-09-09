package com.example.controller.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.Status;
import com.example.common.message.ErrorMessage;
import com.example.form.GetThemeColorForm;
import com.example.mapper.UserMapper;
import com.example.response.GetThemeColorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerGetThemeColorTest {

	
	final String URL = "/user/getThemeColor";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@SpyBean
	private UserMapper userMapper;
	
	@Test
	@Transactional(readOnly = false)
	void getThemaColorListTest() throws Exception {
		/*準備*/
		GetThemeColorForm form = new GetThemeColorForm(); 
		form.setUserId(USER_ID);
		
		//実行
		String result = mvc.perform(post(URL)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset());

		//検証
		int themaColorCount = 14;
		GetThemeColorResponse response = mapper.readValue(result, GetThemeColorResponse.class);
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(themaColorCount ,response.getThemeColorList().size());
	}
	
	@Test
	@Transactional(readOnly = false)
	void authErrorTest() throws Exception {
		/*準備*/
		String notUserId = "aaaaaaaa-6aa2-47ea-87dd-129f580fb669";
		GetThemeColorForm form = new GetThemeColorForm(); 
		form.setUserId(notUserId);
		
		//実行
		String result = mvc.perform(post(URL)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset());

		//検証
		GetThemeColorResponse response = mapper.readValue(result, GetThemeColorResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.AUTHENTICATION_ERROR ,response.getMessage());
	}
	
	/* TODO 
	 * テーマカラーが1件も取得出来ない場合はSuccessでいい？*/
	@Test
	@Transactional(readOnly = false)
	void themaColorNonTest() throws Exception {
		/*準備*/
		GetThemeColorForm form = new GetThemeColorForm(); 
		form.setUserId(USER_ID);
		
//		List<User> userList = new ArrayList<>();
		doReturn(null).when(userMapper).getThemeColor(any());
		
		//実行
		String result = mvc.perform(post(URL)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset());

		//検証
		GetThemeColorResponse response = mapper.readValue(result, GetThemeColorResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.USER_INFO_GET_FAILED ,response.getMessage());
	}
	
	
}
