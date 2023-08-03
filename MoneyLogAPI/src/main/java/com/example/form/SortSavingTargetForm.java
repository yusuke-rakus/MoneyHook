package com.example.form;

import com.example.domain.SavingTarget;

import java.util.List;

public class SortSavingTargetForm extends form {
    public List<SavingTarget> getSavingTargetList() {
        return savingTargetList;
    }

    public void setSavingTargetList(List<SavingTarget> savingTargetList) {
        this.savingTargetList = savingTargetList;
    }

    private List<SavingTarget> savingTargetList;


}
