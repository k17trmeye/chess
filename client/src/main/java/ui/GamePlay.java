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
import java.util.*;

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
        System.out.println(action.getMessage());
        System.out.println();
        System.out.print("[GAMEPLAY] >>> ");
    }

    public void printError(ServerMessage action) {
        System.out.println("\n" + action.getErrorMessage());
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
        ChessGame chessGame = action.getGame();
        recentGame = chessGame;
        ChessBoardUIBlack.main(chessGame, currColor);
    }

    public boolean checkGame() {
        if (recentGame == null) {
            return false;
        }
        if (currColor != null) {
            if (currColor.toLowerCase().contains("white")) {
                return recentGame.isInCheck(ChessGame.TeamColor.WHITE);
            } else if (currColor.toLowerCase().contains("black")) {
                return recentGame.isInCheck(ChessGame.TeamColor.BLACK);
            }
        }
        return false;
    }

    public void redrawBoard() {
        System.out.println();
        ChessBoardUIBlack.main(recentGame, currColor);
        System.out.print("[GAMEPLAY] >>> ");
    }

    public void showMoves(ChessPosition pos) {
        Collection<ChessMove> moves = recentGame.validMoves(pos);
        List<int[]> coordinatesList = new ArrayList<>();
        Integer tempRow = null;
        Integer tempCol = null;
        for (ChessMove eachMove : moves) {
            tempRow = eachMove.getEndPosition().getRow();
            tempCol = eachMove.getEndPosition().getColumn();
            coordinatesList.add(new int[]{tempRow, tempCol});
        }

        if (!coordinatesList.isEmpty()) {
            ChessBoardUIBlack.showMoves(recentGame, coordinatesList, currColor);
        } else {
            System.out.println("No moves available");
        }

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
