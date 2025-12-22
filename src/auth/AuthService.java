import user.User;

public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String username, String password) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new RuntimeException("No such user");
        }

        if (!user.checkPassword(password)) {
            throw new RuntimeException("Wrong password");
        }

        return user;
    }
}
