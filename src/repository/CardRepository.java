import java.util.List;

public interface CardRepository {
    void save(Card card);
    List<Card> findByOwner(String ownerName);
    Card findByNumber(String number);
    List<Card> findBlockRequests();
}
