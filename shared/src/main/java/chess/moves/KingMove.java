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
        ArrayList<ChessMove> kingMoves = new ArrayList<>();

        int myRow = myPosition.getRow();
        int myCol = myPosition.getColumn();

        addMoveIfValid(board, myRow, myCol + 1, myRow, myCol, teamColor, myPosition, kingMoves); // Move up once
        addMoveIfValid(board, myRow, myCol - 1, myRow, myCol, teamColor, myPosition, kingMoves); // Move down once
        addMoveIfValid(board, myRow + 1, myCol, myRow, myCol, teamColor, myPosition, kingMoves); // Move right once
        addMoveIfValid(board, myRow - 1, myCol, myRow, myCol, teamColor, myPosition, kingMoves); // Move left once
        addMoveIfValid(board, myRow + 1, myCol + 1, myRow, myCol, teamColor, myPosition, kingMoves); // Move up left once
        addMoveIfValid(board, myRow - 1, myCol + 1, myRow, myCol, teamColor, myPosition, kingMoves); // Move down left once
        addMoveIfValid(board, myRow + 1, myCol - 1, myRow, myCol, teamColor, myPosition, kingMoves); // Move up right once
        addMoveIfValid(board, myRow - 1, myCol - 1, myRow, myCol, teamColor, myPosition, kingMoves); // Move down right once

        return kingMoves;
    }

    private void addMoveIfValid(ChessBoard board, int newRow, int newCol, int myRow, int myCol,
                                ChessGame.TeamColor teamColor, ChessPosition myPosition,
                                ArrayList<ChessMove> kingMoves) {
        if (newRow >= 1 && newRow <= 8 && newCol >= 1 && newCol <= 8 && (newRow != myRow || newCol != myCol)) {
            ChessPosition newPosition = new ChessPosition(newRow, newCol);
            ChessPiece pieceInter = board.getPiece(newPosition);

            if (pieceInter == null || teamColor != pieceInter.getTeamColor()) {
                kingMoves.add(new ChessMove(myPosition, newPosition, null));
            }
        }
    }
}
