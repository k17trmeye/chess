package webSocketMessages.userCommands;

import chess.ChessGame;
import chess.ChessMove;

import java.util.Objects;

/**
 * Represents a command a user can send the server over a websocket
 * 
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class UserGameCommand {

    protected Integer gameID;

    protected ChessGame.TeamColor playerColor;

    protected ChessMove move;

    protected String user;

    public UserGameCommand(String authToken) {
        this.authToken = authToken;
    }

    public enum CommandType {
        JOIN_PLAYER,
        JOIN_OBSERVER,
        MAKE_MOVE,
        LEAVE,
        RESIGN
    }

    protected CommandType commandType;

    protected String authToken;

    public String getAuthString() {
        return authToken;
    }

    public void setCommandType(CommandType type) {
        commandType = type;
    }

    public void setGameID(Integer newgameID) {
        this.gameID = newgameID;
    }

    public void setPlayerColor(String color) {
        if (color == null) {
            this.playerColor = null;
        } else if (color.toLowerCase().contains("black")) {
            this.playerColor = ChessGame.TeamColor.BLACK;
        }
        else if (color.toLowerCase().contains("white")) {
            this.playerColor = ChessGame.TeamColor.WHITE;
        }
    }

    public void setMove (ChessMove newMove) {
        this.move = newMove;
    }

    public ChessMove getMove() {
        return this.move;
    }

    public ChessGame.TeamColor getPlayerColor() {
        return this.playerColor;
    }

    public Integer getGameID() {
        return this.gameID;
    }

    public CommandType getCommandType() {
        return this.commandType;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return this.user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserGameCommand))
            return false;
        UserGameCommand that = (UserGameCommand) o;
        return getCommandType() == that.getCommandType() && Objects.equals(getAuthString(), that.getAuthString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommandType(), getAuthString());
    }
}
