package dataAccess;

import model.*;

import java.util.List;

public interface DataAccess {
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    // AuthData functions
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    List<AuthData> getAuthData() throws DataAccessException;
    boolean clearAuthData() throws DataAccessException;
    String createAuth (String username) throws DataAccessException;
    String getAuthToken(String username) throws DataAccessException;
    String getUsername (String authToken) throws DataAccessException;
    boolean deleteAuth (String authToken) throws DataAccessException;
    List<GameData> getGameData() throws DataAccessException;

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    // GameData functions
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    boolean clearGameData() throws DataAccessException;
    List<GameData> listGames() throws DataAccessException;
    int createGame(String username, String gameName) throws DataAccessException;
    Integer getGame(Integer gameID) throws DataAccessException;
    boolean joinGame(String username, String playerColor, Integer gameID) throws DataAccessException;

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    // UserData functions
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    List<UserData> getUserData() throws DataAccessException;
    boolean clearUserData() throws DataAccessException;
    String checkUserName(String userName) throws DataAccessException;
    String createUser(String userName, String password, String email) throws DataAccessException;
    String getPassword(String username) throws DataAccessException;
}
