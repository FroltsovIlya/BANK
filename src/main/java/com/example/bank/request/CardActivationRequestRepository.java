package com.example.bank.request;

import java.util.List;

public interface CardActivationRequestRepository {

    void save(CardActivationRequest request);

    boolean existsByCardNumber(String cardNumber);

    List<CardActivationRequest> findAll();

    void deleteByCardNumber(String cardNumber);
}
