package com.example.response;

import com.example.domain.SavingTarget;

public class AddSavingTargetResponse extends response {

	private SavingTarget savingTarget;

	public SavingTarget getSavingTarget() {
		return savingTarget;
	}

	public void setSavingTarget(SavingTarget savingTarget) {
		this.savingTarget = savingTarget;
	}

}
