package ui;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import com.google.gson.Gson;

public class Repl {
    private final ServerFacade serverFacade;
    Scanner scanner = new Scanner(System.in);
    private static String authToken;
    private static Integer newgameID;
    private static String userName;
    private GamePlay game;
    private String currPlayerColor;

    public Repl(Integer port) throws IOException {
        serverFacade = new ServerFacade(port);
    }

    public void preLoginUI() throws Exception {
        System.out.println(EscapeSequences.BLACK_KING + "Welcome to 240 chess. Type help to get started." + EscapeSequences.WHITE_KING);
        System.out.print("[LOGGED OUT] >>> ");

         // Trim whitespace and convert to lowercase
        boolean running = true;

        while (running) {
            String command = scanner.nextLine().trim();

            String[] parts = command.split("\\s+"); // Split input by whitespace
            if (parts.length == 0) {
                System.out.println("Invalid command, type help to get started.");
                System.out.print("[LOGGED OUT] >>> ");
                continue; // Prompt user again
            }

            switch (parts[0]) {
                case "help":
                    System.out.println("register <USERNAME> <PASSWORD> <EMAIL> - to create an account\n" +
                            "login <USERNAME> <PASSWORD> - to play chess\n" +
                            "quit - to leave UI\n" +
                            "clear - to clear userDB\n" +
                            "help - list possible commands\n");
                    System.out.print("[LOGGED OUT] >>> ");
                    break;
                case "quit":
                    System.out.println("Closing UI");
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
                        System.out.println("User registered, logging in\n");
                        var registerAuth = serverFacade.loginUser(registerUsername, registerPassword);
                        authToken = registerAuth;
                        userName = registerUsername;
                        postLoginUI();
                        userName = "";
                        System.out.print("[LOGGED OUT] >>> ");
                    }
                    else {
                        System.out.println("Error registering user\n");
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
                    userName = loginUsername;
                    String loginPassword = parts[2];
                    var loginAuth = serverFacade.loginUser(loginUsername, loginPassword);

                    if (!loginAuth.toString().contains("Error")) {
                        System.out.println("Logging in\n");
                        authToken = loginAuth;
                        userName = loginUsername;
                        postLoginUI();
                        System.out.print("[LOGGED OUT] >>> ");
                    }
                    else {
                        System.out.println("Error logging in user\n");
                        System.out.print("[LOGGED OUT] >>> ");
                    }
                    break;
                case "clear":
                    serverFacade.clear();
                    System.out.println("Clearing All DataBases\n");
                    System.out.print("[LOGGED OUT] >>> ");
                    continue;
                default:
                    System.out.println("Invalid command, type help to get started.\n");
                    System.out.print("[LOGGED OUT] >>> ");
            }
        }
    }

    public void postLoginUI() throws Exception {
        System.out.print("[LOGGED IN] >>> ");

        // Trim whitespace and convert to lowercase
        boolean running = true;

        while (running) {
            String command = scanner.nextLine().trim();

            String[] parts = command.split("\\s+"); // Split input by whitespace
            if (parts.length == 0) {
                System.out.println("Invalid command, type help to get started.");
                System.out.print("[LOGGED IN] >>> ");
                continue; // Prompt user again
            }

            switch (parts[0]) {
                case "help":
                    System.out.println("create <GAMENAME> - to create a game\n" +
                            "list - to list games\n" +
                            "join <GAMEID> <WHITE|BLACK> - to join game\n" +
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
                        int gameID = Integer.parseInt(create);

                        System.out.println("Game created, gameID: " + gameID + "\n");
                        System.out.print("[LOGGED IN] >>> ");
                    }
                    else {
                        System.out.println("Error creating game\n");
                        System.out.print("[LOGGED IN] >>> ");
                    }
                    break;
                case "list":
                    String games = serverFacade.listGames(authToken).toString();
                    if (!games.toString().contains("Error")) {
                        Gson gson = new Gson();
                        GameList gameList = gson.fromJson(games, GameList.class);
                        int numGameIDs = gameList.games.length;
                        if (numGameIDs == 0) {
                            System.out.println("No games created \n");
                        } else {
                            System.out.println("Games: ");
                            int gamesStartIndex = games.indexOf("[");
                            int gamesEndIndex = games.indexOf("]", gamesStartIndex);
                            String gamesArrayString = games.substring(gamesStartIndex, gamesEndIndex + 1);
                            String[] gameObjects = gamesArrayString.split("\\},\\{");

                            for (int i = 0; i < gameObjects.length; i++) {
                                String gameObject = gameObjects[i];
                                gameObject = gameObject.replace("{", "").replace("}", "");
                                String[] keyValuePairs = gameObject.split(",");
                                int gameID = -1;
                                String newGame = "";
                                String blackPlayer = null;
                                String whitePlayer = null;
                                for (String pair : keyValuePairs) {
                                    String[] newparts = pair.split(":");
                                    String key = newparts[0].trim();
                                    String value = newparts[1].trim();
                                    if (key.contains("gameID")) {
                                        gameID = Integer.parseInt(value);
                                    } else if (key.contains("gameName")) {
                                        if (i == gameObjects.length - 1) {
                                            newGame = value.substring(1, value.length() - 2);
                                        } else {
                                            newGame = value.substring(1, value.length() - 1);
                                        }
                                    } else if (key.contains("blackUsername")) {
                                        blackPlayer = value.substring(1, value.length() - 1); // Remove surrounding quotes
                                    } else if (key.contains("whiteUsername")) {
                                        whitePlayer = value.substring(1, value.length() - 1); // Remove surrounding quotes
                                    }
                                }
                                // Print gameID and gameName
                                System.out.println("\tGame ID: " + gameID);
                                System.out.println("\tGame Name: " + newGame);
                                if (blackPlayer == null) {
                                    System.out.println("\tBlack Player: none");
                                } else {
                                    System.out.println("\tBlack Player: " + blackPlayer);
                                }
                                if (whitePlayer == null) {
                                    System.out.println("\tWhite Player: none");
                                } else {
                                    System.out.println("\tWhite Player: " + whitePlayer);
                                }
                                System.out.println();
                            }
                        }
                        System.out.print("[LOGGED IN] >>> ");
                    }
                    else {
                        System.out.println("No games available\n");
                        System.out.print("[LOGGED IN] >>> ");
                    }
                    break;
                case "join":
                    if (parts.length != 3) {
                        System.out.println("Invalid format. Correct format: join <GAMEID> <WHITE|BLACK>\n");
                        System.out.print("[LOGGED IN] >>> ");
                        continue; // Prompt user again
                    }
                    String gameID = parts[1];
                    String playerColor = parts[2];
                    Integer newGame = Integer.parseInt(gameID);
                    newgameID = newGame;


                    var joined = serverFacade.joinGame(authToken, playerColor, newGame.toString());
                    if (!joined.toString().contains("Error")) {
                        if (playerColor.toLowerCase().equals("black")) {
                            System.out.println("Game Joined\n");
                            currPlayerColor = playerColor.toLowerCase();
                            game = new GamePlay();
                            game.joinGame(authToken, currPlayerColor, newgameID, userName);

                            GameUI();

                            System.out.print("[LOGGED IN] >>> ");
                            continue;
                        } else if (playerColor.toLowerCase().equals("white")) {
                            System.out.println("Game Joined\n");
                            currPlayerColor = playerColor.toLowerCase();
                            game = new GamePlay();
                            game.joinGame(authToken, currPlayerColor, newgameID, userName);

                            GameUI();

                            System.out.print("[LOGGED IN] >>> ");
                            continue;
                        } else {
                            System.out.println("Invalid format. Correct format: join <GAMEID> <WHITE|BLACK>\n");
                            System.out.print("[LOGGED IN] >>> ");
                            continue;
                        }
                    }
                    else {
                        System.out.println("Error joining game\n");
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
                    Integer observeNewGame = 0;
                    try {
                        observeNewGame = Integer.parseInt(observeGameID);
                        // Process observeNewGame here
                    } catch (NumberFormatException e) {

                    }
                    if (observeNewGame == 0) {
                        System.out.println("Invalid format. Correct format: observe <GAMEID>\n");
                        System.out.print("[LOGGED IN] >>> ");
                        continue;
                    }

                    var observerJoined = serverFacade.joinGame(authToken, "", observeGameID);
                    if (!observerJoined.toString().contains("Error")) {
                        System.out.println("Game Observer Joined\n");
                        currPlayerColor = null;
                        newgameID = observeNewGame;
                        game = new GamePlay();
                        game.joinObserver(authToken, currPlayerColor, newgameID, userName);
                        GameUI();

                        System.out.print("[LOGGED IN] >>> ");
                        continue;
                    } else {
                        System.out.println("Error joining observer\n");
                        System.out.print("[LOGGED IN] >>> ");
                    }
                    break;
                case "logout":
                    running = false;
                    System.out.println();
                    break;
                case "quit":
                    System.out.println("Closing UI");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid command, type help to get started.\n");
                    System.out.print("[LOGGED IN] >>> ");
            }
        }
    }


    public void GameUI() throws IOException, Exception {

        // Trim whitespace and convert to lowercase
        boolean running = true;

        while (running) {

            if (game.checkGame()) {
                game.resignPlayer(authToken, newgameID, userName);
                System.out.println("You lost, ending game\n");
                running = false;
                continue;
            }

            String command = scanner.nextLine().trim();

            String[] parts = command.split("\\s+"); // Split input by whitespace
            if (parts.length == 0) {
                System.out.println("Invalid command, type help to get started.");
                System.out.print("[GAMEPLAY] >>> ");
                continue; // Prompt user again
            }

            switch (parts[0]) {
                case "help":
                    System.out.println("redraw - to redraw the chessboard\n" +
                            "leave - to leave the game\n" +
                            "move <currRow currCol newRow newCol> - to make a move, example: 'move 7a5a'\n" +
                            "resign - to forfeit the game\n" +
                            "show <currRow currCol> - to show legal moves\n" +
                            "help - list possible commands\n");
                    System.out.print("[GAMEPLAY] >>> ");
                    break;
                case "redraw":
                    game.redrawBoard();
                    break;
                case "leave":
                    System.out.println("Leaving Gameplay\n");
                    game.leaveGame(authToken, newgameID, userName);
                    running = false;
                    break;
                case "move":
                    if (currPlayerColor == null) {
                        System.out.println("Cannot make move as an observer\n");
                        System.out.print("[GAMEPLAY] >>> ");
                        continue;
                    }
                    if (parts.length != 2) {
                        System.out.println("Invalid format. Correct format: move <currRow currCol newRow newCol>, example: 'move 7a5a'\n");
                        System.out.print("[GAMEPLAY] >>> ");
                        continue; // Prompt user again
                    }
                    String chessMove = parts[1];
                    if (chessMove.length() != 4) {
                        System.out.println("Invalid format. Correct format: move <currRow currCol newRow newCol>, example: 'move 7a5a'\n");
                        System.out.print("[GAMEPLAY] >>> ");
                        continue; // Prompt user again
                    }
                    int currRow = 0, currCol = 0, newRow = 0, newCol = 0;

                    for (int i = 0; i < 4; i++) {
                        if (i == 0) {
                            if (Character.isDigit(chessMove.charAt(i))) {
                                currRow = Character.getNumericValue(chessMove.charAt(i));
                            }
                        } else if (i == 1) {
                            currCol = chessMove.charAt(i) - 'a' + 1;
                        } else if (i == 2) {
                            if (Character.isDigit(chessMove.charAt(i))) {
                                newRow = Character.getNumericValue(chessMove.charAt(i));
                            }
                        } else if (i == 3) {
                            newCol = chessMove.charAt(i) - 'a' + 1;
                        }
                    }

                    if (newRow == 0 || newCol == 0 || currRow == 0 || currCol == 0) {
                        System.out.println("Invalid row/col. Correct format: move <currRow currCol newRow newCol>\n");
                        System.out.print("[GAMEPLAY] >>> ");
                        continue;
                    }

                    ChessPosition currPos = new ChessPosition(currRow, currCol);
                    ChessPosition newPos = new ChessPosition(newRow, newCol);
                    ChessMove newMove = new ChessMove(currPos, newPos, null);
                    game.makeMove(authToken, newgameID, currPlayerColor, newMove, userName);
                    break;
                case "resign":
                    if (currPlayerColor == null) {
                        System.out.println("Cannot resign as an observer\n");
                        System.out.print("[GAMEPLAY] >>> ");
                        continue;
                    }
                    System.out.println("Resigning Game");
                    game.resignPlayer(authToken, newgameID, userName);
                    running = false;
                    break;
                case "show":
                    if (currPlayerColor == null) {
                        System.out.println("Cannot show potential moves as an observer\n");
                        System.out.print("[GAMEPLAY] >>> ");
                        continue;
                    }
                    if (parts.length != 2) {
                        System.out.println("Invalid format. Correct format: show <currRow currCol>, example: 'show 7a'\n");
                        System.out.print("[GAMEPLAY] >>> ");
                        continue; // Prompt user again
                    }

                    String currPosition = parts[1];
                    if (currPosition.length() != 2) {
                        System.out.println("Invalid format. Correct format: move <currRow currCol newRow newCol>, example: 'move 7a5a'\n");
                        System.out.print("[GAMEPLAY] >>> ");
                        continue; // Prompt user again
                    }
                    int currentRow = 0, currentCol = 0;

                    for (int i = 0; i < 2; i++) {
                        if (i == 0) {
                            if (Character.isDigit(currPosition.charAt(i))) {
                                currentRow = Character.getNumericValue(currPosition.charAt(i));
                            }
                        } else if (i == 1) {
                            currentCol = currPosition.charAt(i) - 'a' + 1;
                        }
                    }

                    if (currentRow == 0 || currentCol == 0) {
                        System.out.println("Invalid row/col. Correct format: move <currRow currCol newRow newCol>\n");
                        System.out.print("[GAMEPLAY] >>> ");
                        continue;
                    }

                    ChessPosition currPiece = new ChessPosition(currentRow, currentCol);

                    game.showMoves(currPiece);

                    System.out.print("\n[GAMEPLAY] >>> ");
                    break;
                default:
                    System.out.println("Invalid command, type help to get started.\n");
                    System.out.print("[GAMEPLAY] >>> ");
            }
        }
    }


    class GameList {
        Game[] games;
    }

    class Game {
        int gameID;
        String gameName;
        String blackPlayerName;
        String whitePlayerName;
    }
}
