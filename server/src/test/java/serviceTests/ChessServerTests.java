package serviceTests;
import dataaccess.DataAccessException;
import dataaccess.MemoryDataAccess;
import model.*;
import org.junit.jupiter.api.*;
import passoffTests.testClasses.TestException;
import service.Services;

import static org.junit.jupiter.api.Assertions.*;

public class ChessServerTests {
    private static Services services;
    @BeforeAll
    public static void init() throws DataAccessException {
        services = new Services(new MemoryDataAccess());
    }
    @BeforeEach
    public void init_test() throws DataAccessException {
        services.createUser("existingUser", "existingPassword", "existing@email.com");
        services.createUser("nextUser", "nextPassword", "next@email.com");
        services.createAuth("existingUser");
        services.createAuth("nextUser");
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

}
