package com.example.expensetracker;

public class DetailList {
    String personName, detailMessage;
    int amount;

    public DetailList(String personName, int amount, String detailMessage) {
        this.personName = personName;
        this.amount = amount;
        this.detailMessage = detailMessage;
    }


    public String getPersonName() {
        return personName;
    }


    public String getDetailMessage() {
        return detailMessage;
    }


    public int getAmount() {
        return amount;
    }


}
