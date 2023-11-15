package com.example.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class AllotSavingForm extends form {

	@NotEmpty(message = "{validating-message.ID_EMPTY_ERROR}") List<Long> savingIdList;

	@NotNull(message = "{validating-message.SAVING_TARGET_ID_EMPTY_ERROR}") Long savingTargetId;

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

}
