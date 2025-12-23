package com.example.bank.service;

import com.example.bank.card.Card;
import com.example.bank.repository.CardRepository;
import com.example.bank.request.CardActivationRequest;
import com.example.bank.request.CardActivationRequestRepository;
import com.example.bank.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final CardActivationRequestRepository requestRepository;

    public CardService(CardRepository cardRepository,
                       CardActivationRequestRepository requestRepository) {
        this.cardRepository = cardRepository;
        this.requestRepository = requestRepository;
    }

    public List<Card> getUserCards(User user) {
        return cardRepository.findByOwner(user.getUserName());
    }

    public void transfer(String from, String to, int amount) {
        Card source = cardRepository.findByNumber(from);
        Card target = cardRepository.findByNumber(to);

        if (source == null || target == null)
            throw new RuntimeException("Card not found");

        source.withdraw(amount);
        target.deposit(amount);
    }

    public void requestCardActivation(String cardNumber, User user) {
        if (cardRepository.findByNumber(cardNumber) != null)
            throw new RuntimeException("Card already exists");

        if (requestRepository.existsByCardNumber(cardNumber))
            throw new RuntimeException("Request already exists");

        requestRepository.save(
                new CardActivationRequest(cardNumber, user.getUserName())
        );
    }

    public void approveActivation(String cardNumber, User admin) {
        if (!admin.isAdmin())
            throw new RuntimeException("Admin only");

        CardActivationRequest request = requestRepository.findAll()
                .stream()
                .filter(r -> r.getCardNumber().equals(cardNumber))
                .findFirst()
                .orElseThrow();

        Card card = new Card(
                request.getOwnerName(),
                request.getCardNumber()
        );

        card.activate();
        cardRepository.save(card);
        requestRepository.deleteByCardNumber(cardNumber);
    }
}
