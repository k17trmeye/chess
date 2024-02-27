package chess.moves;

import chess.*;

import java.util.ArrayList;

public class RookMove {
    public ArrayList<ChessMove> rookMoves;

    public RookMove () {
        rookMoves = new ArrayList<>();
    }

    public ArrayList<ChessMove> RookMoveCal(ChessBoard board, Integer row, Integer col,
                                              ChessGame.TeamColor teamColor, ChessPosition myPosition) {
        ChessPosition newPosition;
        ChessMove newMove;
        Integer newCol = col;
        Integer newRow = row;
        int myRow = myPosition.getRow();
        int myCol = myPosition.getColumn();

        // Move up till you can't anymore
        newCol = myCol;
        newRow = myRow;
        while (true) {
            newCol++;
            if (newCol > 8) {
                break;
            }

            // Creates new position
            newPosition = new ChessPosition(newRow, newCol);

            // Checks to see if another piece is on the new position
            ChessPiece piece_inter = board.getPiece(newPosition);
            if (piece_inter != null) {
                // Check to see team_color
                if (teamColor == piece_inter.getTeamColor()) {
                    break;
                }
                else {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    rookMoves.add(newMove);
                }
                break;
            }
            else {

                // Adds the new position as a possible move
                newMove = new ChessMove(myPosition, newPosition, null);
                rookMoves.add(newMove);
            }
        }

        // Move down till you can't anymore
        newCol = myCol;
        while (true) {
            newCol--;
            if (newCol < 1) {
                break;
            }

            // Creates new position
            newPosition = new ChessPosition(newRow, newCol);

            // Checks to see if another piece is on the new position
            ChessPiece piece_inter = board.getPiece(newPosition);
            if (piece_inter != null) {
                // Check to see team_color
                if (teamColor == piece_inter.getTeamColor()) {
                    break;
                }
                else {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    rookMoves.add(newMove);
                }
                break;
            }
            else {
                // Adds the new position as a possible move
                newMove = new ChessMove(myPosition, newPosition, null);
                rookMoves.add(newMove);
            }
        }

        // Move left till you can't anymore
        newCol = myCol;
        newRow = myRow;
        while (true) {
            newRow--;
            if (newRow < 1) {
                break;
            }

            // Creates new position
            newPosition = new ChessPosition(newRow, newCol);

            // Checks to see if another piece is on the new position
            ChessPiece piece_inter = board.getPiece(newPosition);
            if (piece_inter != null) {
                // Check to see team_color
                if (teamColor == piece_inter.getTeamColor()) {
                    break;
                }
                else {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    rookMoves.add(newMove);
                }
                break;
            }
            else {
                // Adds the new position as a possible move
                newMove = new ChessMove(myPosition, newPosition, null);
                rookMoves.add(newMove);
            }
        }

        // Move down left up till you can't anymore
        newCol = myCol;
        newRow = myRow;
        while (true) {
            newRow++;
            if (newRow > 8) {
                break;
            }

            // Creates new position
            newPosition = new ChessPosition(newRow, newCol);

            // Checks to see if another piece is on the new position
            ChessPiece piece_inter = board.getPiece(newPosition);
            if (piece_inter != null) {
                // Check to see team_color
                if (teamColor == piece_inter.getTeamColor()) {
                    break;
                }
                else {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    rookMoves.add(newMove);
                }
                break;
            }
            else {
                // Adds the new position as a possible move
                newMove = new ChessMove(myPosition, newPosition, null);
                rookMoves.add(newMove);
            }
        }

        return rookMoves;
    }
}
