package com.example.bank.web.dto;

public class TransferRequest {

    private String fromCard;
    private String toCard;
    private int amount;

    public String getFromCard() {
        return fromCard;
    }

    public String getToCard() {
        return toCard;
    }

    public int getAmount() {
        return amount;
    }
}
