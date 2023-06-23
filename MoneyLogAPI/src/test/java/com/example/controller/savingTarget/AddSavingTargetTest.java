package com.example.controller.savingTarget;

import com.example.common.Status;
import com.example.common.message.SuccessMessage;
import com.example.domain.SavingTarget;
import com.example.form.AddSavingTargetForm;
import com.example.form.GetSavingTargetListForm;
import com.example.mapper.SavingTargetMapper;
import com.example.response.AddSavingTargetResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AddSavingTargetTest {

    final String URL = "/savingTarget/addSavingTarget";
    final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SavingTargetMapper savingTargetMapper;

    @Test
    @Transactional(readOnly = false)
    void addSavingTargetTest() throws Exception {

        String savingTargetName = "貯金目標追加テスト";
        Integer targetAmount = 1000;

        AddSavingTargetForm req = new AddSavingTargetForm();
        req.setUserId(USER_ID);
        req.setSavingTargetName(savingTargetName);
        req.setTargetAmount(targetAmount);

        String result = mvc
                .perform(post(URL).content(mapper.writeValueAsString(req)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString(Charset.defaultCharset());

        AddSavingTargetResponse response = mapper.readValue(result, AddSavingTargetResponse.class);

        /* 検証 */
        assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
        assertEquals(SuccessMessage.SAVING_TARGET_INSERT_SUCCESSED, response.getMessage());

        GetSavingTargetListForm form = new GetSavingTargetListForm();
        form.setUserNo(2L);
        SavingTarget savingTarget =
                savingTargetMapper.getSavingTargetList(form).stream().filter(i -> savingTargetName.equals(i.getSavingTargetName()))
                        .collect(Collectors.toList()).get(0);

        assertEquals(targetAmount, savingTarget.getTargetAmount());
    }
}
