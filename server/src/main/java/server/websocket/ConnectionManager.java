package server.websocket;

import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    public final ConcurrentHashMap<Session, Connection> connections = new ConcurrentHashMap<>();

    public void add(Integer gameID, Session session, String playerName, ChessGame chessGame, boolean observer) {
        var connection = new Connection(session, playerName, chessGame, gameID, observer);
        connections.put(session, connection);
    }

    public boolean isObserver(Integer gameID, String playerName) {
        Connection c = null;
        for (ConcurrentHashMap.Entry<Session, Connection> entry : connections.entrySet()) {
            c = entry.getValue();
            if (c == null) {
                return false;
            }
            if (c.session.isOpen()) {
                if (c.gameID.equals(gameID)) {
                    if (c.playerName.equals(playerName)) {
                        return c.observer;
                    }
                }
            }
        }
        return false;
    }

    public void remove(Integer gameID, String playerName, UserGameCommand.CommandType type) {
        Connection c = null;
        for (ConcurrentHashMap.Entry<Session, Connection> entry : connections.entrySet()) {
            c = entry.getValue();
            if (c == null) {
                return;
            }
            if (c.session.isOpen()) {
                if (c.gameID.equals(gameID)) {

                    if (type == UserGameCommand.CommandType.LEAVE) {
                        if (c.playerName.equals(playerName)) {
                            connections.remove(c.session);
                        }

                    } else {
                        c.gameID = 0;
                    }
                }
            }
        }
    }

    public Integer getGameID(String playerName) {
        Connection c = null;
        for (ConcurrentHashMap.Entry<Session, Connection> entry : connections.entrySet()) {
            c = entry.getValue();
            if (c == null) {
                return 0;
            }
            if (c.session.isOpen()) {
                if (c.playerName.equals(playerName)) {
                    return c.gameID;
                }
            }
        }
        return 0;
    }

    public void broadcast(Integer gameID, ServerMessage notification, String playerName, boolean allPlayers) throws IOException {
        var removeList = new ArrayList<Connection>();
        Connection c = null;
        for (ConcurrentHashMap.Entry<Session, Connection> entry : connections.entrySet()) {
            c = entry.getValue();
            if (c == null) {
                return;
            }

            if (c.session.isOpen()) {
                if (c.gameID.equals(gameID)) {
                    if (allPlayers) {
                        String send = new Gson().toJson(notification);
                        c.send(send);
                    } else {
                        if (!c.playerName.equals(playerName)) {
                            String send = new Gson().toJson(notification);
                            c.send(send);
                        }
                    }
                }
            } else {
                removeList.add(c);
            }
        }
    }


    public void sendToUser(Integer gameID, ServerMessage notification, String playerName, Session session) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (HashMap.Entry<Session, Connection> entry : connections.entrySet()) {
            Connection c = entry.getValue();
            if (c.session.isOpen()) {
                if (c.session == session) {
                    if (c.gameID.equals(gameID)) {
                        String send = new Gson().toJson(notification);
                        c.send(send);
                        return;
                    } else if (c.gameID == 0 && notification.getServerMessageType() == ServerMessage.ServerMessageType.ERROR) {
                        String send = new Gson().toJson(notification);
                        c.send(send);
                        return;
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
    public ChessGame makeMove(Integer gameID, String playerName, ChessMove move) throws IOException, InvalidMoveException {
        for (HashMap.Entry<Session, Connection> entry : connections.entrySet()) {
            Connection c = entry.getValue();
            if (c.session.isOpen()) {
                if (c.gameID.equals(gameID)) {
                    c.chessGame.makeMove(move);
                    return c.chessGame;
                }
            }
        }
        return null;
    }
}