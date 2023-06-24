package com.example.controller.transaction;

import com.example.common.Status;
import com.example.form.GetMonthlyFixedIncomeForm;
import com.example.response.GetMonthlyFixedIncomeResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
class GetMonthlyFixedIncomeTest {

    final String URL = "/transaction/getMonthlyFixedIncome";
    final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @Transactional(readOnly = true)
    void getMonthlyFixedIncomeTest() throws Exception {

        Date month = Date.valueOf("2023-06-01");

        GetMonthlyFixedIncomeForm req = new GetMonthlyFixedIncomeForm();
        req.setMonth(month);
        req.setUserId(USER_ID);

        String result = mvc
                .perform(post(URL).content(mapper.writeValueAsString(req)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString(Charset.defaultCharset());

        GetMonthlyFixedIncomeResponse response = mapper.readValue(result, GetMonthlyFixedIncomeResponse.class);

        /* 検証 */
        BigInteger disposableIncome = BigInteger.valueOf(185305);
        Integer fixedListCount = 1;

        assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
        assertNull(response.getMessage());
        assertEquals(response.getDisposableIncome(), disposableIncome);
        assertEquals(response.getMonthlyFixedList().size(), fixedListCount);
    }
}
