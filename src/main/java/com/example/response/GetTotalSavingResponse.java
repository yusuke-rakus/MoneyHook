package com.example.response;

import com.example.domain.MonthlySavingData;

import java.math.BigInteger;
import java.util.List;

public class GetTotalSavingResponse extends response {

	private BigInteger totalSavingAmount;
	private List<MonthlySavingData> savingDataList;

	public BigInteger getTotalSavingAmount() {
		return totalSavingAmount;
	}

	public void setTotalSavingAmount(BigInteger totalSavingAmount) {
		this.totalSavingAmount = totalSavingAmount;
	}

	public List<MonthlySavingData> getSavingDataList() {
		return savingDataList;
	}

	public void setSavingDataList(List<MonthlySavingData> savingDataList) {
		this.savingDataList = savingDataList;
	}

}
