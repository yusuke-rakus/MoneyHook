package com.example.controller.transaction;

import com.example.common.Status;
import com.example.form.GetTimelineDataForm;
import com.example.response.GetTimelineDataResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GetTimelineDataTest {

    final String URL = "/transaction/getTimelineData";
    final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @Transactional(readOnly = true)
    void getTimelineDataTest() throws Exception {
        Date month = Date.valueOf("2023-06-01");

        GetTimelineDataForm requestForm = new GetTimelineDataForm();
        requestForm.setUserId(USER_ID);
        requestForm.setMonth(month);

        String result = mvc
                .perform(post(URL).content(mapper.writeValueAsString(requestForm))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString(Charset.defaultCharset());

        GetTimelineDataResponse response = mapper.readValue(result, GetTimelineDataResponse.class);

        /* 検証 */
        int timelineDataCount = 43;
        Date date = Date.valueOf("2023-06-30");

        assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
        assertEquals(null, response.getMessage());
        assertEquals(timelineDataCount, response.getTransactionList().size());
        assertEquals(date.toString(), response.getTransactionList().get(0).getTransactionDate().toString());
    }
}
