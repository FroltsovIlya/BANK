import user.User;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUserRepository implements UserRepository {

    private final List<User> users = new ArrayList<>();

    public InMemoryUserRepository() {
        users.add(new User("Ilya", "passwd", false));
        users.add(new User("Vasya", "passwd3", false));
        users.add(new User("Admin", "Admin", true));
    }

    @Override
    public User findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUserName().equals(username))
                .findFirst()
                .orElse(null);
    }
}
