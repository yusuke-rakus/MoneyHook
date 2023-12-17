package com.example.response;

import com.example.domain.SavingTarget;

import java.util.List;

public class GetSavingTargetListResponse extends response {

	private List<SavingTarget> savingTargetList;

	public List<SavingTarget> getSavingTarget() {
		return savingTargetList;
	}

	public void setSavingTarget(List<SavingTarget> savingTargetList) {
		this.savingTargetList = savingTargetList;
	}

}
