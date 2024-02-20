package dataAccess;

import model.*;

import java.util.List;

public interface GameData_INT {
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    // GameData functions
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    void clearGameData() throws DataAccessException;
    List<GameData> listGames() throws DataAccessException;
    int createGame(String username, String gameName) throws DataAccessException;
    Integer getGame(Integer gameID) throws DataAccessException;
    boolean joinGame(String username, String playerColor, Integer gameID) throws DataAccessException;
}
