package com.example.response;

import com.example.domain.CategoryList;

import java.math.BigInteger;
import java.util.List;

public class GetTotalSpendingResponse extends response {

    private BigInteger totalSpending;

    private List<CategoryList> categoryTotalList;

    public BigInteger getTotalSpending() {
        return totalSpending;
    }

    public void setTotalSpending(BigInteger totalSpending) {
        this.totalSpending = totalSpending;
    }

    public List<CategoryList> getCategoryTotalList() {
        return categoryTotalList;
    }

    public void setCategoryTotalList(List<CategoryList> categoryTotalList) {
        this.categoryTotalList = categoryTotalList;
    }

}
