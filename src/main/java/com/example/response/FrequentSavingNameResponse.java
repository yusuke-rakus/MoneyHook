package com.example.response;

import java.util.List;

import com.example.domain.Saving;

public class FrequentSavingNameResponse extends response {

	private List<Saving> savingList;

	public List<Saving> getSavingList() {
		return savingList;
	}

	public void setSavingList(List<Saving> savingList) {
		this.savingList = savingList;
	}

}
