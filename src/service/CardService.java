import user.User;

import java.util.List;

public class CardService {

    private final CardRepository cardRepository;
    private final CardActivationRequestRepository requestRepository;

    public CardService(
            CardRepository cardRepository,
            CardActivationRequestRepository requestRepository
    ) {
        this.cardRepository = cardRepository;
        this.requestRepository = requestRepository;
    }

    // 👤 просмотр своих карт
    public List<Card> getUserCards(User user) {
        return cardRepository.findByOwner(user.getUserName());
    }

    public void transfer(String fromCard, String toCard, int amount) {

        Card source = cardRepository.findByNumber(fromCard);
        Card target = cardRepository.findByNumber(toCard);

        if (source == null || target == null) {
            throw new RuntimeException("Card not found");
        }

        if (!source.isActive()) {
            throw new RuntimeException("Source card is not active");
        }

        if (!target.isActive()) {
            throw new RuntimeException("Target card is not active");
        }

        if (source.getBalance() < amount) {
            throw new RuntimeException("Not enough money");
        }

        // 🔒 после ВСЕХ проверок
        source.withdraw(amount);
        target.deposit(amount);
    }


    // 🚫 запрос блокировки
    public void requestBlock(String cardNumber, User user) {

        Card card = cardRepository.findByNumber(cardNumber);

        if (card == null || !card.getOwnerName().equals(user.getUserName())) {
            throw new RuntimeException("Access denied");
        }

        card.requestBlock();
    }

    public void requestCardActivation(String cardNumber, User user) {

        if (!CardNumberValidator.isValid(cardNumber)) {
            throw new RuntimeException("Invalid card number format");
        }

        if (cardRepository.findByNumber(cardNumber) != null) {
            throw new RuntimeException("Card already exists");
        }

        if (requestRepository.existsByCardNumber(cardNumber)) {
            throw new RuntimeException("Request already exists");
        }

        requestRepository.save(
                new CardActivationRequest(cardNumber, user.getUserName())
        );
    }


    // 👮 админ: просмотр запросов
    public List<Card> getBlockRequests(User admin) {

        if (!admin.isAdmin()) {
            throw new RuntimeException("Admin only");
        }

        return cardRepository.findBlockRequests();
    }

    public List<CardActivationRequest> getActivationRequests(User admin) {

        if (!admin.isAdmin()) {
            throw new RuntimeException("Admin only");
        }

        return requestRepository.findAll();
    }

    public void approveActivation(String cardNumber, User admin) {

        if (!admin.isAdmin()) {
            throw new RuntimeException("Admin only");
        }

        CardActivationRequest request = requestRepository.findAll()
                .stream()
                .filter(r -> r.getCardNumber().equals(cardNumber))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Request not found"));

        Card card = new Card(
                request.getOwnerName(),
                request.getCardNumber()
        );

        card.activate();
        cardRepository.save(card);
        requestRepository.deleteByCardNumber(cardNumber);
    }


    // 👮 админ: блокировка
    public void blockCard(String cardNumber, User admin) {

        if (!admin.isAdmin()) {
            throw new RuntimeException("Admin only");
        }

        Card card = cardRepository.findByNumber(cardNumber);

        if (card == null) {
            throw new RuntimeException("Card not found");
        }

        card.block();
    }
}
