import user.User;

import java.util.Scanner;

public class Application {

    private final AuthService authService;
    private final CardService cardService;
    private final Scanner scanner = new Scanner(System.in);

    private User currentUser;

    public Application(AuthService authService, CardService cardService) {
        this.authService = authService;
        this.cardService = cardService;
    }

    public void run() {

        while (true) {

            if (currentUser == null) {
                loginFlow();
            } else {
                userFlow();
            }
        }
    }

    // ---------- LOGIN ----------

    private void loginFlow() {
        try {
            currentUser = login();
            System.out.println("Welcome, " + currentUser.getUserName());
        } catch (RuntimeException e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    private User login() {

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        return authService.login(username, password);
    }

    // ---------- USER / ADMIN FLOW ----------

    private void userFlow() {

        if (currentUser.isAdmin()) {
            adminMenu();
        } else {
            userMenu();
        }
    }

    // ---------- USER MENU ----------

    private void userMenu() {

        System.out.println("""
        1. View cards
        2. Transfer money
        3. Request card block
        4. Request new card
        5. Logout
        0. Exit program
        """);


        switch (scanner.nextLine()) {

            case "1" -> cardService.getUserCards(currentUser)
                    .forEach(c -> System.out.println(
                            c.getNumber() + " | " + c.getBalance() + " | " + c.getStatus()
                    ));

            case "2" -> {
                System.out.print("From card: ");
                String from = scanner.nextLine();
                System.out.print("To card: ");
                String to = scanner.nextLine();
                System.out.print("Amount: ");
                int amount = Integer.parseInt(scanner.nextLine());

                cardService.transfer(from, to, amount);
            }

            case "3" -> {
                System.out.print("Card number: ");
                cardService.requestBlock(scanner.nextLine(), currentUser);
            }

            case "4" -> {
                System.out.print("New card number: ");
                cardService.requestCardActivation(scanner.nextLine(), currentUser);
            }

            case "5" -> logout();


            case "0" -> exit();
        }
    }

    // ---------- ADMIN MENU ----------

    private void adminMenu() {

        System.out.println("""
        1. View block requests
        2. Block card
        3. View card activation requests
        4. Approve card activation
        5. Logout
        0. Exit program
        """);


        switch (scanner.nextLine()) {

            case "1" -> cardService.getBlockRequests(currentUser)
                    .forEach(c -> System.out.println(
                            c.getNumber() + " | " + c.getOwnerName()
                    ));

            case "2" -> {
                System.out.print("Card number: ");
                cardService.blockCard(scanner.nextLine(), currentUser);
            }

            case "3" -> cardService.getActivationRequests(currentUser)
                    .forEach(r -> System.out.println(
                            r.getCardNumber() + " | " + r.getOwnerName()
                    ));

            case "4" -> {
                System.out.print("Card number: ");
                cardService.approveActivation(scanner.nextLine(), currentUser);
            }

            case "5" -> logout();

        }
    }

    // ---------- SESSION CONTROL ----------

    private void logout() {
        System.out.println("User" + currentUser.getUserName() + " logged out");
        currentUser = null;
    }

    private void exit() {
        System.out.println("Goodbye!");
        System.exit(0);
    }
}
