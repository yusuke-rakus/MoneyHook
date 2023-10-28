package com.example.form;

import com.example.common.message.ValidatingMessage;

import javax.validation.constraints.NotNull;

public class DeleteSavingTargetForm extends form {

    @NotNull(message = ValidatingMessage.ID_EMPTY_ERROR)
    private Long savingTargetId;

    public Long getSavingTargetId() {
        return savingTargetId;
    }

    public void setSavingTargetId(Long savingTargetId) {
        this.savingTargetId = savingTargetId;
    }
}
