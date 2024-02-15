package model;

import chess.ChessGame;

import java.util.Objects;

public class GameData {
    private final Integer gameID;
    private static String whiteUsername;
    private static String blackUsername;
    private final String gameName;
    private final ChessGame game;

    public GameData(Integer gameID, String new_whiteUsername, String new_blackUsername, String gameName, ChessGame game) {
        this.gameID = gameID;
        whiteUsername = new_whiteUsername;
        blackUsername = new_blackUsername;
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

    public void addPlayer(String userName, String playerColor) {
        if (playerColor.equals("WHITE")) {
            whiteUsername = userName;
        }
        else if (playerColor.equals("BLACK")) {
            blackUsername = userName;
        }
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
