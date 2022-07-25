package com.example.response;

import java.util.List;

import com.example.domain.CategoryList;

public class GetMonthlyVariableDataResponse extends response {

	private Integer totalVariable;

	private List<CategoryList> monthlyVariableList;

	public Integer getTotalVariable() {
		return totalVariable;
	}

	public void setTotalVariable(Integer totalVariable) {
		this.totalVariable = totalVariable;
	}

	public List<CategoryList> getMonthlyVariableList() {
		return monthlyVariableList;
	}

	public void setMonthlyVariableList(List<CategoryList> monthlyVariableList) {
		this.monthlyVariableList = monthlyVariableList;
	}

}
