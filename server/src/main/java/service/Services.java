package service;

import dataAccess.*;
import model.GameData;

import java.util.List;

public class Services {
    private final DataAccess dataAccess;

    public Services(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public boolean clear() throws DataAccessException {
        boolean dataEmpty = dataAccess.clearUserData();
        boolean authEmpty = dataAccess.clearAuthData();
        boolean gameEmpty = dataAccess.clearGameData();

        return (dataEmpty & authEmpty & gameEmpty);
    }

    public boolean checkPassword(String username, String password) throws DataAccessException {
        String user_password = dataAccess.getPassword(username);
        if (password.equals(user_password)) {
            return true;
        }
        return false;
    }

    public String getToken(String username) throws DataAccessException {
        return dataAccess.getAuthToken(username);
    }

    public String getUser(String username) throws DataAccessException{
        return dataAccess.checkUserName(username);
    }

    public String createUser(String username, String password, String email) throws DataAccessException{
        return dataAccess.createUser(username, password, email);
    }

    public String createAuth(String username) throws DataAccessException{
        return dataAccess.createAuth(username);
    }

    public List<GameData> getGames() throws DataAccessException{
        return dataAccess.listGames();
    }

    public String getAuthToken(String username) throws DataAccessException {
        return dataAccess.getUsername(username);
    }

    public boolean deleteAuth(String authToken) throws DataAccessException {
        return dataAccess.deleteAuth(authToken);
    }
}
