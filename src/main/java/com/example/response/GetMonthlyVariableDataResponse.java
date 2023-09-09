package com.example.response;

import com.example.domain.CategoryList;

import java.math.BigInteger;
import java.util.List;

public class GetMonthlyVariableDataResponse extends response {

    private BigInteger totalVariable;

    private List<CategoryList> monthlyVariableList;

    public BigInteger getTotalVariable() {
        return totalVariable;
    }

    public void setTotalVariable(BigInteger totalVariable) {
        this.totalVariable = totalVariable;
    }

    public List<CategoryList> getMonthlyVariableList() {
        return monthlyVariableList;
    }

    public void setMonthlyVariableList(List<CategoryList> monthlyVariableList) {
        this.monthlyVariableList = monthlyVariableList;
    }

}
