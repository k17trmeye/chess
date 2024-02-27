package chess.moves;

import chess.*;

import java.util.ArrayList;

public class QueenMove {
    public ArrayList<ChessMove> queenMoves;

    public QueenMove () {
        queenMoves = new ArrayList<>();
    }

    public ArrayList<ChessMove> queenMoveCal(ChessBoard board, Integer row, Integer col,
                                             ChessGame.TeamColor teamColor, ChessPosition myPosition) {
        ArrayList<ChessMove> queenMoves = new ArrayList<>();

        // Define the possible directions for the queen's movement
        int[] rowDirs = { 1, -1, 0, 0, 1, -1, 1, -1 };
        int[] colDirs = { 0, 0, 1, -1, 1, -1, -1, 1 };

        for (int i = 0; i < rowDirs.length; i++) {
            int newRow = row;
            int newCol = col;

            // Move in the specified direction until you can't anymore
            while (true) {
                newRow += rowDirs[i];
                newCol += colDirs[i];

                // Check if the new position is out of the board bounds
                if (newRow < 1 || newRow > 8 || newCol < 1 || newCol > 8) {
                    break;
                }

                ChessPosition newPosition = new ChessPosition(newRow, newCol);
                ChessPiece pieceInter = board.getPiece(newPosition);

                if (pieceInter != null) {
                    // If the piece belongs to the same team, stop
                    if (teamColor == pieceInter.getTeamColor()) {
                        break;
                    } else {
                        // Add the new position as a possible move
                        queenMoves.add(new ChessMove(myPosition, newPosition, null));
                        break;
                    }
                } else {
                    // Add the new position as a possible move
                    queenMoves.add(new ChessMove(myPosition, newPosition, null));
                }
            }
        }

        return queenMoves;
    }

}
