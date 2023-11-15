package com.example.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigInteger;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SavingTarget {

	private Long savingTargetId;
	private Long userNo;
	private String savingTargetName;
	private BigInteger targetAmount;
	private boolean deleteFlg;
	private Integer sortNo;
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

	public BigInteger getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(BigInteger targetAmount) {
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

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

}
