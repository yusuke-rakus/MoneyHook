package com.example.controller;

import java.util.List;

import com.example.domain.SavingTarget;
import com.example.response.response;

public class GetSavingTargetListResponse extends response {

	private List<SavingTarget> savingTargetList;

	public List<SavingTarget> getSavingTarget() {
		return savingTargetList;
	}

	public void setSavingTarget(List<SavingTarget> savingTargetList) {
		this.savingTargetList = savingTargetList;
	}

}
