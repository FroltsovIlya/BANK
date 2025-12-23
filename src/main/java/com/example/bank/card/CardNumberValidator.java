package com.example.bank.card;

public final class CardNumberValidator {

    private static final String REGEX = "\\d{4} \\d{4} \\d{4} \\d{4}";

    private CardNumberValidator() {}

    public static boolean isValid(String number) {
        return number != null && number.matches(REGEX);
    }
}
