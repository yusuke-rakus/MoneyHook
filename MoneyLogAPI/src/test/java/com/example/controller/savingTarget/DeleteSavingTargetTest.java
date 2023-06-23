package com.example.controller.savingTarget;

import com.example.common.Status;
import com.example.common.message.SuccessMessage;
import com.example.domain.SavingTarget;
import com.example.form.DeleteSavingTargetForm;
import com.example.form.GetSavingTargetListForm;
import com.example.mapper.SavingTargetMapper;
import com.example.response.DeleteSavingTargetResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DeleteSavingTargetTest {

    final String URL = "/savingTarget/deleteSavingTarget";
    final String USER_ID = "a77a6e94-6aa2-47ea-87dd-129f580fb669";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SavingTargetMapper savingTargetMapper;

    @Test
    @Transactional(readOnly = false)
    void deleteSavingTargetTest() throws Exception {

        Long savingTargetId = 1L;

        DeleteSavingTargetForm req = new DeleteSavingTargetForm();
        req.setUserId(USER_ID);
        req.setSavingTargetId(savingTargetId);

        String result = mvc
                .perform(post(URL).content(mapper.writeValueAsString(req)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString(Charset.defaultCharset());

        DeleteSavingTargetResponse response = mapper.readValue(result, DeleteSavingTargetResponse.class);

        /* 検証 */
        assertEquals(Status.SUCCESS.getStatus(), response.getStatus());
        assertEquals(SuccessMessage.SAVING_TARGET_DELETE_SUCCESSED, response.getMessage());

        GetSavingTargetListForm form = new GetSavingTargetListForm();
        form.setUserNo(2L);
        SavingTarget savingTarget =
                savingTargetMapper.getSavingTargetList(form).stream().filter(i -> savingTargetId.equals(i.getSavingTargetId())).findFirst().orElse(null);

        assertEquals(null, savingTarget);
    }
}
