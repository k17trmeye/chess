package chess.moves;

import chess.*;

import java.util.ArrayList;

public class KnightMove {
    public ArrayList<ChessMove> knightMoves;

    public KnightMove () {
        knightMoves = new ArrayList<>();
    }

    public ArrayList<ChessMove> knightMoveCal(ChessBoard board, Integer row, Integer col,
                                              ChessGame.TeamColor teamColor, ChessPosition myPosition) {
        ArrayList<ChessMove> knightMoves = new ArrayList<>();

        int[] rowOffsets = {2, 2, -2, -2, 1, 1, -1, -1};
        int[] colOffsets = {1, -1, 1, -1, 2, -2, 2, -2};

        for (int i = 0; i < rowOffsets.length; i++) {
            int newRow = row + rowOffsets[i];
            int newCol = col + colOffsets[i];

            if (newRow >= 1 && newRow <= 8 && newCol >= 1 && newCol <= 8) {
                ChessPosition newPosition = new ChessPosition(newRow, newCol);
                ChessPiece pieceInter = board.getPiece(newPosition);

                if (pieceInter == null || teamColor != pieceInter.getTeamColor()) {
                    knightMoves.add(new ChessMove(myPosition, newPosition, null));
                }
            }
        }

        return knightMoves;
    }
}
