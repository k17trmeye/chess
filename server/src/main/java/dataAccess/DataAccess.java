package dataAccess;

import chess.ChessGame;
import model.*;

import java.util.List;

public interface DataAccess {
    void clearAuthData() throws DataAccessException;
    AuthData createAuth (String username) throws DataAccessException;
    String getAuthToken(String username) throws DataAccessException;
    String getUsername (String authToken) throws DataAccessException;
    boolean deleteAuth (String authToken) throws DataAccessException;

    void clearGameData() throws DataAccessException;
    List<GameData> listGames() throws DataAccessException;
    Integer createGame(String gameName) throws DataAccessException;
    Integer getGame(Integer gameID) throws DataAccessException;
    boolean returnChessGame(Integer gameID, ChessGame chessGame) throws DataAccessException;
    ChessGame getChessGame(Integer gameID) throws DataAccessException;
    boolean joinGame(String username, String playerColor, Integer gameID) throws DataAccessException;
    String getPlayerColor(String playerColor, Integer gameID) throws DataAccessException;

    void clearUserData() throws DataAccessException;
    String checkUserName(String userName) throws DataAccessException;
    String createUser(String userName, String password, String email) throws DataAccessException;
    String getPassword(String username) throws DataAccessException;
}
