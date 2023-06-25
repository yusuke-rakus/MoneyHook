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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.Status;
import com.example.common.message.ErrorMessage;
import com.example.common.message.SuccessMessage;
import com.example.domain.User;
import com.example.form.EditThemeColorForm;
import com.example.response.EditThemeColorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerChangeThemeColorTest {
	

	final String URL = "/user/editThemeColor";
	final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	@Transactional(readOnly = false)
	void changeThemaColorTest() throws Exception {
		/*準備*/
		Long themaColorId = 2L;
		
		
		EditThemeColorForm form = new EditThemeColorForm(); 
		form.setUserId(USER_ID);
		form.setThemeColorId(themaColorId);
		
		//実行
		String result = mvc.perform(post(URL)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset());
		
		//ユーザー情報取得
		String sql = "SELECT * FROM user WHERE user_id = ?";
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		User user = jdbcTemplate.queryForObject(sql, rowMapper, USER_ID);
		
		Integer newThemaColorId = Math.toIntExact(themaColorId);

		EditThemeColorResponse response = mapper.readValue(result, EditThemeColorResponse.class);
		assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
		assertEquals(SuccessMessage.USER_THEME_COLOR_CHANGED, response.getMessage());
		assertEquals(user.getThemeColorId(), newThemaColorId);
	}
	
	@Test
	@Transactional(readOnly = false)
	void changeErrorThemaColorTest() throws Exception {
		/*準備*/
		Long themaColorId = 2L;
		String noUserId = "b77a6e94-6aa2-47ea-87dd-129f580fb660";
		
		EditThemeColorForm form = new EditThemeColorForm(); 
		form.setUserId(noUserId);
		form.setThemeColorId(themaColorId);
		
		//実行
		String result = mvc.perform(post(URL)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset());
		
		//ユーザー情報取得
		String sql = "SELECT * FROM user WHERE user_id = ?";
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		User user = jdbcTemplate.queryForObject(sql, rowMapper, USER_ID);
		
		Integer newThemaColorId = Math.toIntExact(themaColorId);
		int nowThemaColorId = 1;

		EditThemeColorResponse response = mapper.readValue(result, EditThemeColorResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.AUTHENTICATION_ERROR, response.getMessage());
		assertNotEquals(user.getThemeColorId(), newThemaColorId);
		assertEquals(user.getThemeColorId(), nowThemaColorId);
	}
	
	@Test
	@Transactional(readOnly = false)
	void updateNoThemaColorIdTest() throws Exception {
		/*準備*/
		Long themaColorId = 100L;
		
		EditThemeColorForm form = new EditThemeColorForm(); 
		form.setUserId(USER_ID);
		form.setThemeColorId(themaColorId);
		
		//実行
		String result = mvc.perform(post(URL)
				.content(mapper.writeValueAsString(form))
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString(Charset.defaultCharset());
		
		//ユーザー情報取得
		String sql = "SELECT * FROM user WHERE user_id = ?";
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		User user = jdbcTemplate.queryForObject(sql, rowMapper, USER_ID);
		
		Integer newThemaColorId = Math.toIntExact(themaColorId);
		int nowThemaColorId = 1;

		EditThemeColorResponse response = mapper.readValue(result, EditThemeColorResponse.class);
		assertEquals(Status.ERROR.getStatus(), response.getStatus());
		assertEquals(ErrorMessage.THEME_COLOR_NOT_FOUND, response.getMessage());
		assertNotEquals(user.getThemeColorId(), newThemaColorId);
		assertEquals(user.getThemeColorId(), nowThemaColorId);
	}
}
