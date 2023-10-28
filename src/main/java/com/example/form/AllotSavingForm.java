package com.example.form;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.common.message.ValidatingMessage;

public class AllotSavingForm extends form {

	@NotEmpty(message = ValidatingMessage.ID_EMPTY_ERROR)
	List<Long> savingIdList;

	@NotNull(message = ValidatingMessage.SAVING_TARGET_ID_EMPTY_ERROR)
	Long savingTargetId;

	public List<Long> getSavingIdList() {
		return savingIdList;
	}

	public void setSavingIdList(List<Long> savingIdList) {
		this.savingIdList = savingIdList;
	}

	public Long getSavingTargetId() {
		return savingTargetId;
	}

	public void setSavingTargetId(Long savingTargetId) {
		this.savingTargetId = savingTargetId;
	}

	@Override
	public String toString() {
		return "AllotSavingForm [savingIdList=" + savingIdList + ", savingTargetId=" + savingTargetId + "]";
	}

}
