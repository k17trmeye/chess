package service;

import dataAccess.DataAccessException;
import dataAccess.*;
import model.GameData;

import java.util.List;

public class ListGamesService {
    private final AuthData_INT authData;
    private final GameData_INT gameData;

    public ListGamesService(UserData_INT userData, AuthData_INT authData, GameData_INT gameData) {
        this.authData = authData;
        this.gameData = gameData;
    }

    public String getUser(String username) throws DataAccessException{
        return authData.getUsername(username);
    }

    public List<GameData> getGames() throws DataAccessException{
        return gameData.listGames();
    }
}
