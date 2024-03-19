package ui;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import java.util.Scanner;

public class Repl {
    private final ServerFacade serverFacade;
    Scanner scanner = new Scanner(System.in);
    private static String authToken;


    public Repl(Integer port) throws IOException {
        serverFacade = new ServerFacade(port);
        serverFacade.clear();
    }

    public void preLoginUI() throws IOException {
        System.out.println(EscapeSequences.BLACK_KING + "Welcome to 240 chess. Type help to get started." + EscapeSequences.WHITE_KING);
        System.out.print("[LOGGED OUT] >>> ");

         // Trim whitespace and convert to lowercase
        boolean running = true;

        while (running) {
            String command = scanner.nextLine().trim().toLowerCase();

            String[] parts = command.split("\\s+"); // Split input by whitespace
            if (parts.length == 0) {
                System.out.println("Invalid command, type help to get started.");
                System.out.print("[LOGGED OUT] >>> ");
                continue; // Prompt user again
            }

            switch (parts[0].toLowerCase()) {
                case "help":
                    System.out.println("register <USERNAME> <PASSWORD> <EMAIL> - to create an account\n" +
                            "login <USERNAME> <PASSWORD> - to play chess\n" +
                            "quit - to leave UI\n" +
                            "help - list possible commands\n");
                    System.out.print("[LOGGED OUT] >>> ");
                    break;
                case "quit":
                    running = false;
                    break;
                case "register":
                    if (parts.length != 4) {
                        System.out.println("Invalid format. Correct format: register <USERNAME> <PASSWORD> <EMAIL>\n");
                        System.out.print("[LOGGED OUT] >>> ");
                        continue; // Prompt user again
                    }
                    String registerUsername = parts[1];
                    String registerPassword = parts[2];
                    String registerEmail = parts[3];
                    var authdata = serverFacade.registerUser(registerUsername, registerPassword, registerEmail);

                    if (!authdata.toString().contains("Error")) {
                        System.out.println("User registered: " + authdata + "\n");
                        System.out.print("[LOGGED OUT] >>> ");
                    }
                    else {
                        System.out.println("Error registering user: " + authdata + "\n");
                        System.out.print("[LOGGED OUT] >>> ");
                    }
                    break;
                case "login":
                    if (parts.length != 3) {
                        System.out.println("Invalid format. Correct format: login <USERNAME> <PASSWORD>\n");
                        System.out.print("[LOGGED OUT] >>> ");
                        continue; // Prompt user again
                    }
                    String loginUsername = parts[1];
                    String loginPassword = parts[2];
                    var loginAuth = serverFacade.loginUser(loginUsername, loginPassword);

                    if (!loginAuth.toString().contains("Error")) {
                        System.out.println("Logged in, authToken: " + loginAuth + "\n");
                        authToken = loginAuth;
                        postLoginUI();
                    }
                    else {
                        System.out.println("Error logging in user: " + loginAuth + "\n");
                        System.out.print("[LOGGED OUT] >>> ");
                    }
                    break;

                default:
                    System.out.println("Invalid command, type help to get started.\n");
                    System.out.print("[LOGGED OUT] >>> ");
            }
        }
    }

    public void postLoginUI() throws IOException {
        System.out.print("[LOGGED IN] >>> ");

        // Trim whitespace and convert to lowercase
        boolean running = true;

        while (running) {
            String command = scanner.nextLine().trim().toLowerCase();

            String[] parts = command.split("\\s+"); // Split input by whitespace
            if (parts.length == 0) {
                System.out.println("Invalid command, type help to get started.");
                System.out.print("[LOGGED IN] >>> ");
                continue; // Prompt user again
            }

            switch (parts[0].toLowerCase()) {
                case "help":
                    System.out.println("create <GAMENAME> - to create a game\n" +
                            "list - to list games\n" +
                            "join <GAMEID> <WHITE|BLACK|empty> - to join game\n" +
                            "observe <GAMEID> - to observe game\n" +
                            "logout - to logout\n" +
                            "quit - to stop playing chess\n" +
                            "help - list possible commands\n");
                    System.out.print("[LOGGED IN] >>> ");
                    break;
                case "create":
                    if (parts.length != 2) {
                        System.out.println("Invalid format. Correct format: create <GAMENAME>\n");
                        System.out.print("[LOGGED IN] >>> ");
                        continue; // Prompt user again
                    }
                    String gameName = parts[1];
                    var create = serverFacade.createGame(authToken, gameName);
                    if (!create.toString().contains("Error")) {
                        System.out.println("Game created, gameID: " + create + "\n");
                        System.out.print("[LOGGED IN] >>> ");
                    }
                    else {
                        System.out.println("Error creating game" + create + "\n");
                        System.out.print("[LOGGED IN] >>> ");
                    }
                    break;
                case "list":
                    var games = serverFacade.listGames(authToken);
                    if (!games.toString().contains("Error")) {
                        System.out.println("Games: \n");
                        System.out.println(games);
                        System.out.print("[LOGGED IN] >>> ");
                    }
                    else {
                        System.out.println("No games available\n");
                        System.out.print("[LOGGED IN] >>> ");
                    }
                    break;
                case "join":
                    if (parts.length != 3) {
                        System.out.println("Invalid format. Correct format: join <GAMEID> <WHITE|BLACK|empty>\n");
                        System.out.print("[LOGGED IN] >>> ");
                        continue; // Prompt user again
                    }
                    String gameID = parts[1];
                    String playerColor = parts[2];

                    var joined = serverFacade.joinGame(authToken, playerColor, gameID);
                    if (!joined.toString().contains("Error")) {
                        System.out.println("Game Joined");
                        ChessBoardUI.main();
                    }
                    else {
                        System.out.println("Error joining game: " + joined + "\n");
                        System.out.print("[LOGGED IN] >>> ");
                    }
                    break;
                case "observe":
                    if (parts.length != 2) {
                        System.out.println("Invalid format. Correct format: observe <GAMEID>\n");
                        System.out.print("[LOGGED IN] >>> ");
                        continue; // Prompt user again
                    }
                    String observeGameID = parts[1];

                    var observerJoined = serverFacade.joinGame(authToken, "", observeGameID);
                    if (!observerJoined.toString().contains("Error")) {
                        System.out.println("Game Observer Joined");
                        ChessBoardUI.main();
                    }
                    else {
                        System.out.println("Error joining observer game" + observerJoined + "\n");
                        System.out.print("[LOGGED IN] >>> ");
                    }
                    break;
                case "logout":
                    running = false;
                    break;
                case "quit":
                    break;
                default:
                    System.out.println("Invalid command, type help to get started.\n");
                    System.out.print("[LOGGED IN] >>> ");
            }
        }
    }
}
