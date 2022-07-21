package com.example.form;

public class AddSavingTargetForm extends form {

	private Long savingTargetId;
	private String savingTargetName;
	private Integer targetAmount;

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

	public Integer getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(Integer targetAmount) {
		this.targetAmount = targetAmount;
	}

	@Override
	public String toString() {
		return "AddSavingTargetForm [savingTargetId=" + savingTargetId + ", savingTargetName=" + savingTargetName
				+ ", targetAmount=" + targetAmount + "]";
	}

}
