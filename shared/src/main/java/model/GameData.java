package model;

import chess.ChessGame;

import java.util.Objects;

public class GameData {
    private final Integer gameID;
    private String whiteUsername;
    private String blackUsername;
    private final String gameName;
    private final ChessGame game;

    public GameData(Integer gameID, String newWhiteusername, String newBlackusername, String gameName, ChessGame game) {
        this.gameID = gameID;
        whiteUsername = newWhiteusername;
        blackUsername = newBlackusername;
        this.gameName = gameName;
        this.game = new ChessGame();
    }

    public Integer getGameID() {
        return gameID;
    }

    public String getWhiteUsername() {
        return whiteUsername;
    }

    public String getBlackUsername() {
        return blackUsername;
    }

    public String getGameName() {
        return gameName;
    }

    public ChessGame getGame() {
        return game;
    }

    public boolean addPlayer(String userName, String playerColor) {
        if (playerColor.equals("WHITE") && whiteUsername == null) {
            whiteUsername = userName;
            return true;
        }
        else if (playerColor.equals("BLACK") && blackUsername == null) {
            blackUsername = userName;
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameData gameData = (GameData) o;
        return Objects.equals(gameID, gameData.gameID) && Objects.equals(whiteUsername, gameData.whiteUsername) && Objects.equals(blackUsername, gameData.blackUsername) && Objects.equals(gameName, gameData.gameName) && Objects.equals(game, gameData.game);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameID, whiteUsername, blackUsername, gameName, game);
    }

    @Override
    public String toString() {
        return "GameData{" +
                "gameID=" + gameID +
                ", whiteUsername='" + whiteUsername + '\'' +
                ", blackUsername='" + blackUsername + '\'' +
                ", gameName='" + gameName + '\'' +
                ", game=" + game +
                '}';
    }
}
