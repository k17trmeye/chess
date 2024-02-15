package dataAccess;

import chess.ChessGame;
import model.*;

import java.util.List;
import java.util.Random;

public interface DataAccess {
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    // UserData functions
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    List<UserData> getUserData() throws DataAccessException;
    void clearUserData() throws DataAccessException;
    String checkUserName(String userName) throws DataAccessException;
    void createUser(String userName, String password, String email) throws DataAccessException;
    String getPassword(String username) throws DataAccessException;

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    // GameData functions
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    void clearGameData() throws DataAccessException;
    List<GameData> listGames() throws DataAccessException;
    int createGame(String username, String gameName) throws DataAccessException;
    Integer getGame(Integer gameID) throws DataAccessException;
    boolean joinGame(String username, String playerColor, Integer gameID) throws DataAccessException;

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    // AuthData functions
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    List<AuthData> getAuthData() throws DataAccessException;
    void clearAuthData() throws DataAccessException;
    String createAuth (String username) throws DataAccessException;
    String getAuthToken (String username) throws DataAccessException;
    String getUsername (String authToken) throws DataAccessException;
    void deleteAuth (String authToken) throws DataAccessException;
}
