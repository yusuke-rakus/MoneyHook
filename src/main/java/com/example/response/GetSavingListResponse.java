package com.example.response;

import com.example.domain.Saving;

import java.util.List;

public class GetSavingListResponse extends response {

	private List<Saving> savingList;

	public List<Saving> getSavingList() {
		return savingList;
	}

	public void setSavingList(List<Saving> savingList) {
		this.savingList = savingList;
	}
}
