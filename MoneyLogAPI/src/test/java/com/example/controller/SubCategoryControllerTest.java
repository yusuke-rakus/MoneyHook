package com.example.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.Status;
import com.example.common.message.ErrorMessage;
import com.example.form.GetSubCategoryListForm;
import com.example.response.SubCategoryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class SubCategoryControllerTest {
	
	final String URL = "/subCategory/getSubCategoryList";
	final String USER_ID1 = "4f4da417-7693-4fa1-b153-a3511ed1a57a";
	final String USER_ID2 = "a77a6e94-6aa2-47ea-87dd-129f580fb669";
	
	//コントローラテスト実施のため用意
	@Autowired
	private MockMvc mvc;
	
	//JSONへの双方向の変換を行うため用意
	@Autowired
	private ObjectMapper mapper;
	
	@Test
	@Transactional(readOnly = true)
	void getSubCategorySuccessByUser1Test() throws Exception {
		/* 準備 */
		Long categoryId = 2L;
		/* 送るFormの中身を用意 */
		GetSubCategoryListForm requsetForm = new GetSubCategoryListForm();
		requsetForm.setUserId(USER_ID1);
		requsetForm.setCategoryId(categoryId);
		
		/* 実行 */
		String result = mvc.perform(post(URL) //対象のURLに対してPOSTでリクエストを送る
				.content(mapper.writeValueAsString(requsetForm)) //FormをJSONに変換してリクエストにセット
				.contentType(MediaType.APPLICATION_JSON)) //headerにセット
				.andDo(print()) //ログにリクエスト・レスポンを表示
				.andExpect(status().isOk()) //変換ステータスを指定
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset()); //文字列に変換
		
		/* JSONをresponseに変換 */
		SubCategoryResponse response = mapper.readValue(result, SubCategoryResponse.class);
		/* 検証 */
		int getCount = 5;
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(null, response.getMessage());
		assertEquals(getCount, response.getSubCategoryList().size());
	}
	

	@Test
	@Transactional(readOnly = true)
	void getSubCategorySuccessByUser2Test() throws Exception {
		/* 準備 */
		Long categoryId = 2L;
		GetSubCategoryListForm requsetForm = new GetSubCategoryListForm();
		requsetForm.setUserId(USER_ID2);
		requsetForm.setCategoryId(categoryId);
		
		/* 実行 */
		String result = mvc.perform(post(URL) 
				.content(mapper.writeValueAsString(requsetForm))
				.contentType(MediaType.APPLICATION_JSON)) 
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset()); 
		
		SubCategoryResponse response = mapper.readValue(result, SubCategoryResponse.class);
		/* 検証 */
		int getCount = 8;
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(null, response.getMessage());
		assertEquals(getCount, response.getSubCategoryList().size());
		
	}
	
	@Test
	@Transactional(readOnly = true)
	void authErrorTest() throws Exception {
		/* 準備 */
		UUID uuid = UUID.randomUUID();
		String userId = uuid.toString();
		Long categoryId = 2L;
		GetSubCategoryListForm requsetForm = new GetSubCategoryListForm();
		requsetForm.setUserId(userId);
		requsetForm.setCategoryId(categoryId);
		
		/* 実行 */
		String result = mvc.perform(post(URL) 
				.content(mapper.writeValueAsString(requsetForm))
				.contentType(MediaType.APPLICATION_JSON)) 
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset()); 
		
		SubCategoryResponse response = mapper.readValue(result, SubCategoryResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.AUTHENTICATION_ERROR, response.getMessage());
		assertEquals(null, response.getSubCategoryList());
	}
	
	@Test
	@Transactional(readOnly = true)
	void getSubCategoryiIs0Test() throws Exception {
		/* 準備 */
		Long categoryId = 30L;
		GetSubCategoryListForm requsetForm = new GetSubCategoryListForm();
		requsetForm.setUserId(USER_ID2);
		requsetForm.setCategoryId(categoryId);
		
		/* 実行 */
		String result = mvc.perform(post(URL) 
				.content(mapper.writeValueAsString(requsetForm))
				.contentType(MediaType.APPLICATION_JSON)) 
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset()); 
		
		SubCategoryResponse response = mapper.readValue(result, SubCategoryResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.SUB_CATEGORY_GET_FAILED, response.getMessage());
		assertEquals(null, response.getSubCategoryList());
	}

}
