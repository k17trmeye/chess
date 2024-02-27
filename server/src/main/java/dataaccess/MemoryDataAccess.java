package dataaccess;
import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MemoryDataAccess implements DataAccess{
    private final List<UserData> dbUserdata = new ArrayList<>();
    @Override
    public void clearUserData() {
        dbUserdata.clear();
    }
    @Override
    public String checkUserName(String userName) {
        for (UserData userData : dbUserdata) {
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
        dbUserdata.add(newUser);

        return userName;
    }
    @Override
    public String getPassword(String username) {
        for (UserData userData : dbUserdata) {
            if (userData.getUsername().equals(username)) {
                return userData.getPassword();
            }
        }
        return null;
    }
    @Override
    public void setLoggedIn(String username) {
        for (UserData userData : dbUserdata) {
            if (userData.getUsername().equals(username)) {
                if (!userData.getLoggedIn()) {
                    userData.setLoggedIn();
                }
            }
        }
    }

    private final List<AuthData> dbAuthdata = new ArrayList<>();
    @Override
    public void clearAuthData(){
        dbAuthdata.clear();
    }
    @Override
    public AuthData createAuth (String username) {
        // Create a new AuthData
        String authToken = newAuthToken();
        AuthData newAuthData = new AuthData(username, authToken);
        // Add new AuthData to DataBase
        dbAuthdata.add(newAuthData);
        // Return the authToken
        return newAuthData;
    }
    @Override
    public String getAuthToken (String username) {
        // Create a new AuthData
        String authToken = newAuthToken();
        AuthData newAuthData = new AuthData(username, authToken);
        // Add new AuthData to DataBase
        dbAuthdata.add(newAuthData);
        return authToken;
    }
    @Override
    public String getUsername (String authToken) {
        for (AuthData authData : dbAuthdata) {
            if (authData.getAuthToken().equals(authToken)) {
                return authData.getUsername();
            }
        }
        return null;
    }
    @Override
    public boolean deleteAuth (String authToken) {
        for (AuthData authData : dbAuthdata) {
            if (authData.getAuthToken().equals(authToken)) {
                dbAuthdata.remove(authData);
                return true;
            }
        }
        return false;
    }

    private final List<GameData> dbGamedata = new ArrayList<>();
    @Override
    public void clearGameData(){
        dbGamedata.clear();
    }
    @Override
    public List<GameData> listGames() {
        return dbGamedata;
    }
    @Override
    public Integer createGame(String gameName) {
        // Create a random 4 digit int for the gameID
        Random random = new Random();
        Integer randomNumber = random.nextInt(9000) + 1000;

        // Create new GameData
        for (GameData gameData : dbGamedata) {
            if (gameData.getGameID().equals(randomNumber)){
                randomNumber = random.nextInt(9000) + 1000;
            }
        }
        GameData newGame = new GameData(randomNumber, null,
                null, gameName, new ChessGame());

        // Add newGame to DataBase
        dbGamedata.add(newGame);

        // Return gameID
        return randomNumber;
    }
    @Override
    public Integer getGame(Integer gameID) {
        for (GameData eachGame : dbGamedata) {
            if (eachGame.getGameID().equals(gameID)) {
                return gameID;
            }
        }
        return 0;
    }
    @Override
    public boolean joinGame(String username, String playerColor, Integer gameID) {
        for (GameData eachGame : dbGamedata) {
            if (eachGame.getGameID().equals(gameID)) {
                return eachGame.addPlayer(username, playerColor);
            }
        }
        return false;
    }

    @Override
    public boolean getPlayerColor(String username, String playerColor, Integer gameID) {
        for (GameData eachGame : dbGamedata) {
            if (eachGame.getGameID().equals(gameID)) {
                if (playerColor == "BLACK") {
                    if (eachGame.getBlackUsername() == null) {
                        return true;
                    }
                }
                else if (playerColor == "WHITE") {
                    if (eachGame.getWhiteUsername() == null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private String newAuthToken() {
        StringBuilder sb;
        boolean valid = false;
        while (true) {
            int length = 12;
            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            Random random = new Random();
            sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                int index = random.nextInt(characters.length());
                sb.append(characters.charAt(index));
            }
            for (AuthData authData : dbAuthdata) {
                if (sb.toString().equals(authData.getAuthToken())) {
                    valid = true;
                }
            }
            if (!valid) {
                break;
            }
        }
        return sb.toString();
    }
}
