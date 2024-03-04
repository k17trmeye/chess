package dataAccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import exception.ResponseException;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Random;

import com.google.gson.Gson;

public class MySQLDataAccess implements DataAccess{

    public MySQLDataAccess() throws DataAccessException, ResponseException {
        configureDatabase();
    }

    @Override
    public void clearAuthData() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "TRUNCATE TABLE authData";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AuthData createAuth(String username) throws DataAccessException {
        if (username != null) {
            String authToken = newAuthToken();
            try (var conn = DatabaseManager.getConnection()) {
                var statement = "INSERT INTO authData (username, authToken) VALUES (?, ?)";
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, authToken);
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return new AuthData(username, authToken);
        }
        return null;
    }

    @Override
    public String getAuthToken(String username) throws DataAccessException {
        String authToken = newAuthToken();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "INSERT INTO authData (username, authToken) VALUES (?, ?)";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, authToken);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return authToken;
    }

    @Override
    public String getUsername(String authToken) throws DataAccessException {
        String username = null;
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username FROM authData WHERE authToken = ?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, authToken);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        username =  rs.getString("username");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return username;
    }

    @Override
    public boolean deleteAuth(String authToken) throws DataAccessException {
        if (getUsername(authToken) != null) {
            try (var conn = DatabaseManager.getConnection()) {
                var statement = "DELETE FROM authData WHERE authToken = ?";
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.setString(1, authToken);
                    preparedStatement.executeUpdate();
                    if (getUsername(authToken) == null) {
                        return true;
                    } else {
                        return false;
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            return false;
        }
    }

    @Override
    public void clearGameData() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "TRUNCATE TABLE gameData";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<GameData> listGames() throws DataAccessException {
        List<GameData> result = new ArrayList<>();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT * FROM gameData";
            try (var ps = conn.prepareStatement(statement)) {
                try (var rs = ps.executeQuery()) {
                    while (rs.next()) {
                        result.add(readGameData(rs));
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private GameData readGameData(ResultSet rs) throws SQLException {
        var gameID = rs.getInt("gameID");
        var whiteUsername = rs.getString("whiteUsername");
        var blackUsername = rs.getString("blackUsername");
        var gameName = rs.getString("gameName");
        String chessGame = rs.getString("game");
        var game = new Gson().fromJson(chessGame, ChessGame.class);
        return new GameData(gameID, whiteUsername, blackUsername, gameName, game);
    }

    @Override
    public Integer createGame(String gameName) throws DataAccessException {
        Integer gameID = newGameID();
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "INSERT INTO gameData (gameID, gameName, game) VALUES (?, ?, ?)";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setInt(1, gameID);
                preparedStatement.setString(2, gameName);
                var json = new Gson().toJson(new ChessGame());
                preparedStatement.setString(3, json);
                preparedStatement.executeUpdate();
            }
            return gameID;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer getGame(Integer gameID) throws DataAccessException {
        int newGameID = 0;
        try (var conn = DatabaseManager.getConnection()) {
            String statement = "SELECT gameID FROM gameData WHERE gameID = ?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        newGameID =  rs.getInt("gameID");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return newGameID;
    }

    @Override
    public boolean joinGame(String username, String playerColor, Integer gameID) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            String statement = null;
            if (Objects.equals(playerColor, "BLACK")) {
                statement = "UPDATE gameData SET blackUsername = ? WHERE gameID = ?";
            } else if (Objects.equals(playerColor, "WHITE")){
                statement = "UPDATE gameData SET whiteUsername = ? WHERE gameID = ?";
            }
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                ps.setInt(2, gameID);
                System.out.println("joinGame: " + ps);
                ps.executeUpdate();
            }
            if (statement == null) {
                return false;
            }
            else {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean getPlayerColor(String username, String playerColor, Integer gameID) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            String statement = null;
            if (Objects.equals(playerColor, "BLACK")) {
                statement = "SELECT blackUsername FROM gameData WHERE gameID = ?";
            } else if (Objects.equals(playerColor, "WHITE")){
                statement = "SELECT whiteUsername FROM gameData WHERE gameID = ?";
            }
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setInt(1, gameID);
                System.out.println("getPlayerColor: " + ps);
                String newUsername;
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        System.out.println(playerColor);
                        if (playerColor == "WHITE") {
                            newUsername = rs.getString("whiteUsername");
                            System.out.println(newUsername);
                            if (newUsername == null) {
                                return true;
                            }
                        } else if (playerColor == "BLACK" || Objects.equals(playerColor, "BLACK")) {
                            System.out.println("here");
                            newUsername = rs.getString("blackUsername");
                            System.out.println(newUsername);
                            if (newUsername == null) {
                                return true;
                            }
                        }
                    }
                }
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clearUserData() throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "TRUNCATE TABLE userData";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String checkUserName(String userName) throws DataAccessException {
        String newUserName = null;
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT username FROM userData WHERE username = ?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, userName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return null;
                    }
                    else {
                        newUserName =  rs.getString("username");
                    }

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return newUserName;
    }

    @Override
    public String createUser(String userName, String password, String email) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "INSERT INTO userData (username, password, email) VALUES (?, ?, ?)";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, userName);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, email);
                preparedStatement.executeUpdate();
            }
            return userName;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getPassword(String username) throws DataAccessException {
        String password = null;
        try (var conn = DatabaseManager.getConnection()) {
            var statement = "SELECT password FROM userData WHERE username = ?";
            try (PreparedStatement ps = conn.prepareStatement(statement)) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        password =  rs.getString("password");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return password;
    }


    private final String[] createGameDB = {
            """
            CREATE TABLE IF NOT EXISTS  gameData (
               `id` int NOT NULL AUTO_INCREMENT,
               `gameID` int NOT NULL,
               `whiteUsername` varchar(256) DEFAULT NULL,
               `blackUsername` varchar(256) DEFAULT NULL,
               `gameName` varchar(256) NOT NULL,
               `game` blob NOT NULL,
               `json` TEXT DEFAULT NULL,
               PRIMARY KEY (`id`)
             ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private final String[] createAuthDB = {
            """
            CREATE TABLE IF NOT EXISTS  authData (
               `id` int NOT NULL AUTO_INCREMENT,
               `username` varchar(256) NOT NULL,
               `authToken` varchar(256) NOT NULL,
               `json` TEXT DEFAULT NULL,
               PRIMARY KEY (`id`)
             ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };
    private final String[] createUserDB = {
            """
            CREATE TABLE IF NOT EXISTS  userData (
               `id` int NOT NULL AUTO_INCREMENT,
               `username` varchar(256) NOT NULL,
               `password` varchar(256) NOT NULL,
               `email` varchar(256) NOT NULL,
               `json` TEXT DEFAULT NULL,
               PRIMARY KEY (`id`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private void configureDatabase() throws ResponseException, DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            // Used to clear DB
//            var newStatement = "DROP TABLE IF EXISTS gameData";
//            try (var preparedStatement = conn.prepareStatement(newStatement)) {
//                preparedStatement.executeUpdate();
//            }
//            newStatement = "DROP TABLE IF EXISTS userData";
//            try (var preparedStatement = conn.prepareStatement(newStatement)) {
//                preparedStatement.executeUpdate();
//            }
//            newStatement = "DROP TABLE IF EXISTS authData";
//            try (var preparedStatement = conn.prepareStatement(newStatement)) {
//                preparedStatement.executeUpdate();
//            }

            // Used to create DB
            for (var statement : createUserDB) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
            for (var statement : createAuthDB) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
            for (var statement : createGameDB) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new ResponseException(500, String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }
    private String newAuthToken() throws DataAccessException {
        StringBuilder sb;

        int length = 12;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        return sb.toString();
    }

    private Integer newGameID() {
        // Create a random 4 digit int for the gameID
        Random random = new Random();
        return random.nextInt(9000) + 1000;
    }
}
