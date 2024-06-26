package server.websocket;

import chess.ChessGame;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class Connection {
    public Session session;
    public String playerName;
    public ChessGame chessGame;
    public Integer gameID;
    public boolean observer;

    public Connection(Session session, String playerName, ChessGame chessGame, Integer gameID, boolean observer) {
        this.session = session;
        this.playerName = playerName;
        this.chessGame = chessGame;
        this.gameID = gameID;
        this.observer = observer;
    }

    public void send(String msg) throws IOException {
        session.getRemote().sendString(msg);
    }
}