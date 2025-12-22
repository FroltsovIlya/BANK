import java.time.LocalDateTime;

public class CardActivationRequest {

    private final String cardNumber;
    private final String ownerName;
    private final LocalDateTime createdAt;

    public CardActivationRequest(String cardNumber, String ownerName) {
        this.cardNumber = cardNumber;
        this.ownerName = ownerName;
        this.createdAt = LocalDateTime.now();
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }
}
