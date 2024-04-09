package ui;

import java.io.IOException;

import java.util.Scanner;

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
            String[] parts = command.split("\\s+");
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
        boolean running = true;
        while (running) {
            String command = scanner.nextLine().trim();
            String[] parts = command.split("\\s+");
            if (parts.length == 0) {
                System.out.println("Invalid command, type help to get started.");
                continue;
            }
            switch (parts[0]) {
                case "help":
                    System.out.println("create <GAMENAME> - to create a game\nlist - to list games\njoin <GAMEID> <WHITE|BLACK> - to join game\nobserve <GAMEID> - to observe game\nlogout - to logout\nquit - to stop playing chess\nhelp - list possible commands\n");
                    break;
                case "create":
                    if (parts.length != 2) { System.out.println("Invalid format. Correct format: create <GAMENAME>"); continue; }
                    String gameName = parts[1];
                    var create = serverFacade.createGame(authToken, gameName);
                    if (!create.toString().contains("Error")) {
                        int gameID = Integer.parseInt(create); System.out.println("Game created, gameID: " + gameID + "\n");
                    } else { System.out.println("Error creating game"); }
                    break;
                case "list":
                    String games = serverFacade.listGames(authToken).toString();
                    if (!games.contains("Error")) {
                        Gson gson = new Gson(); GameList gameList = gson.fromJson(games, GameList.class);
                        if (gameList.games.length == 0) { System.out.println("No games created\n");
                        } else { System.out.println("Games: ");
                            for (String gameObject : games.substring(games.indexOf("[") + 1, games.indexOf("]"))
                                    .split("\\},\\{")) {
                                gameObject = gameObject.replace("{", "").replace("}", "").replace("[", "").replace("]", "");
                                int gameID = -1; String newGame = "", blackPlayer = null, whitePlayer = null; String[] pairs = gameObject.split(",");
                                for (String pair : pairs) {
                                    String[] split = pair.split(":"); String key = split[0].trim(), value = split[1].trim();
                                    if (key.contains("gameID")) gameID = Integer.parseInt(value);
                                    else if (key.contains("gameName")) newGame = value.substring(1, value.length() - 1);
                                    else if (key.contains("blackUsername")) blackPlayer = value.substring(1, value.length() - 1);
                                    else if (key.contains("whiteUsername")) whitePlayer = value.substring(1, value.length() - 1);
                                } System.out.println("\tGame ID: " + gameID + "\n" + "\tGame Name: " + newGame + "\n" + "\tBlack Player: " + (blackPlayer != null ? blackPlayer : "none") + "\n" + "\tWhite Player: " + (whitePlayer != null ? whitePlayer : "none") + "\n");
                            }
                        }
                    } else { System.out.println("No games available"); }
                    break;
                case "join":
                    if (parts.length != 3) { System.out.println("Invalid format. Correct format: join <GAMEID> <WHITE|BLACK>");
                        continue;
                    }
                    String gameID = parts[1], playerColor = parts[2]; Integer newGame = Integer.parseInt(gameID);
                    newgameID = newGame; var joined = serverFacade.joinGame(authToken, playerColor, newGame.toString());
                    if (!joined.toString().contains("Error")) {
                        if (playerColor.toLowerCase().contains("black") || playerColor.toLowerCase().contains("white")) {
                            System.out.println("Game Joined"); currPlayerColor = playerColor.toLowerCase(); game = new GamePlay();
                            game.joinGame(authToken, currPlayerColor, newgameID, userName); gameUI();
                        } else { System.out.println("Invalid format. Correct format: join <GAMEID> <WHITE|BLACK>"); }
                    } else { System.out.println("Error joining game"); }
                    break;
                case "observe":
                    if (parts.length != 2) { System.out.println("Invalid format. Correct format: observe <GAMEID>"); continue; }
                    String observeGameID = parts[1];
                    int observeNewGame = Integer.parseInt(observeGameID);
                    if (observeNewGame == 0) { System.out.println("Invalid format. Correct format: observe <GAMEID>"); continue; }
                    var observerJoined = serverFacade.joinGame(authToken, "", observeGameID);
                    if (!observerJoined.toString().contains("Error")) {
                        System.out.println("Game Observer Joined"); currPlayerColor = null; newgameID = observeNewGame; game = new GamePlay(); game.joinObserver(authToken, currPlayerColor, newgameID, userName);
                        gameUI();
                    } else { System.out.println("Error joining observer"); }
                    break;
                case "logout":
                    running = false; System.out.println(); return;
                case "quit":
                    System.out.println("Closing UI"); System.exit(0); return;
                default: System.out.println("Invalid command, type help to get started.");
            } System.out.print("[LOGGED IN] >>> ");
        }
    }



    public void gameUI() throws IOException, Exception {
        boolean running = true;
        while (running) {
            String command = scanner.nextLine().trim();
            if (game.gameOver()) {
                return;
            }
            String[] parts = command.split("\\s+");
            if (parts.length == 0) {
                System.out.println("Invalid command, type help to get started.");
                System.out.print("[GAMEPLAY] >>> ");
                continue;
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
                    if (parts.length != 2 || parts[1].length() != 4) {
                        System.out.println("Invalid format. Correct format: move <currRow currCol newRow newCol>, example: 'move 7a5a'\n");
                        System.out.print("[GAMEPLAY] >>> ");
                        continue; // Prompt user again
                    }
                    int currRow = parts[1].charAt(0) - '0', currCol = parts[1].charAt(1) - 'a' + 1,
                            newRow = parts[1].charAt(2) - '0', newCol = parts[1].charAt(3) - 'a' + 1;
                    if (currRow < 1 || currRow > 8 || currCol < 1 || currCol > 8 || newRow < 1 || newRow > 8 || newCol < 1 || newCol > 8) {
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
                    if (parts.length != 2 || parts[1].length() != 2) {
                        System.out.println("Invalid format. Correct format: show <currRow currCol>, example: 'show 7a'\n");
                        System.out.print("[GAMEPLAY] >>> ");
                        continue; // Prompt user again
                    }
                    int currentRow = parts[1].charAt(0) - '0', currentCol = parts[1].charAt(1) - 'a' + 1;
                    if (currentRow < 1 || currentRow > 8 || currentCol < 1 || currentCol > 8) {
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
