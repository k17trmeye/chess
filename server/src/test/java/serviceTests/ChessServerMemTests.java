package serviceTests;
import dataaccess.DataAccessException;
import dataaccess.MemoryDataAccess;
import model.*;
import org.junit.jupiter.api.*;
import passoffTests.testClasses.TestException;
import service.Services;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChessServerMemTests {
    private static Services services;
    private static AuthData authData;
    private static AuthData nextAuthData;
    @BeforeAll
    public static void init() throws DataAccessException {
        services = new Services(new MemoryDataAccess());
    }
    @BeforeEach
    public void init_test() throws DataAccessException {
        services.createUser("existingUser", "existingPassword", "existing@email.com");
        services.createUser("nextUser", "nextPassword", "next@email.com");
        authData = services.createAuth("existingUser");
        nextAuthData = services.createAuth("nextUser");
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
    public void CreateUserTest() throws TestException, DataAccessException {
        // Valid test
        String result = services.createUser("trace", "meyers", "email.com");
        Assertions.assertTrue(result.contains("trace"), "Error creating user");

        // Invalid test
//        result = services.createUser("existingUser", "existingPassword", "existing@email.com");
//        Assertions.assertTrue(result, "Error creating invalid user");
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
        deleted = services.deleteAuth(authToken);
        assertNotEquals(true, deleted, "Error getting username");
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
        assertEquals(username, "existingUser","Error checking username");

        // Invalid Test
        String nextAuthToken = services.getUser("existingUser_1");
        assertNull(nextAuthToken, "Error checking invalid username");
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
