package com.example.response;

import com.example.domain.SavingTarget;

import java.math.BigInteger;
import java.util.List;

public class GetSavingAmountForSavingTargetResponse extends response {

	private List<SavingTarget> savingTargetList;
	private BigInteger uncategorizedAmount;

	public List<SavingTarget> getSavingTargetList() {
		return savingTargetList;
	}

	public void setSavingTargetList(List<SavingTarget> savingTargetList) {
		this.savingTargetList = savingTargetList;
	}

	public BigInteger getUncategorizedAmount() {
		return uncategorizedAmount;
	}

	public void setUncategorizedAmount(BigInteger uncategorizedAmount) {
		this.uncategorizedAmount = uncategorizedAmount;
	}

}
