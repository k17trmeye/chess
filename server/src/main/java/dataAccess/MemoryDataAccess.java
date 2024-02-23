package dataAccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MemoryDataAccess implements DataAccess{
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    // UserData functions
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    private final List<UserData> DB_UserData = new ArrayList<>();

    @Override
    public List<UserData> getUserData() {
        return DB_UserData;
    }

    @Override
    public void clearUserData() {
        DB_UserData.clear();
    }
    @Override
    public String checkUserName(String userName) {
        for (UserData userData : DB_UserData) {
            if (userData.getUsername().equals(userName)) {
                return userData.getUsername();
            }
        }
        return null;
    }
    @Override
    public String createUser(String userName, String password, String email) {
        // Create a new user
        UserData newUser = new UserData(userName, password, email, false);

        // Add the new user to the database
        DB_UserData.add(newUser);

        return userName;
    }
    @Override
    public String getPassword(String username) {
        for (UserData userData : DB_UserData) {
            if (userData.getUsername().equals(username)) {
                return userData.getPassword();
            }
        }
        return null;
    }
    @Override
    public boolean returnLoginStatus(String username) {
        for (UserData userData : DB_UserData) {
            if (userData.getUsername().equals(username)) {
                return userData.getLoggedIn();
            }
        }
        return false;
    }
    @Override
    public void setLoggedIn(String username) {
        for (UserData userData : DB_UserData) {
            if (userData.getUsername().equals(username)) {
                if (!userData.getLoggedIn()) {
                    userData.setLoggedIn();
                }
            }
        }
    }
    @Override
    public boolean setLoggedOut(String username) {
        for (UserData userData : DB_UserData) {
            if (userData.getUsername().equals(username)) {
                if (!userData.getLoggedIn()) {
                    userData.setLoggedOut();
                    return true;
                }
            }
        }
        return false;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    // AuthData functions
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    private final List<AuthData> DB_AuthData = new ArrayList<>();

    @Override
    public List<AuthData> getAuthData() {
        return DB_AuthData;
    }
    @Override
    public void clearAuthData(){
        DB_AuthData.clear();
    }
    @Override
    public AuthData createAuth (String username) {
        // Create a random 12 character string for the authToken
        int length = 12;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        for (AuthData authData : DB_AuthData) {
            if (sb.toString().equals(authData.getAuthToken())) {
                return null;
            }
        }
        // Create a new AuthData
        AuthData newAuthData = new AuthData(username, sb.toString());

        // Add new AuthData to DataBase
        DB_AuthData.add(newAuthData);

        // Return the authToken
        return newAuthData;
    }
    @Override
    public String getAuthToken (String username) {
        for (AuthData authData : DB_AuthData) {
            if (authData.getUsername().equals(username)) {
                return authData.getAuthToken();
            }
        }
        return null;
    }
    @Override
    public String getUsername (String authToken) {
        for (AuthData authData : DB_AuthData) {
            if (authData.getAuthToken().equals(authToken)) {
                return authData.getUsername();
            }
        }
        return null;
    }
    @Override
    public boolean deleteAuth (String authToken) {
        for (AuthData authData : DB_AuthData) {
            if (authData.getAuthToken().equals(authToken)) {
                DB_AuthData.remove(authData);
                return true;
            }
        }
        return false;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    // GameData functions
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    private final List<GameData> DB_GameData = new ArrayList<>();;

    @Override
    public List<GameData> getGameData() {
        return DB_GameData;
    }
    @Override
    public void clearGameData(){
        DB_GameData.clear();
    }
    @Override
    public List<GameData> listGames() {
        return DB_GameData;
    }
    @Override
    public Integer createGame(String gameName) {
        // Create a random 6 digit int for the gameID
        Random random = new Random();
        Integer randomNumber = random.nextInt(9000) + 1000;

        // Create new GameData
        for (GameData gameData : DB_GameData) {
            if (gameData.getGameID().equals(randomNumber)){
                return 0;
            }
        }
        GameData newGame = new GameData(randomNumber, null,
                null, gameName, new ChessGame());

        // Add newGame to DataBase
        DB_GameData.add(newGame);

        // Return gameID
        return randomNumber;
    }
    @Override
    public Integer getGame(Integer gameID) {
        for (GameData eachGame : DB_GameData) {
            if (eachGame.getGameID().equals(gameID)) {
                return gameID;
            }
        }
        return 0;
    }
    @Override
    public boolean joinGame(String username, String playerColor, Integer gameID) {
        for (GameData eachGame : DB_GameData) {
            if (eachGame.getGameID().equals(gameID)) {
                return eachGame.addPlayer(username, playerColor);
            }
        }
        return false;
    }
}
