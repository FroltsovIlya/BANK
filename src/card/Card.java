import java.time.LocalDate;

public class Card {

    private final String number;
    private final String ownerName;
    private final LocalDate expireDate;
    private int balance;
    private CardStatus status;

    // 🔹 ДЛЯ СУЩЕСТВУЮЩИХ КАРТ
    public Card(
            String ownerName,
            String number,
            int balance,
            LocalDate expireDate,
            CardStatus status
    ) {
        this.ownerName = ownerName;
        this.number = number;
        this.balance = balance;
        this.expireDate = expireDate;
        this.status = status;
    }

    // 🔹 ДЛЯ НОВЫХ КАРТ (через запрос)
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
        return status == CardStatus.ACTIVE
                && !expireDate.isBefore(LocalDate.now());
    }

        public String getNumber() {
        return number;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getBalance() {
        return balance;
    }

    public CardStatus getStatus() {
        return status;
    }

    public void withdraw(int amount) {
        if (status != CardStatus.ACTIVE) {
            throw new RuntimeException("Source card blocked");
        }
        if (amount > balance) {
            throw new RuntimeException("Not enough money");
        }
        balance -= amount;
    }


    public void deposit(int amount) {
        if (status != CardStatus.ACTIVE) {
            throw new RuntimeException("Target card blocked");
        }
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
}
