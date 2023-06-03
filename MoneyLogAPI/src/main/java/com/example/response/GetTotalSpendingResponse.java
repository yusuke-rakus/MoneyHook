package com.example.response;

import java.util.List;

import com.example.domain.CategoryList;

public class GetTotalSpendingResponse extends response {

	private Integer totalSpending;

	private List<CategoryList> categoryTotalList;

	public Integer getTotalSpending() {
		return totalSpending;
	}

	public void setTotalSpending(Integer totalSpending) {
		this.totalSpending = totalSpending;
	}

	public List<CategoryList> getCategoryTotalList() {
		return categoryTotalList;
	}

	public void setCategoryTotalList(List<CategoryList> categoryTotalList) {
		this.categoryTotalList = categoryTotalList;
	}

}
