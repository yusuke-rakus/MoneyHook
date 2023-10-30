package com.example.controller.category;

import com.example.common.Status;
import com.example.common.message.ErrorMessage;
import com.example.domain.Category;
import com.example.response.CategoryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

	final String URL = "/category/getCategoryList";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";
	final String TOKEN = "sample_token";
	final HttpHeaders HEADER = new HttpHeaders();

	// コントローラテスト実施のため用意
	@Autowired
	private MockMvc mvc;

	// JSONへの双方向の変換を行うため用意
	@Autowired
	private ObjectMapper mapper;

	@Test
	@Transactional(readOnly = true)
	void getCategoryListTest() throws Exception {
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		CategoryResponse response = mapper.readValue(result, CategoryResponse.class);
		int categoryCount = 28;
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(categoryCount, response.getCategoryList().size());
		// category_id昇順で取得してきているかを確認
		int categoryId = 1;
		for (Category category : response.getCategoryList()) {
			assertEquals(categoryId, category.getCategoryId());
			assertNotNull(category.getCategoryName());
			categoryId++;
		}

	}

	@Test
	@Sql("classpath:sql/category/category.sql")
	@Transactional(readOnly = false)
	void getCategoryListis0Test() throws Exception {
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		CategoryResponse response = mapper.readValue(result, CategoryResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.CATEGORY_GET_FAILED, response.getMessage());
	}
}