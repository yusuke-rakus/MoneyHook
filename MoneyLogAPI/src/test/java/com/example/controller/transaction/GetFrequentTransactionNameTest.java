package com.example.controller.transaction;

import com.example.common.Status;
import com.example.form.FrequentTransactionNameForm;
import com.example.response.FrequentTransactionNameResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GetFrequentTransactionNameTest {

    final String URL = "/transaction/getFrequentTransactionName";
    final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @Transactional(readOnly = true)
    void getFrequentTransactionNameTest() throws Exception {

        FrequentTransactionNameForm requestForm = new FrequentTransactionNameForm();
        requestForm.setUserId(USER_ID);

        String result = mvc
                .perform(post(URL).content(mapper.writeValueAsString(requestForm))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString(Charset.defaultCharset());

        FrequentTransactionNameResponse response = mapper.readValue(result, FrequentTransactionNameResponse.class);

        /* 検証 */
        int frequentMaxCount = 5;

        assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
        assertNull(response.getMessage());
        assertThat(frequentMaxCount).isLessThanOrEqualTo(response.getTransactionList().size());
    }
}
