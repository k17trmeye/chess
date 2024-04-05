package server.websocket;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.Services;
import webSocketMessages.serverMessages.ServerMessage;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

import webSocketMessages.userCommands.UserGameCommand;

@WebSocket
public class WebSocketHandler {
    private final ConnectionManager connections = new ConnectionManager();
    private Services services = null;

    public void setServices(Services services) {
        this.services = services;
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException, DataAccessException, InvalidMoveException {
        UserGameCommand action = new Gson().fromJson(message, UserGameCommand.class);
        if (action.getCommandType() == null) {
            return;
        }
        switch (action.getCommandType()) {
            case JOIN_PLAYER -> joinPlayer(action, session);
            case JOIN_OBSERVER -> joinObserver(action, session);
            case MAKE_MOVE -> makeMove(action, session);
            case LEAVE -> leavePlayer(action);
            case RESIGN -> forfeitMatch(action);
        }
    }

    private void joinPlayer(UserGameCommand action, Session session) throws IOException, DataAccessException {
        String authToken = action.getAuthString();
        String playerName = services.getUsername(authToken);
        ChessGame.TeamColor teamColor = action.getPlayerColor();
        ChessGame.TeamColor testColor = null;
        if (teamColor.toString().toLowerCase().contains("black")) {
            testColor = ChessGame.TeamColor.WHITE;
        } else {
            testColor = ChessGame.TeamColor.BLACK;
        }
        Integer gameID = action.getGameID();

        ChessGame chessGame = services.getChessGame(gameID);
        connections.add(gameID, session, playerName, chessGame);

        if (playerName == null) {
            String zeroMessage = "Error: Invalid authToken";
            var zero = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            zero.setErrorMessage(zeroMessage);
            connections.sendToUser(gameID, zero, playerName, session);
            connections.remove(gameID, playerName);
            return;
        }
        if (authToken == null) {
            String oneMessage = "Error: Invalid authToken";
            var one = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            one.setErrorMessage(oneMessage);
            connections.sendToUser(gameID, one, playerName, session);
            connections.remove(gameID, playerName);
            return;
        }
        if (gameID == 0) {
            String twoMessage = "Error: Invalid gameID";
            var two = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            two.setErrorMessage(twoMessage);
            connections.sendToUser(gameID, two, playerName, session);
            connections.remove(gameID, playerName);
            return;
        }
        if (services.getChessGame(gameID).getTeamTurn() == null) {
            String sixMessage = "Error: Player Resigned";
            var six = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            six.setErrorMessage(sixMessage);
            connections.sendToUser(gameID, six, playerName, session);
            connections.remove(gameID, playerName);
            return;
        }
        if (services.getChessGame(gameID).getTeamTurn() == null) {
            String sixMessage = "Error: Player Resigned";
            var six = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            six.setErrorMessage(sixMessage);
            connections.sendToUser(gameID, six, playerName, session);
            connections.remove(gameID, playerName);
            return;
        }
        if (services.getToken(playerName) == authToken) {
            String threeMessage = "Error: Invalid authToken";
            var three = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            three.setErrorMessage(threeMessage);
            connections.sendToUser(gameID, three, playerName, session);
            connections.remove(gameID, playerName);
            return;
        }
        if (Objects.equals(services.getPlayerColor(testColor.toString(), gameID), playerName)) {
            String fourMessage = "Error: Wrong playerColor";
            var four = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            four.setErrorMessage(fourMessage);
            connections.sendToUser(gameID, four, playerName, session);
            connections.remove(gameID, playerName);
            return;
        }

        if (services.getPlayerColor(teamColor.toString(), gameID) != null) {
            String fourMessage = "Error: Invalid playerColor";
            var four = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            four.setErrorMessage(fourMessage);
            connections.sendToUser(gameID, four, playerName, session);
            connections.remove(gameID, playerName);
            return;
        }
        if (!Objects.equals(services.getGame(gameID), gameID)) {
            String fiveMessage = "Error: Invalid gameID";
            var five = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            five.setErrorMessage(fiveMessage);
            connections.sendToUser(gameID, five, playerName, session);
            connections.remove(gameID, playerName);
            return;
        }

        services.joinGame(playerName, teamColor.toString(), gameID);
        var newNotification = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
        newNotification.setMessage("loading game");
        newNotification.setGame(chessGame);
        connections.sendToUser(gameID, newNotification, playerName, session);

        String message = String.format("%s is in the game as %s, %s teams turn", playerName, action.getPlayerColor(), chessGame.getTeamTurn());
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        notification.setMessage(message);
        connections.broadcast(gameID, notification, playerName, false);



        services.returnChessGame(gameID, chessGame);
    }

    private void leavePlayer(UserGameCommand action) throws IOException, DataAccessException {
        String playerName = action.getUser();
        String authToken = action.getAuthString();
        Integer gameID = action.getGameID();
        connections.remove(gameID, playerName);
        String message = String.format("%s has left the game", playerName);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        notification.setMessage(message);
        connections.broadcast(gameID, notification, authToken, false);
    }

    private void forfeitMatch(UserGameCommand action) throws IOException, DataAccessException {
        String playerName = action.getUser();
        String authToken = action.getAuthString();
        Integer gameID = action.getGameID();
        connections.remove(gameID, playerName);
        System.out.println("Turn before: " + services.getChessGame(gameID).getTeamTurn());
        services.getChessGame(gameID).setTeamTurn(ChessGame.TeamColor.DONE);
        System.out.println("Turn after: " + services.getChessGame(gameID).getTeamTurn());
        String message = String.format("%s has forfeited the match", playerName);
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        notification.setMessage(message);
        connections.broadcast(gameID, notification, authToken, false);
    }

    private void makeMove(UserGameCommand action, Session session) throws IOException, DataAccessException, InvalidMoveException {
        ChessGame.TeamColor playerColor = null;
        String playerName = action.getUser();
        String authToken = action.getAuthString();
        Integer gameID = action.getGameID();
        ChessMove move = action.getMove();
        String testUsername = services.getUsername(authToken);
        ChessGame.TeamColor currColor = services.getChessGame(gameID).getTeamTurn();
        if (services.getPlayerColor("WHITE", gameID).toLowerCase().contains(testUsername)) {
            playerColor = ChessGame.TeamColor.WHITE;
        } else if (services.getPlayerColor("BLACK", gameID).toLowerCase().contains(testUsername)) {
            playerColor = ChessGame.TeamColor.BLACK;
        }
        if (currColor == playerColor) {
            Collection<ChessMove> moves = services.getChessGame(gameID).validMoves(move.getStartPosition());
            for (ChessMove eachMove : moves) {
                if (eachMove.getStartPosition().getColumn() == move.getStartPosition().getColumn() && eachMove.getStartPosition().getRow() == move.getStartPosition().getRow()) {
                    if (eachMove.getEndPosition().getColumn() == move.getEndPosition().getColumn() && eachMove.getEndPosition().getRow() == move.getEndPosition().getRow()) {
                        ChessGame chessGame = connections.makeMove(gameID, playerName, move);

                        String message = "Successful move";
                        var notification = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
                        notification.setMessage(message);
                        notification.setGame(chessGame);
                        connections.broadcast(gameID, notification, testUsername, true);

                        String newMessage = String.format("%s has made move", playerName);;
                        var newNotification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
                        newNotification.setMessage(newMessage);
                        newNotification.setGame(chessGame);
                        connections.broadcast(gameID, newNotification, testUsername, false);

                        services.returnChessGame(gameID, chessGame);
                        return;
                    }
                }
            }
            String oneMessage = "Error: Not a valid move";
            var one = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            one.setErrorMessage(oneMessage);
            connections.sendToUser(gameID, one, playerName, session);
        } else {
            String twoMessage = "Error: Not your turn, waiting for opposing player";
            var two = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            two.setErrorMessage(twoMessage);
            connections.sendToUser(gameID, two, playerName, session);
        }

    }

    private void joinObserver(UserGameCommand action, Session session) throws IOException, DataAccessException {

        String authToken = action.getAuthString();
        String playerName = services.getUsername(authToken);
        Integer gameID = action.getGameID();

        ChessGame chessGame = services.getChessGame(gameID);
        connections.add(gameID, session, playerName, chessGame);

        if (chessGame == null) {
            String oneMessage = "Error: Invalid gameID";
            var one = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            one.setErrorMessage(oneMessage);
            connections.sendToUser(gameID, one, playerName, session);
            connections.remove(gameID, playerName);
            return;
        }
        if (playerName == null) {
            String twoMessage = "Error: Invalid authToken";
            var two = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            two.setErrorMessage(twoMessage);
            connections.sendToUser(gameID, two, playerName, session);
            connections.remove(gameID, playerName);
            return;
        }

        var newNotification = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
        newNotification.setMessage("load game");
        newNotification.setGame(chessGame);
        connections.sendToUser(gameID, newNotification, playerName, session);

        String message = String.format("%s is in the game as an observer, %s teams turn", playerName, chessGame.getTeamTurn());
        var notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
        notification.setMessage(message);
        connections.broadcast(gameID, notification, playerName, false);



        services.returnChessGame(gameID, chessGame);
    }


}
