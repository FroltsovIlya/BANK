package com.example.bank.repository;

import com.example.bank.card.Card;
import java.util.List;

public interface CardRepository {

    Card findByNumber(String number);

    List<Card> findByOwner(String ownerName);

    List<Card> findBlockRequests();

    void save(Card card);
}
