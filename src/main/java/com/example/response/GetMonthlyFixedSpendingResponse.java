package com.example.response;

import com.example.domain.MonthlyFixedList;

import java.math.BigInteger;
import java.util.List;

public class GetMonthlyFixedSpendingResponse extends response {

    private BigInteger disposableIncome;
    private List<MonthlyFixedList> monthlyFixedList;

    public BigInteger getDisposableIncome() {
        return disposableIncome;
    }

    public void setDisposableIncome(BigInteger disposableIncome) {
        this.disposableIncome = disposableIncome;
    }

    public List<MonthlyFixedList> getMonthlyFixedList() {
        return monthlyFixedList;
    }

    public void setMonthlyFixedList(List<MonthlyFixedList> monthlyFixedList) {
        this.monthlyFixedList = monthlyFixedList;
    }

}
