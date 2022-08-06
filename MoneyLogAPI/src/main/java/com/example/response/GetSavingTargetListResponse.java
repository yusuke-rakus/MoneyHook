package com.example.response;

import java.util.List;

import com.example.domain.SavingTarget;

public class GetSavingTargetListResponse extends response {

	private List<SavingTarget> savingTargetList;

	public List<SavingTarget> getSavingTarget() {
		return savingTargetList;
	}

	public void setSavingTarget(List<SavingTarget> savingTargetList) {
		this.savingTargetList = savingTargetList;
	}

}
