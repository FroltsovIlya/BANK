import user.User;

public interface UserRepository {

    User findByUsername(String username);
}
