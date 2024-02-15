package dataAccess;

import model.AuthData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AuthData_DAO {
    private final List<AuthData> DB_AuthData;

    public AuthData_DAO() {
        this.DB_AuthData = new ArrayList<>();
    }

    public List<AuthData> getAuthData() {
        return DB_AuthData;
    }

    public void clear() throws DataAccessException {
        for (AuthData authData : DB_AuthData) {
            DB_AuthData.remove(authData);
        }
    }

    public String createAuth (String username) {
        // Create a random 12 character string for the authToken
        int length = 12;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        // Create a new AuthData
        AuthData newAuthData = new AuthData(username, sb.toString());

        // Add new AuthData to DataBase
        DB_AuthData.add(newAuthData);

        // Return the authToken
        return sb.toString();
    }

    public String getAuthToken (String username) {
        for (AuthData authData : DB_AuthData) {
            if (authData.getUsername().equals(username)) {
                return authData.getAuthToken();
            }
        }
        return null;
    }

    public String getUsername (String authToken) {
        for (AuthData authData : DB_AuthData) {
            if (authData.getUsername().equals(authToken)) {
                return authData.getUsername();
            }
        }
        return null;
    }

    public void deleteAuth (String authToken) {
        for (AuthData authData : DB_AuthData) {
            if (authData.getUsername().equals(authToken)) {
                DB_AuthData.remove(authData);
            }
        }
    }



}
