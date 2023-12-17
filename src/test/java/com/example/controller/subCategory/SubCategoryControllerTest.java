package com.example.controller.subCategory;

import com.example.common.Status;
import com.example.common.message.Message;
import com.example.form.GetSubCategoryListForm;
import com.example.response.SubCategoryResponse;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SubCategoryControllerTest {

	final String URL = "/subCategory/getSubCategoryList";
	final String USER_ID1 = "4f4da417-7693-4fa1-b153-a3511ed1a57a";
	final String USER_ID2 = "a77a6e94-6aa2-47ea-87dd-129f580fb669";
	final String TOKEN = "sample_token";
	final HttpHeaders HEADER = new HttpHeaders();

	// コントローラテスト実施のため用意
	@Autowired
	private MockMvc mvc;

	// JSONへの双方向の変換を行うため用意
	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private Message message;

	@Test
	@Transactional(readOnly = true)
	void getSubCategorySuccessByUser1Test() throws Exception {
		/* 準備 */
		Long categoryId = 2L;
		/* 送るFormの中身を用意 */
		GetSubCategoryListForm requestForm = new GetSubCategoryListForm();
		requestForm.setUserId(USER_ID1);
		requestForm.setCategoryId(categoryId);
		HEADER.add("UserId", USER_ID2);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);


		/* 実行 */
		String result = mvc.perform(post(URL).headers(HEADER)// 対象のURLに対してPOSTでリクエストを送る
						.content(mapper.writeValueAsString(requestForm)) // FormをJSONに変換してリクエストにセット
						.contentType(MediaType.APPLICATION_JSON)) // headerにセット
				.andDo(print()) // ログにリクエスト・レスポンを表示
				.andExpect(status().isOk()) // 変換ステータスを指定
				.andReturn().getResponse().getContentAsString(Charset.defaultCharset()); // 文字列に変換

		/* JSONをresponseに変換 */
		SubCategoryResponse response = mapper.readValue(result, SubCategoryResponse.class);
		/* 検証 */
		int getCount = 4;
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertNull(response.getMessage());
		assertEquals(getCount, response.getSubCategoryList().size());
	}

	@Test
	@Transactional(readOnly = true)
	void getSubCategorySuccessByUser2Test() throws Exception {
		/* 準備 */
		Long categoryId = 2L;
		GetSubCategoryListForm requestForm = new GetSubCategoryListForm();
		requestForm.setUserId(USER_ID2);
		requestForm.setCategoryId(categoryId);
		HEADER.add("UserId", USER_ID2);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		/* 実行 */
		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(requestForm))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		SubCategoryResponse response = mapper.readValue(result, SubCategoryResponse.class);
		/* 検証 */
		int getCount = 7;
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertNull(response.getMessage());
		assertEquals(getCount, response.getSubCategoryList().size());

	}

	@Test
	@Transactional(readOnly = true)
	void authErrorTest() throws Exception {
		/* 準備 */
		UUID uuid = UUID.randomUUID();
		String userId = uuid.toString();
		Long categoryId = 2L;
		GetSubCategoryListForm requestForm = new GetSubCategoryListForm();
		requestForm.setUserId(userId);
		requestForm.setCategoryId(categoryId);
		HEADER.add("UserId", USER_ID2);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		/* 実行 */
		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(requestForm))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		SubCategoryResponse response = mapper.readValue(result, SubCategoryResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.authentication-error"), response.getMessage());
		assertNull(response.getSubCategoryList());
	}

	@Test
	@Transactional(readOnly = true)
	void getSubCategoryIs0Test() throws Exception {
		/* 準備 */
		Long categoryId = 30L;
		GetSubCategoryListForm requestForm = new GetSubCategoryListForm();
		requestForm.setUserId(USER_ID2);
		requestForm.setCategoryId(categoryId);
		HEADER.add("UserId", USER_ID2);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		/* 実行 */
		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(requestForm))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		SubCategoryResponse response = mapper.readValue(result, SubCategoryResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(message.get("error-message.sub-category-get-failed"), response.getMessage());
		assertNull(response.getSubCategoryList());
	}

}
