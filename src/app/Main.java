public class Main {

    public static void main(String[] args) {

        UserRepository userRepo = new InMemoryUserRepository();
        CardRepository cardRepository = new InMemoryCardRepository();
        CardActivationRequestRepository requestRepository =
                new InMemoryCardActivationRequestRepository();

        AuthService authService = new AuthService(userRepo);
        CardService cardService =
                new CardService(cardRepository, requestRepository);

        Application app = new Application(authService, cardService);
        app.run();
    }


}
