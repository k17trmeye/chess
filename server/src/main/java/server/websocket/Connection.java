package server.websocket;

import chess.ChessGame;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class Connection {
    public Session session;
    public String playerName;
    public ChessGame chessGame;
    public Integer gameID;

    public Connection(Session session, String playerName, ChessGame chessGame, Integer gameID) {
        this.session = session;
        this.playerName = playerName;
        this.chessGame = chessGame;
        this.gameID = gameID;
    }

    public void send(String msg) throws IOException {
        session.getRemote().sendString(msg);
    }
}