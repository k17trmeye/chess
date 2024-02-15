package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameData_DAO {
    private final List<GameData> DB_GameData;

    public GameData_DAO() {
        this.DB_GameData = new ArrayList<>();
    }

    public List<GameData> getGameData() {
        return DB_GameData;
    }

    public void clear() throws DataAccessException {
        for (GameData gameData : DB_GameData) {
            DB_GameData.remove(gameData);
        }
    }

    public List<GameData> listGames() {
        return DB_GameData;
    }

    public int createGame(String username, String gameName) {
        // Create a random 6 digit int for the gameID
        Random random = new Random();
        int randomNumber = random.nextInt(900000) + 100000;

        // Create new GameData
        GameData newGame = new GameData(randomNumber, username + "_1",
                username + "_2", gameName, new ChessGame());

        // Add newGame to DataBase
        DB_GameData.add(newGame);

        // Return gameID
        return randomNumber;
    }

    public Integer getGame(Integer gameID) {
        for (GameData eachGame : DB_GameData) {
            if (eachGame.getGameID().equals(gameID)) {
                return gameID;
            }
        }
        return 0;
    }

    public boolean joinGame(String username, String playerColor, Integer gameID) {
        for (GameData eachGame : DB_GameData) {
            if (eachGame.getGameID().equals(gameID)) {
                eachGame.addPlayer(username, playerColor);
                return true;
            }
        }
        return false;
    }

}
