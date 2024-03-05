package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.MySQLDataAccess;
import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.*;
import passoffTests.testClasses.TestException;
import service.Services;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChessServerSQLTests {

    private static Services services;
    private static AuthData authData;
    private static AuthData nextAuthData;
    private static Integer gameID;
    @BeforeAll
    public static void init() throws DataAccessException {
        services = new Services(new MySQLDataAccess());
    }
    @BeforeEach
    public void init_test() throws DataAccessException {
        services.createUser("existingUser", "existingPassword", "existing@email.com");
        services.createUser("nextUser", "nextPassword", "next@email.com");
        authData = services.createAuth("existingUser");
        nextAuthData = services.createAuth("nextUser");
        gameID = services.createGame("existingGame");
    }
    @AfterEach
    public void reset_test() throws DataAccessException {
        services.clear();
    }
    @AfterAll
    static void stopServer() throws DataAccessException{
        System.out.println("All tests passed");
    }

    @Test
    public void clearAuthDataTest() throws DataAccessException{
        // Valid Test
        services.clear();
        String test = services.getToken("existingUser");
        assertNotNull(test, "Error clearing authData");
    }

    @Test
    public void clearGameDataTest() throws DataAccessException{
        // Valid Test
        services.clear();
        Integer game = services.getGame(gameID);
        assertEquals(game, 0, "Error clearing gameData");
    }

    @Test
    public void clearUserDataTest() throws DataAccessException{
        // Valid Test
        services.clear();
        String username = services.getUsername("existingUser");
        assertNull(username, "Error clearing UserData");
    }

    @Test
    public void CreateUserTest() throws TestException, DataAccessException {
//         Valid test
        String result = services.createUser("trace", "meyers", "email.com");
        Assertions.assertTrue(result.contains("trace"), "Error creating user");

//         Invalid test
        result = services.createUser("existingUser", "existingPassword", "existing@email.com");
        Assertions.assertFalse(result.contains("trace"), "Error creating user");
    }

    @Test
    public void CreateAuthTest() throws TestException, DataAccessException {
        // Valid test
        AuthData result = services.createAuth("trace");
        assertEquals("trace", services.getUsername(result.getAuthToken()), "Error creating authData");

        // Invalid test
        String temp_authToken = services.getToken("trace");
        AuthData result_1 = services.createAuth("trace");
        assertNotSame(services.getToken("trace"), temp_authToken, "Error creating new authData");
    }

    @Test
    public void getUsernameTest() throws TestException, DataAccessException {
        // Valid Test
        String authToken = services.getToken("existingUser");
        String username = services.getUsername(authToken);
        assertEquals("existingUser", username, "Error getting username");

        // Invalid Test
        String new_authToken = services.getToken("existingUser_1");
        username = services.getUsername(new_authToken);
        assertNotEquals("existingUser", username, "Error getting username");
    }
    @Test
    public void deleteAuthTest() throws TestException, DataAccessException {
        // Valid Test
        String authToken = services.getToken("nextUser");
        boolean deleted = services.deleteAuth(authToken);
        assertTrue(deleted, "Error deleting authData");

        // Invalid Test
        boolean newDeleted = services.deleteAuth(authToken);
        assertNotEquals(true, newDeleted, "Error getting username");
    }

    @Test
    public void checkPasswordTest() throws TestException, DataAccessException {
        // Valid Test
        boolean test = services.checkPassword("existingUser","existingPassword");
        assertTrue(test, "Error checking password");

        // Invalid Test
        test = services.checkPassword("existingUser", "existingPassword_1");
        assertNotEquals(true, test, "Error checking invalid password");
    }

    @Test
    public void getUserTest() throws TestException, DataAccessException {
        // Valid Test
        boolean test = services.checkPassword("existingUser","existingPassword");
        assertTrue(test, "Error getting user");

        // Invalid Test
        test = services.checkPassword("existingUser", "existingPassword_1");
        assertNotEquals(true, test, "Error getting invalid user");
    }

    @Test
    public void getTokenTest() throws TestException, DataAccessException {
        // Valid Test
        String authToken = services.getToken("existingUser");
        assertNotEquals(authToken, authData.getAuthToken(),"Error getting token");

        // Invalid Test
        String nextAuthToken = services.getToken("existingUser");
        assertNotEquals(nextAuthToken, authToken, "Error getting invalid token");
    }

    @Test
    public void checkUsernameTest() throws TestException, DataAccessException {
        // Valid Test
        String username = services.getUser("existingUser");
        assertNotNull(username,"Error checking username");

        // Invalid Test
        String nextUser = services.getUser("existingUser_1");
        assertNull(nextUser, "Error checking username");
    }

    @Test
    public void listGamesTest() throws TestException, DataAccessException {
        // Valid Test
        List<GameData> gameData = services.listGames();
        List<GameData> testData = new ArrayList<>();
        assertEquals(gameData, testData,"Error listing game");
    }

    @Test
    public void createGameTest() throws TestException, DataAccessException {
        // Valid Test
        Integer gameID = services.createGame("gameName");
        assertNotNull(gameID,"Error creating game");

        // Invalid Test
        Integer nextGameID = services.createGame("existingUser_1");
        assertNotEquals(nextGameID, gameID,"Error not creating game");
    }

    @Test
    public void getGameTest() throws TestException, DataAccessException {
        // Valid Test
        Integer gameID = services.createGame("gameName");
        Integer retGameID = services.getGame(gameID);
        assertEquals(gameID, retGameID, "Error getting game");

        // Invalid Test
        Integer nextGameID = services.getGame(gameID + 4);
        assertEquals(nextGameID, 0,"Error not getting game");
    }

    @Test
    public void joinGameTest() throws TestException, DataAccessException {
        // Valid Test
        Integer gameID = services.createGame("game");
        boolean joined = services.joinGame("existingUser", "BLACK", gameID);
        assertTrue(joined, "Error joining game");

        // Invalid Test
        joined = services.joinGame("nextUser", "BLACK", gameID);
        assertFalse(joined, "Error not joining game");
    }

    @Test
    public void getPlayerColorTest() throws TestException, DataAccessException {
        // Valid Test
        Integer gameID = services.createGame("game");
        services.joinGame("existingUser", "BLACK", gameID);
        boolean playerColor = services.getPlayerColor("existingUser", "BLACK", gameID);
        assertFalse(playerColor, "Error getting player color");

        // Invalid Test
        playerColor = services.getPlayerColor("existingUser", "WHITE", gameID);
        assertTrue(playerColor, "Invalid player color");
    }
}
