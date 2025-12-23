package com.example.bank.card;

import java.time.LocalDate;

public class Card {

    private final String number;
    private final String ownerName;
    private final LocalDate expireDate;
    private int balance;
    private CardStatus status;

    // существующие карты
    public Card(String ownerName, String number, int balance,
                LocalDate expireDate, CardStatus status) {
        this.ownerName = ownerName;
        this.number = number;
        this.balance = balance;
        this.expireDate = expireDate;
        this.status = status;
    }

    // новые карты через activation request
    public Card(String ownerName, String number) {
        if (!CardNumberValidator.isValid(number)) {
            throw new IllegalArgumentException("Invalid card number");
        }
        this.ownerName = ownerName;
        this.number = number;
        this.balance = 0;
        this.expireDate = LocalDate.now().plusYears(5);
        this.status = CardStatus.INACTIVE;
    }

    public void activate() {
        if (status == CardStatus.INACTIVE) {
            status = CardStatus.ACTIVE;
        }
    }

    public boolean isActive() {
        return status == CardStatus.ACTIVE &&
                !expireDate.isBefore(LocalDate.now());
    }

    public void withdraw(int amount) {
        if (!isActive()) throw new RuntimeException("Card not active");
        if (amount > balance) throw new RuntimeException("Not enough money");
        balance -= amount;
    }

    public void deposit(int amount) {
        if (!isActive()) throw new RuntimeException("Card not active");
        balance += amount;
    }

    public void requestBlock() {
        if (status == CardStatus.ACTIVE) {
            status = CardStatus.BLOCK_REQUESTED;
        }
    }

    public void block() {
        status = CardStatus.BLOCKED;
    }

    public String getNumber() { return number; }
    public String getOwnerName() { return ownerName; }
    public int getBalance() { return balance; }
    public CardStatus getStatus() { return status; }
}
