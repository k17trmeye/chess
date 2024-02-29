package dataaccess;

import model.AuthData;
import model.GameData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import exception.ResponseException;

import java.sql.SQLException;
import java.util.Random;

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
        return false;
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
        return null;
    }

    @Override
    public Integer createGame(String gameName) throws DataAccessException {
        return null;
    }

    @Override
    public Integer getGame(Integer gameID) throws DataAccessException {
        return null;
    }

    @Override
    public boolean joinGame(String username, String playerColor, Integer gameID) throws DataAccessException {
        return false;
    }

    @Override
    public boolean getPlayerColor(String username, String playerColor, Integer gameID) throws DataAccessException {
        return false;
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
        return null;
    }

    @Override
    public String createUser(String userName, String password, String email) throws DataAccessException {
        return null;
    }

    @Override
    public String getPassword(String username) throws DataAccessException {
        return null;
    }

    @Override
    public void setLoggedIn(String username) throws DataAccessException {

    }


    private final String[] createGameDB = {
            """
            CREATE TABLE IF NOT EXISTS  gameData (
               `id` int NOT NULL AUTO_INCREMENT,
               `gameID` int NOT NULL,
               `whiteUsername` varchar(256) NOT NULL,
               `blackUsername` varchar(256) NOT NULL,
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
}
