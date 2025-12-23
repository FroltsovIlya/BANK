package com.example.bank.user;

public class User {

    private final String userName;
    private final String password;
    private final boolean admin;

    public User(String userName, String password, boolean admin) {
        this.userName = userName;
        this.password = password;
        this.admin = admin;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isAdmin() {
        return admin;
    }

    public boolean checkPassword(String input) {
        return password.equals(input);
    }
}
