package com.example.domain;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Saving {

	private Long savingId;
	private Long userNo;
	private String savingName;
	private Integer savingAmount;
	private Date savingDate;
	private Long savingTargetId;
	private String savingTargetName;

	public Long getSavingId() {
		return savingId;
	}

	public void setSavingId(Long savingId) {
		this.savingId = savingId;
	}

	public Long getUserNo() {
		return userNo;
	}

	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}

	public String getSavingName() {
		return savingName;
	}

	public void setSavingName(String savingName) {
		this.savingName = savingName;
	}

	public Integer getSavingAmount() {
		return savingAmount;
	}

	public void setSavingAmount(Integer savingAmount) {
		this.savingAmount = savingAmount;
	}

	public Date getSavingDate() {
		return savingDate;
	}

	public void setSavingDate(Date savingDate) {
		this.savingDate = savingDate;
	}

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

	@Override
	public String toString() {
		return "Saving [savingId=" + savingId + ", userNo=" + userNo + ", savingName=" + savingName + ", savingAmount="
				+ savingAmount + ", savingDate=" + savingDate + ", savingTargetId=" + savingTargetId
				+ ", savingTargetName=" + savingTargetName + "]";
	}

}
