package service;

import chess.ChessGame;
import dataAccess.*;
import model.AuthData;
import model.GameData;

import java.util.List;

public class Services {
    private final DataAccess dataAccess;
    public Services(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }
    public void clear() throws DataAccessException {
        dataAccess.clearUserData();
        dataAccess.clearAuthData();
        dataAccess.clearGameData();
    }
    public boolean checkPassword(String username, String password) throws DataAccessException {
        return password.equals(dataAccess.getPassword(username));
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
    public AuthData createAuth(String username) throws DataAccessException{
        return dataAccess.createAuth(username);
    }
    public String getUsername(String authToken) throws DataAccessException {
        return dataAccess.getUsername(authToken);
    }
    public boolean deleteAuth(String authToken) throws DataAccessException {
        return dataAccess.deleteAuth(authToken);
    }
    public List<GameData> listGames() throws DataAccessException{
        return dataAccess.listGames();
    }
    public Integer createGame(String gameName) throws DataAccessException{
        return dataAccess.createGame(gameName);
    }
    public Integer getGame(Integer gameID) throws DataAccessException {
        return dataAccess.getGame(gameID);
    }
    public boolean joinGame(String username, String playerColor, Integer gameID) throws DataAccessException {
        return dataAccess.joinGame(username, playerColor, gameID);
    }
    public String getPlayerColor(String playerColor, Integer gameID) throws DataAccessException {
        return dataAccess.getPlayerColor(playerColor, gameID);
    }
    public boolean returnChessGame(Integer gameID, ChessGame chessGame) throws DataAccessException{
        return dataAccess.returnChessGame(gameID, chessGame);
    }

    public ChessGame getChessGame(Integer gameID) throws DataAccessException{
        return dataAccess.getChessGame(gameID);
    }
}