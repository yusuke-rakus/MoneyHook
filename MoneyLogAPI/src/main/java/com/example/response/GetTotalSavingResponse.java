package com.example.response;

import java.util.List;

import com.example.domain.MonthlySavingData;

public class GetTotalSavingResponse extends response {

	private Integer totalSavingAmount;
	private List<MonthlySavingData> savingDataList;

	public Integer getTotalSavingAmount() {
		return totalSavingAmount;
	}

	public void setTotalSavingAmount(Integer totalSavingAmount) {
		this.totalSavingAmount = totalSavingAmount;
	}

	public List<MonthlySavingData> getSavingDataList() {
		return savingDataList;
	}

	public void setSavingDataList(List<MonthlySavingData> savingDataList) {
		this.savingDataList = savingDataList;
	}

	@Override
	public String toString() {
		return "GetTotalSavingResponse [totalSavingAmount=" + totalSavingAmount + ", savingDataList=" + savingDataList
				+ "]";
	}

}
