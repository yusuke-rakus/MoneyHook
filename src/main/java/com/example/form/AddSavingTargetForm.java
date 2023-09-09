package com.example.form;

import com.example.common.message.ValidatingMessage;
import com.example.common.validation.AnyOneNotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@AnyOneNotEmpty(fields = {"savingTargetId",
        "savingTargetName"}, message = ValidatingMessage.BOTH_OF_ID_AND_NAME_EMPTY_ERROR)
public class AddSavingTargetForm extends form {

    private Long savingTargetId;
    private String savingTargetName;

    @NotNull(message = ValidatingMessage.SAVING_TARGET_AMOUNT_EMPTY_ERROR)
    private BigInteger targetAmount;

    public Long getSavingTargetId() {
        return savingTargetId;
    }

    public void setSavingTargetId(Long savingTargetId) {
        this.savingTargetId = savingTargetId;
    }

    public String getSavingTargetName() {
        return savingTargetName;
    }

    public void setSavingTargetName(String savingTargetName) {
        this.savingTargetName = savingTargetName;
    }

    public BigInteger getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigInteger targetAmount) {
        this.targetAmount = targetAmount;
    }

    @Override
    public String toString() {
        return "AddSavingTargetForm [savingTargetId=" + savingTargetId + ", savingTargetName=" + savingTargetName
                + ", targetAmount=" + targetAmount + "]";
    }

}
