package com.example.controller.transaction;

import com.example.common.Status;
import com.example.domain.Transaction;
import com.example.form.GetTransactionForm;
import com.example.response.GetTransactionResponse;
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
class GetTransactionTest {

    final String URL = "/transaction/getTransaction";
    final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @Transactional(readOnly = true)
    void getTransactionTest() throws Exception {
        Long transactionId = 1L;

        GetTransactionForm requestForm = new GetTransactionForm();
        requestForm.setUserId(USER_ID);
        requestForm.setTransactionId(transactionId);

        String result = mvc
                .perform(post(URL).content(mapper.writeValueAsString(requestForm))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString(Charset.defaultCharset());

        GetTransactionResponse response = mapper.readValue(result, GetTransactionResponse.class);

        /* 検証 */
        Transaction transaction = new Transaction();
        transaction.setTransactionName("サーキュレーター");
        transaction.setTransactionAmount(BigInteger.valueOf(-646));
        Date transactionDate = Date.valueOf("2023-06-30");
        transaction.setTransactionDate(transactionDate);
        transaction.setCategoryId(5L);
        transaction.setSubCategoryId(13L);
        transaction.setFixedFlg(false);
        transaction.setCategoryName("ショッピング");
        transaction.setSubCategoryName("電化製品");

        assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
        assertNull(response.getMessage());
        assertEquals(transaction.getTransactionName(), response.getTransaction().getTransactionName());
        assertEquals(transaction.getTransactionAmount(), response.getTransaction().getTransactionAmount());
        assertEquals(transaction.getTransactionDate().toString(),
                response.getTransaction().getTransactionDate().toString());
        assertEquals(transaction.getCategoryId(), response.getTransaction().getCategoryId());
        assertEquals(transaction.getSubCategoryId(), response.getTransaction().getSubCategoryId());
        assertEquals(transaction.isFixedFlg(), response.getTransaction().isFixedFlg());
        assertEquals(transaction.getCategoryName(), response.getTransaction().getCategoryName());
        assertEquals(transaction.getSubCategoryName(), response.getTransaction().getSubCategoryName());
    }
}
