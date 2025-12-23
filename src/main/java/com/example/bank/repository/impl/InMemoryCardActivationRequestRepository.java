package com.example.bank.repository.impl;

import com.example.bank.request.CardActivationRequest;
import com.example.bank.request.CardActivationRequestRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryCardActivationRequestRepository
        implements CardActivationRequestRepository {

    private final List<CardActivationRequest> requests = new ArrayList<>();

    @Override
    public void save(CardActivationRequest request) {
        requests.add(request);
    }

    @Override
    public boolean existsByCardNumber(String cardNumber) {
        return requests.stream()
                .anyMatch(r -> r.getCardNumber().equals(cardNumber));
    }

    @Override
    public List<CardActivationRequest> findAll() {
        return List.copyOf(requests);
    }

    @Override
    public void deleteByCardNumber(String cardNumber) {
        requests.removeIf(r -> r.getCardNumber().equals(cardNumber));
    }
}
