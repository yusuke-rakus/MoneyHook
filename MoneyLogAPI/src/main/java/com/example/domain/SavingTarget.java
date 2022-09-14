package com.example.domain;

public class SavingTarget {

	private Long savingTargetId;
	private Long userNo;
	private String savingTargetName;
	private Integer targetAmount;
	private boolean deleteFlg;
	private Integer totalSavedAmount;
	private Integer savingCount;

	public Long getSavingTargetId() {
		return savingTargetId;
	}

	public void setSavingTargetId(Long savingTargetId) {
		this.savingTargetId = savingTargetId;
	}

	public Long getUserNo() {
		return userNo;
	}

	public void setUserNo(Long userNo) {
		this.userNo = userNo;
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

	public boolean isDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(boolean deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

	public Integer getTotalSavedAmount() {
		return totalSavedAmount;
	}

	public void setTotalSavedAmount(Integer totalSavedAmount) {
		this.totalSavedAmount = totalSavedAmount;
	}

	public Integer getSavingCount() {
		return savingCount;
	}

	public void setSavingCount(Integer savingCount) {
		this.savingCount = savingCount;
	}

	@Override
	public String toString() {
		return "SavingTarget [savingTargetId=" + savingTargetId + ", userNo=" + userNo + ", savingTargetName="
				+ savingTargetName + ", targetAmount=" + targetAmount + ", deleteFlg=" + deleteFlg
				+ ", totalSavedAmount=" + totalSavedAmount + "]";
	}

}
