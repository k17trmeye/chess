package chess.moves;

import chess.*;

import java.util.ArrayList;

public class BishopMove {
    public ArrayList<ChessMove> bishopMoves;

    public BishopMove () {
        bishopMoves = new ArrayList<>();
    }

    public ArrayList<ChessMove> BishopMoveCal(ChessBoard board, Integer row, Integer col,
                                              ChessGame.TeamColor teamColor, ChessPosition myPosition) {
        ChessPosition newPosition;
        ChessMove newMove;
        Integer newCol = col;
        Integer newRow = row;
        while (true) {
            newCol++;
            newRow++;
            if ((newCol > 8) || (newRow > 8)) {
                break;
            }
            newPosition = new ChessPosition(newRow, newCol);
            ChessPiece pieceInter = board.getPiece(newPosition);
            if (pieceInter != null) {
                if (teamColor == pieceInter.getTeamColor()) {
                    break;
                }
                else {
                    newMove = new ChessMove(myPosition, newPosition, null);
                    bishopMoves.add(newMove);
                }
                break;
            }
            else {
                newMove = new ChessMove(myPosition, newPosition, null);
                bishopMoves.add(newMove);
            }
        }
        newCol = col;
        newRow = row;
        while (true) {
            newCol++;
            newRow--;
            if ((newCol > 8) || (newRow < 1)) {
                break;
            }
            newPosition = new ChessPosition(newRow, newCol);
            ChessPiece pieceInter = board.getPiece(newPosition);
            if (pieceInter != null) {
                if (teamColor == pieceInter.getTeamColor()) {
                    break;
                }
                else {
                    newMove = new ChessMove(myPosition, newPosition, null);
                    bishopMoves.add(newMove);
                }
                break;
            }
            else {
                newMove = new ChessMove(myPosition, newPosition, null);
                bishopMoves.add(newMove);
            }
        }
        newCol = col;
        newRow = row;
        while (true) {
            newCol--;
            newRow--;
            if ((newCol < 1) || (newRow < 1)) {
                break;
            }
            newPosition = new ChessPosition(newRow, newCol);
            ChessPiece pieceInter = board.getPiece(newPosition);
            if (pieceInter != null) {
                if (teamColor == pieceInter.getTeamColor()) {
                    break;
                }
                else {
                    newMove = new ChessMove(myPosition, newPosition, null);
                    bishopMoves.add(newMove);
                }
                break;
            }
            else {
                newMove = new ChessMove(myPosition, newPosition, null);
                bishopMoves.add(newMove);
            }
        }
        newCol = col;
        newRow = row;
        while (true) {
            newCol--;
            newRow++;
            if ((newCol < 1) || (newRow > 8)) {
                break;
            }
            newPosition = new ChessPosition(newRow, newCol);
            ChessPiece pieceInter = board.getPiece(newPosition);
            if (pieceInter != null) {
                if (teamColor == pieceInter.getTeamColor()) {
                    break;
                }
                else {
                    newMove = new ChessMove(myPosition, newPosition, null);
                    bishopMoves.add(newMove);
                }
                break;
            }
            else {
                newMove = new ChessMove(myPosition, newPosition, null);
                bishopMoves.add(newMove);
            }
        }
        return bishopMoves;
    }
}
