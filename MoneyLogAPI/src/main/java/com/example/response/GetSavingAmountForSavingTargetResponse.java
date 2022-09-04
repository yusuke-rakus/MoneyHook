package com.example.response;

import java.util.List;

import com.example.domain.SavingTarget;

public class GetSavingAmountForSavingTargetResponse extends response {

	private List<SavingTarget> savingTargetList;
	private Integer uncategorizedAmount;

	public List<SavingTarget> getSavingTargetList() {
		return savingTargetList;
	}

	public void setSavingTargetList(List<SavingTarget> savingTargetList) {
		this.savingTargetList = savingTargetList;
	}

	public Integer getUncategorizedAmount() {
		return uncategorizedAmount;
	}

	public void setUncategorizedAmount(Integer uncategorizedAmount) {
		this.uncategorizedAmount = uncategorizedAmount;
	}

	@Override
	public String toString() {
		return "GetSavingAmountForSavingTargetResponse [savingTargetList=" + savingTargetList + ", uncategorizedAmount="
				+ uncategorizedAmount + "]";
	}

}
