package com.example.bank.repository.impl;

import com.example.bank.card.Card;
import com.example.bank.card.CardStatus;
import com.example.bank.repository.CardRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryCardRepository implements CardRepository {

    private final List<Card> cards = new ArrayList<>();

    public InMemoryCardRepository() {
        cards.add(new Card(
                "Ilya",
                "1000 2000 3000 4000",
                10_000,
                LocalDate.now().plusYears(2),
                CardStatus.ACTIVE
        ));
    }

    @Override
    public Card findByNumber(String number) {
        return cards.stream()
                .filter(c -> c.getNumber().equals(number))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Card> findByOwner(String ownerName) {
        return cards.stream()
                .filter(c -> c.getOwnerName().equals(ownerName))
                .toList();
    }

    @Override
    public List<Card> findBlockRequests() {
        return cards.stream()
                .filter(c -> c.getStatus() == CardStatus.BLOCK_REQUESTED)
                .toList();
    }

    @Override
    public void save(Card card) {
        cards.add(card);
    }
}
