package dataAccess;

import model.*;

import java.util.List;

public interface DataAccess {
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    // AuthData functions
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    List<AuthData> getAuthData() throws DataAccessException;
    void clearAuthData() throws DataAccessException;
    AuthData createAuth (String username) throws DataAccessException;
    String getAuthToken(String username) throws DataAccessException;
    String getUsername (String authToken) throws DataAccessException;
    boolean deleteAuth (String authToken) throws DataAccessException;
    List<GameData> getGameData() throws DataAccessException;

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    // GameData functions
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    void clearGameData() throws DataAccessException;
    List<GameData> listGames() throws DataAccessException;
    Integer createGame(String gameName) throws DataAccessException;
    Integer getGame(Integer gameID) throws DataAccessException;
    boolean joinGame(String username, String playerColor, Integer gameID) throws DataAccessException;
    public boolean getPlayerColor(String username, String playerColor, Integer gameID) throws DataAccessException;

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    // UserData functions
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    List<UserData> getUserData() throws DataAccessException;
    void clearUserData() throws DataAccessException;
    String checkUserName(String userName) throws DataAccessException;
    String createUser(String userName, String password, String email) throws DataAccessException;
    String getPassword(String username) throws DataAccessException;
    boolean returnLoginStatus(String username) throws DataAccessException;
    void setLoggedIn(String username) throws DataAccessException;
    boolean setLoggedOut(String password) throws DataAccessException;
}
