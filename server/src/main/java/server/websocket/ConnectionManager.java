package server.websocket;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ServerMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<Integer, Connection> connections = new ConcurrentHashMap<>();

    public void add(Integer gameID, Session session, String playerName, ChessGame chessGame) {
        var connection = new Connection(session, playerName, chessGame);
        connections.put(gameID, connection);
    }

    public void remove(Integer gameID, String playerName) {
        for (HashMap.Entry<Integer, Connection> entry : connections.entrySet()) {
            Integer newGameID = entry.getKey();
            Connection c = entry.getValue();
            if (c.session.isOpen()) {
                if (newGameID.equals(gameID)) {
                    if (c.playerName.equals(playerName)) {
                        connections.remove(gameID);
                    }
                }
            }
        }
    }

    public void broadcast(Integer gameID, ServerMessage notification, String playerName, boolean allPlayers) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (HashMap.Entry<Integer, Connection> entry : connections.entrySet()) {
            Integer newGameID = entry.getKey();
            Connection c = entry.getValue();
            if (c.session.isOpen()) {
                if (newGameID.equals(gameID)) {
                    if (allPlayers) {
                        String send = new Gson().toJson(notification);
                        System.out.println("Sending to " + c.playerName);
                        System.out.println(notification.getServerMessageType());
                        System.out.println(notification.getMessage() + "\n");
                        c.send(send);
                    } else {
                        if (!c.playerName.equals(playerName)) {
                            String send = new Gson().toJson(notification);
                            System.out.println("Sending to " + c.playerName);
                            System.out.println(notification.getServerMessageType());
                            System.out.println(notification.getMessage() + "\n");
                            c.send(send);
                        }
                    }
                }
            } else {
                removeList.add(c);
            }
        }

        // Clean up any connections that were left open.
        for (var c : removeList) {
            connections.remove(gameID);
        }
    }


    public void sendToUser(Integer gameID, ServerMessage notification, String playerName) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (HashMap.Entry<Integer, Connection> entry : connections.entrySet()) {
            Integer newGameID = entry.getKey();
            Connection c = entry.getValue();
            if (c.session.isOpen()) {
                if (newGameID.equals(gameID)) {
                    if (c.playerName == null && notification.getServerMessageType() == ServerMessage.ServerMessageType.ERROR) {
                        System.out.println("Sending to " + c.playerName);
                        System.out.println(notification.getServerMessageType());
                        System.out.println(notification.getErrorMessage() + "\n");
                        String send = new Gson().toJson(notification);
                        c.send(send);
                    } else if (c.playerName.equals(playerName)) {
                        System.out.println("Sending to " + c.playerName);
                        System.out.println(notification.getServerMessageType());
                        System.out.println(notification.getMessage() + "\n");
                        String send = new Gson().toJson(notification);
                        c.send(send);
                    } else if (notification.getServerMessageType() == ServerMessage.ServerMessageType.ERROR) {
                        System.out.println("Sending to " + c.playerName);
                        System.out.println(notification.getServerMessageType());
                        System.out.println(notification.getErrorMessage() + "\n");
                        String send = new Gson().toJson(notification);
                        c.send(send);
                    }
                } else if (notification.getServerMessageType() == ServerMessage.ServerMessageType.ERROR) {
                    System.out.println("Sending to " + c.playerName);
                    System.out.println(notification.getServerMessageType());
                    System.out.println(notification.getErrorMessage() + "\n");
                    String send = new Gson().toJson(notification);
                    c.send(send);
                }
            } else {
                removeList.add(c);
            }
        }

        // Clean up any connections that were left open.
        for (var c : removeList) {
            connections.remove(gameID);
        }
    }
    public ChessGame makeMove(Integer gameID, String playerName, ChessMove move) throws IOException, InvalidMoveException {
        for (HashMap.Entry<Integer, Connection> entry : connections.entrySet()) {
            Integer newGameID = entry.getKey();
            Connection c = entry.getValue();
            if (c.session.isOpen()) {
                if (newGameID.equals(gameID)) {
                    c.chessGame.makeMove(move);
                    return c.chessGame;
                }
            }
        }
        return null;
    }
}