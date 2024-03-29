package clientTests;

import org.junit.jupiter.api.*;
import server.Server;
import ui.ServerFacade;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class ServerFacadeTests {

    private static Server server;
    static ServerFacade facade;
    static String authToken;
    static String gameID;

    @BeforeAll
    public static void init() throws IOException {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade(port);
        facade.clear();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @BeforeEach
    void setUp() throws Exception {
        facade.registerUser("player2", "password2", "p2@email.com");
        facade.registerUser("player3", "password3", "p3@email.com");
        authToken = facade.loginUser("player3", "password3");
        gameID = facade.createGame(authToken, "existingGame");
    }

    @AfterEach
    void erase() throws Exception {
        facade.clear();
    }

    @Test
    void registerTestPos() throws Exception {
        var authData = facade.registerUser("player1", "password", "p1@email.com");
        assertTrue(authData.toString().contains("player1"), "Error registering user");
    }

    @Test
    void registerTestNeg() throws Exception {
        var authData = facade.registerUser("player2", "password2", "p2@email.com");
        assertTrue(authData.toString().contains("Error"), "Error registering already added user");
    }

    @Test
    void loginUserTestPos() throws Exception {
        var authData = facade.loginUser("player2", "password2");
        assertNotNull(authData, "Error logging in user");
    }

    @Test
    void loginUserTestNeg() throws Exception {
        var authData = facade.loginUser("player1", "password");
        assertTrue(authData.toString().contains("Error"), "Error logging in with invalid user");
    }

    @Test
    void logoutUserTestPos() throws Exception {
        var authData = facade.logoutUser(authToken);
        assertEquals(authData, "{}","Error logging in user");
    }

    @Test
    void logoutUserTestNeg() throws Exception {
        var authData = facade.logoutUser("randomToken");
        assertNotNull(authData, "Error logging out with invalid authToken");
    }

    @Test
    void createGameTestPos() throws Exception {
        var authData = facade.createGame(authToken, "newGame");
        assertNotNull(authData,"Error creating game");
    }

    @Test
    void createGameTestNeg() throws Exception {
        var authData = facade.createGame("hello", "noGame");
        assertTrue(authData.toString().contains("Error"), "Error creating game with invalid authToken");
    }

    @Test
    void joinGameTestPos() throws Exception {
        var authData = facade.joinGame(authToken, "BLACK", gameID);
        assertNotNull(authData,"Error joining game");
    }

    @Test
    void joinGameTestNeg() throws Exception {
        var authData = facade.joinGame("hello", "WHITE", gameID);
        assertTrue(authData.toString().contains("Error"), "Error joining game with invalid authToken");
    }

    @Test
    void listGamesTestPos() throws Exception {
        var authData = facade.listGames(authToken);
        assertNotNull(authData, "Error listing games");
    }

    @Test
    void listGamesTestNeg() throws Exception {
        var authData = facade.listGames("randomToken");
        assertTrue(authData.toString().contains("Error"), "Error listing games with invalid authToken");
    }

    @Test
    void clearTest() throws Exception {
        facade.clear();
    }

}
