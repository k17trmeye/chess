package ui;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.google.gson.Gson;

public class GamePlay extends Endpoint{
    Session session;
    String currColor;
    ChessGame recentGame;

    public GamePlay() throws URISyntaxException, DeploymentException, IOException {
        URI uri = new URI("ws://localhost:8080/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {

            public void onMessage(String message) {
                ServerMessage action = new Gson().fromJson(message, ServerMessage.class);
                switch (action.getServerMessageType()) {
                    case LOAD_GAME -> loadBoard(action);
                    case ERROR -> printError(action);
                    case NOTIFICATION -> notifyPlayer(action);
                }
            }
        });
    }

    public void send(String msg) throws Exception {
        this.session.getBasicRemote().sendText(msg);
    }

    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public void notifyPlayer(ServerMessage action) {
        System.out.println("\nNotification!");
        System.out.println(action.getMessage());
        System.out.println();
        System.out.print("[GAMEPLAY] >>> ");
    }

    public void printError(ServerMessage action) {
        System.out.println("\n" + action.getMessage());
        System.out.println();
        System.out.print("[GAMEPLAY] >>> ");
    }

    public void joinGame(String authToken, String playerColor, Integer gameID, String playerName) throws Exception {
        try {
            this.currColor = playerColor;
            var action = new UserGameCommand(authToken);
            action.setUser(playerName);
            action.setCommandType(UserGameCommand.CommandType.JOIN_PLAYER);
            action.setGameID(gameID);
            action.setPlayerColor(playerColor);
            String send = new Gson().toJson(action);
            this.session.getBasicRemote().sendText(send);

        } catch (IOException ex) {
            throw new Exception();
        }
    }

    public void leaveGame(String authToken, Integer gameID, String playerName) throws Exception {
        try {
            UserGameCommand action = new UserGameCommand(authToken);
            action.setUser(playerName);
            action.setCommandType(UserGameCommand.CommandType.LEAVE);
            action.setGameID(gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));

        } catch (IOException ex) {
            throw new Exception();
        }
        this.session.close();
    }

    public void resignPlayer(String authToken, Integer gameID, String playerName) throws Exception {
        try {
            UserGameCommand action = new UserGameCommand(authToken);
            action.setUser(playerName);
            action.setCommandType(UserGameCommand.CommandType.RESIGN);
            action.setGameID(gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));

        } catch (IOException ex) {
            throw new Exception();
        }
        this.session.close();
    }

    public void makeMove(String authToken, Integer gameID, String playerColor, ChessMove move, String playerName) throws Exception {
        try {
            UserGameCommand action = new UserGameCommand(authToken);
            action.setPlayerColor(playerColor);
            action.setCommandType(UserGameCommand.CommandType.MAKE_MOVE);
            action.setUser(playerName);
            action.setGameID(gameID);
            action.setMove(move);
            this.session.getBasicRemote().sendText(new Gson().toJson(action));

        } catch (IOException ex) {
            throw new Exception();
        }
    }

    public void loadBoard(ServerMessage action) {
        System.out.println();
        ChessGame chessGame = action.getGame();
        recentGame = chessGame;
        if (currColor.equalsIgnoreCase("white")) {
            ChessBoardUIBlack.main(chessGame);
        } else if (currColor.equalsIgnoreCase("black")) {
            ChessBoardUIWhite.main(chessGame);
        } else {
            ChessBoardUIBlack.main(chessGame);
        }

        System.out.print("[GAMEPLAY] >>> ");
    }

    public void redrawBoard() {
        System.out.println();
        if (currColor == null) {
            ChessBoardUIBlack.main(recentGame);
        } else if (currColor.equalsIgnoreCase("white")) {
            ChessBoardUIBlack.main(recentGame);
        } else if (currColor.equalsIgnoreCase("black")) {
            ChessBoardUIWhite.main(recentGame);
        }
        System.out.println();
        System.out.print("[GAMEPLAY] >>> ");
    }

    public void showMoves(ChessPosition pos) {

    }

    public void joinObserver(String authToken, String playerColor, Integer gameID, String playerName) throws Exception {
        try {
            var action = new UserGameCommand(authToken);
            action.setUser(playerName);
            action.setCommandType(UserGameCommand.CommandType.JOIN_OBSERVER);
            action.setGameID(gameID);
            action.setPlayerColor(playerColor);
            String send = new Gson().toJson(action);
            this.session.getBasicRemote().sendText(send);

        } catch (IOException ex) {
            throw new Exception();
        }
    }
}