package model;

import java.util.Objects;

public class UserData {
    private final String username;
    private final String password;
    private final String email;

    private static boolean loggedIn;

    public UserData(String username, String password, String email, boolean loggedIn) {
        this.username = username;
        this.password = password;
        this.email = email;
        UserData.loggedIn = loggedIn;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn() {
        loggedIn = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return Objects.equals(username, userData.username) && Objects.equals(password, userData.password) && Objects.equals(email, userData.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, email);
    }

    @Override
    public String toString() {
        return "UserData{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
