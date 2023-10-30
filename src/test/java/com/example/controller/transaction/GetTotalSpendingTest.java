package com.example.controller.transaction;

import com.example.common.Status;
import com.example.common.message.ErrorMessage;
import com.example.common.message.ValidatingMessage;
import com.example.form.GetTotalSpendingForm;
import com.example.response.GetTotalSpendingResponse;
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
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GetTotalSpendingTest {

	final String URL = "/transaction/getTotalSpending";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";
	final String FAIL_USER_ID = "fail_user_id";
	final String NULL_USER_ID = null;
	final String TOKEN = "sample_token";
	final HttpHeaders HEADER = new HttpHeaders();

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@Test
	@Transactional(readOnly = true)
	void getTotalSpendingSuccess01Test() throws Exception {

		Long categoryId = 1L;
		Long subCategoryId = 1L;
		Date startMonth = Date.valueOf("2022-01-01");
		Date endMonth = Date.valueOf("2023-06-01");

		GetTotalSpendingForm req = new GetTotalSpendingForm();
		req.setCategoryId(categoryId);
		req.setSubCategoryId(subCategoryId);
		req.setStartMonth(startMonth);
		req.setEndMonth(endMonth);
		req.setUserId(USER_ID);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		GetTotalSpendingResponse response = mapper.readValue(result, GetTotalSpendingResponse.class);

		/* 検証 */
		BigInteger totalSpending = BigInteger.valueOf(-92510);

		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertNull(response.getMessage());
		assertEquals(response.getTotalSpending(), totalSpending);
	}

	@Test
	@Transactional(readOnly = true)
	void getTotalSpendingSuccess02Test() throws Exception {

		Long categoryId = 1L;
		Long subCategoryId = null;
		Date startMonth = Date.valueOf("2022-01-01");
		Date endMonth = Date.valueOf("2023-06-01");

		GetTotalSpendingForm req = new GetTotalSpendingForm();
		req.setCategoryId(categoryId);
		req.setSubCategoryId(subCategoryId);
		req.setStartMonth(startMonth);
		req.setEndMonth(endMonth);
		req.setUserId(USER_ID);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		GetTotalSpendingResponse response = mapper.readValue(result, GetTotalSpendingResponse.class);

		/* 検証 */
		BigInteger totalSpending = BigInteger.valueOf(-92510);

		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertNull(response.getMessage());
		assertEquals(response.getTotalSpending(), totalSpending);
	}

	@Test
	@Transactional(readOnly = true)
	void getTotalSpendingUserError01Test() throws Exception {

		Long categoryId = 1L;
		Long subCategoryId = 1L;
		Date startMonth = Date.valueOf("2022-01-01");
		Date endMonth = Date.valueOf("2023-06-01");

		GetTotalSpendingForm req = new GetTotalSpendingForm();
		req.setCategoryId(categoryId);
		req.setSubCategoryId(subCategoryId);
		req.setStartMonth(startMonth);
		req.setEndMonth(endMonth);
		req.setUserId(FAIL_USER_ID);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		GetTotalSpendingResponse response = mapper.readValue(result, GetTotalSpendingResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.AUTHENTICATION_ERROR, response.getMessage());
	}

	@Test
	@Transactional(readOnly = true)
	void getTotalSpendingUserError02Test() throws Exception {

		Long categoryId = 1L;
		Long subCategoryId = 1L;
		Date startMonth = Date.valueOf("2022-01-01");
		Date endMonth = Date.valueOf("2023-06-01");

		GetTotalSpendingForm req = new GetTotalSpendingForm();
		req.setCategoryId(categoryId);
		req.setSubCategoryId(subCategoryId);
		req.setStartMonth(startMonth);
		req.setEndMonth(endMonth);
		req.setUserId(NULL_USER_ID);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		GetTotalSpendingResponse response = mapper.readValue(result, GetTotalSpendingResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.AUTHENTICATION_ERROR, response.getMessage());
	}

	@Test
	@Transactional(readOnly = true)
	void getTotalSpendingCategoryErrorTest() throws Exception {

		Long categoryId = null;
		Long subCategoryId = 1L;
		Date startMonth = Date.valueOf("2022-01-01");
		Date endMonth = Date.valueOf("2023-06-01");

		GetTotalSpendingForm req = new GetTotalSpendingForm();
		req.setCategoryId(categoryId);
		req.setSubCategoryId(subCategoryId);
		req.setStartMonth(startMonth);
		req.setEndMonth(endMonth);
		req.setUserId(USER_ID);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		GetTotalSpendingResponse response = mapper.readValue(result, GetTotalSpendingResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ValidatingMessage.CATEGORY_NOT_SELECT_ERROR, response.getMessage());
	}

	@Test
	@Transactional(readOnly = true)
	void getTotalSpendingCategoryStartMonthErrorTest() throws Exception {

		Long categoryId = 1L;
		Long subCategoryId = 1L;
		Date startMonth = null;
		Date endMonth = Date.valueOf("2023-06-01");

		GetTotalSpendingForm req = new GetTotalSpendingForm();
		req.setCategoryId(categoryId);
		req.setSubCategoryId(subCategoryId);
		req.setStartMonth(startMonth);
		req.setEndMonth(endMonth);
		req.setUserId(USER_ID);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		GetTotalSpendingResponse response = mapper.readValue(result, GetTotalSpendingResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ValidatingMessage.START_MONTH_NOT_INPUT_ERROR, response.getMessage());
	}

	@Test
	@Transactional(readOnly = true)
	void getTotalSpendingCategoryEndMonthErrorTest() throws Exception {

		Long categoryId = 1L;
		Long subCategoryId = 1L;
		Date startMonth = Date.valueOf("2022-01-01");
		Date endMonth = null;

		GetTotalSpendingForm req = new GetTotalSpendingForm();
		req.setCategoryId(categoryId);
		req.setSubCategoryId(subCategoryId);
		req.setStartMonth(startMonth);
		req.setEndMonth(endMonth);
		req.setUserId(USER_ID);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		GetTotalSpendingResponse response = mapper.readValue(result, GetTotalSpendingResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ValidatingMessage.END_MONTH_NOT_INPUT_ERROR, response.getMessage());
	}

	@Test
	@Transactional(readOnly = true)
	void getTotalSpendingCategoryCategoryRelationalErrorTest() throws Exception {

		Long categoryId = 1L;
		Long subCategoryId = 99L;
		Date startMonth = Date.valueOf("2022-01-01");
		Date endMonth = Date.valueOf("2023-06-01");

		GetTotalSpendingForm req = new GetTotalSpendingForm();
		req.setCategoryId(categoryId);
		req.setSubCategoryId(subCategoryId);
		req.setStartMonth(startMonth);
		req.setEndMonth(endMonth);
		req.setUserId(USER_ID);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		GetTotalSpendingResponse response = mapper.readValue(result, GetTotalSpendingResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.CATEGORY_IS_NOT_RELATIONAL, response.getMessage());
	}

	@Test
	@Transactional(readOnly = true)
	void getTotalSpendingCategoryDateError01Test() throws Exception {

		Long categoryId = 1L;
		Long subCategoryId = 1L;
		Date startMonth = Date.valueOf("2022-01-01");
		Date endMonth = Date.valueOf("2025-01-01");

		GetTotalSpendingForm req = new GetTotalSpendingForm();
		req.setCategoryId(categoryId);
		req.setSubCategoryId(subCategoryId);
		req.setStartMonth(startMonth);
		req.setEndMonth(endMonth);
		req.setUserId(USER_ID);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		GetTotalSpendingResponse response = mapper.readValue(result, GetTotalSpendingResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.DATE_RANGE_ERROR, response.getMessage());
	}

	@Test
	@Transactional(readOnly = true)
	void getTotalSpendingCategoryDateError02Test() throws Exception {

		Long categoryId = 1L;
		Long subCategoryId = 1L;
		Date startMonth = Date.valueOf("2022-01-01");
		Date endMonth = Date.valueOf("2021-12-31");

		GetTotalSpendingForm req = new GetTotalSpendingForm();
		req.setCategoryId(categoryId);
		req.setSubCategoryId(subCategoryId);
		req.setStartMonth(startMonth);
		req.setEndMonth(endMonth);
		req.setUserId(USER_ID);
		HEADER.add("UserId", USER_ID);
		HEADER.add(HttpHeaders.AUTHORIZATION, TOKEN);

		String result = mvc.perform(post(URL).headers(HEADER).content(mapper.writeValueAsString(req))
						.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andReturn()
				.getResponse().getContentAsString(Charset.defaultCharset());

		GetTotalSpendingResponse response = mapper.readValue(result, GetTotalSpendingResponse.class);

		/* 検証 */
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.DATE_REVERSED_ERROR, response.getMessage());
	}
}
