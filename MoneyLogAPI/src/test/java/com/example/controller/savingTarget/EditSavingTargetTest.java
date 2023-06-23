package com.example.controller.savingTarget;

import com.example.common.Status;
import com.example.common.message.SuccessMessage;
import com.example.domain.SavingTarget;
import com.example.form.EditSavingTargetForm;
import com.example.form.GetSavingTargetListForm;
import com.example.mapper.SavingTargetMapper;
import com.example.response.EditSavingTargetResponse;
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
class EditSavingTargetTest {

    final String URL = "/savingTarget/editSavingTarget";
    final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SavingTargetMapper savingTargetMapper;

    @Test
    @Transactional(readOnly = false)
    void editSavingTargetTest() throws Exception {

        Long savingTargetId = 1L;
        String savingTargetName = "貯金目標編集テスト";
        Integer targetAmount = 1000;

        EditSavingTargetForm req = new EditSavingTargetForm();
        req.setUserId(USER_ID);
        req.setSavingTargetId(savingTargetId);
        req.setSavingTargetName(savingTargetName);
        req.setTargetAmount(targetAmount);

        String result = mvc
                .perform(post(URL).content(mapper.writeValueAsString(req)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString(Charset.defaultCharset());

        EditSavingTargetResponse response = mapper.readValue(result, EditSavingTargetResponse.class);

        /* 検証 */
        assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
        assertEquals(SuccessMessage.SAVING_TARGET_EDIT_SUCCESSED, response.getMessage());

        GetSavingTargetListForm form = new GetSavingTargetListForm();
        form.setUserNo(2L);
        SavingTarget savingTarget =
                savingTargetMapper.getSavingTargetList(form).stream().filter(i -> savingTargetId.equals(i.getSavingTargetId()))
                        .collect(Collectors.toList()).get(0);

        assertEquals(savingTargetName, savingTarget.getSavingTargetName());
        assertEquals(targetAmount, savingTarget.getTargetAmount());
    }
}
