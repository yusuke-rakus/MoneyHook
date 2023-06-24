package com.example.response;

import com.example.domain.CategoryList;

import java.math.BigInteger;
import java.util.List;

public class GetHomeResponse extends response {

    private BigInteger balance;
    private List<CategoryList> categoryList;

    public BigInteger getBalance() {
        return balance;
    }

    public void setBalance(BigInteger balance) {
        this.balance = balance;
    }

    public List<CategoryList> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryList> categoryList) {
        this.categoryList = categoryList;
    }

}
