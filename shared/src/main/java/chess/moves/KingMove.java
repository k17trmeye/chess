package chess.moves;

import chess.*;

import java.util.ArrayList;

public class KingMove {
    public ArrayList<ChessMove> kingMoves;

    public KingMove () {
        kingMoves = new ArrayList<>();
    }
    
    public ArrayList<ChessMove> kingMoveCal(ChessBoard board, Integer row, Integer col,
                                            ChessGame.TeamColor teamColor, ChessPosition myPosition) {
        ChessPosition newPosition;
        ChessMove newMove;
        Integer newCol = col;
        Integer newRow = row;
        int myRow = myPosition.getRow();
        int myCol = myPosition.getColumn();

        // Move up once
        newCol = myCol + 1;
        newRow = myRow;
        if (newCol > 8) {
            // Do nothing
        }
        else {
            // Creates new position
            newPosition = new ChessPosition(newRow, newCol);

            // Checks to see if another piece is on the new position
            ChessPiece piece_inter = board.getPiece(newPosition);
            if (piece_inter != null) {
                // Check to see team_color
                if (teamColor != piece_inter.getTeamColor()) {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    kingMoves.add(newMove);
                }
            }
            else {
                // Adds the new position as a possible move
                newMove = new ChessMove(myPosition, newPosition, null);
                kingMoves.add(newMove);
            }
        }

        // Move down once
        newRow = myRow;
        newCol = myCol - 1;
        if (newCol < 1) {
            // Do nothing
        }
        else {
            // Creates new position
            newPosition = new ChessPosition(newRow, newCol);

            // Checks to see if another piece is on the new position
            ChessPiece piece_inter = board.getPiece(newPosition);
            if (piece_inter != null) {
                // Check to see team_color
                if (teamColor != piece_inter.getTeamColor()) {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    kingMoves.add(newMove);
                }
            }
            else {
                // Adds the new position as a possible move
                newMove = new ChessMove(myPosition, newPosition, null);
                kingMoves.add(newMove);
            }
        }

        // Move right once
        newCol = myCol;
        newRow = myRow + 1;
        if (newRow > 8) {
            // Do nothing
        }
        else {
            // Creates new position
            newPosition = new ChessPosition(newRow, newCol);

            // Checks to see if another piece is on the new position
            ChessPiece piece_inter = board.getPiece(newPosition);
            if (piece_inter != null) {
                // Check to see team_color
                if (teamColor != piece_inter.getTeamColor()) {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    kingMoves.add(newMove);
                }
            }
            else {
                // Adds the new position as a possible move
                newMove = new ChessMove(myPosition, newPosition, null);
                kingMoves.add(newMove);
            }
        }

        // Move left once
        newCol = myCol;
        newRow = myRow - 1;
        if (newRow < 1) {
            // Do nothing
        }
        else {
            // Creates new position
            newPosition = new ChessPosition(newRow, newCol);

            // Checks to see if another piece is on the new position
            ChessPiece piece_inter = board.getPiece(newPosition);
            if (piece_inter != null) {
                // Check to see team_color
                if (teamColor != piece_inter.getTeamColor()) {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    kingMoves.add(newMove);
                }
            }
            else {
                // Adds the new position as a possible move
                newMove = new ChessMove(myPosition, newPosition, null);
                kingMoves.add(newMove);
            }
        }

        // Move up left once
        newCol = myCol + 1;
        newRow = myRow + 1;
        if ((newCol > 8) || (newRow > 8)) {
            // Do nothing
        }
        else {
            // Creates new position
            newPosition = new ChessPosition(newRow, newCol);

            // Checks to see if another piece is on the new position
            ChessPiece piece_inter = board.getPiece(newPosition);
            if (piece_inter != null) {
                // Check to see team_color
                if (teamColor != piece_inter.getTeamColor()) {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    kingMoves.add(newMove);
                }
            }
            else {
                // Adds the new position as a possible move
                newMove = new ChessMove(myPosition, newPosition, null);
                kingMoves.add(newMove);
            }
        }

        // Move down left once
        newCol = myCol + 1;
        newRow = myRow - 1;
        if ((newCol > 8) || (newRow < 1)) {
            // Do nothing
        }
        else {
            // Creates new position
            newPosition = new ChessPosition(newRow, newCol);

            // Checks to see if another piece is on the new position
            ChessPiece piece_inter = board.getPiece(newPosition);
            if (piece_inter != null) {
                // Check to see team_color
                if (teamColor != piece_inter.getTeamColor()) {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    kingMoves.add(newMove);
                }
            }
            else {
                // Adds the new position as a possible move
                newMove = new ChessMove(myPosition, newPosition, null);
                kingMoves.add(newMove);
            }
        }

        // Move up right once
        newCol = myCol - 1;
        newRow = myRow + 1;
        if ((newCol < 1) || (newRow > 8)) {
            // Do nothing
        }
        else {
            // Creates new position
            newPosition = new ChessPosition(newRow, newCol);

            // Checks to see if another piece is on the new position
            ChessPiece piece_inter = board.getPiece(newPosition);
            if (piece_inter != null) {
                // Check to see team_color
                if (teamColor != piece_inter.getTeamColor()) {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    kingMoves.add(newMove);
                }
            }
            else {
                // Adds the new position as a possible move
                newMove = new ChessMove(myPosition, newPosition, null);
                kingMoves.add(newMove);
            }
        }

        // Move down right once
        newCol = myCol - 1;
        newRow = myRow - 1;
        if ((newCol < 1) || (newRow < 1)) {
            // Do nothing
        }
        else {
            // Creates new position
            newPosition = new ChessPosition(newRow, newCol);

            // Checks to see if another piece is on the new position
            ChessPiece piece_inter = board.getPiece(newPosition);
            if (piece_inter != null) {
                // Check to see team_color
                if (teamColor != piece_inter.getTeamColor()) {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    kingMoves.add(newMove);
                }
            }
            else {
                // Adds the new position as a possible move
                newMove = new ChessMove(myPosition, newPosition, null);
                kingMoves.add(newMove);
            }
        }

        return kingMoves;
    }
}
