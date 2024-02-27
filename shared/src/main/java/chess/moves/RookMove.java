package chess.moves;

import chess.*;

import java.util.ArrayList;

public class RookMove {
    public ArrayList<ChessMove> rookMoves;

    public RookMove () {
        rookMoves = new ArrayList<>();
    }

    public ArrayList<ChessMove> rookMoveCal(ChessBoard board, Integer row, Integer col,
                                            ChessGame.TeamColor teamColor, ChessPosition myPosition) {
        ArrayList<ChessMove> rookMoves = new ArrayList<>();

        // Define the possible directions for the rook's movement
        int[] rowDirs = { 1, -1, 0, 0 };
        int[] colDirs = { 0, 0, 1, -1 };

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
                        rookMoves.add(new ChessMove(myPosition, newPosition, null));
                        break;
                    }
                } else {
                    // Add the new position as a possible move
                    rookMoves.add(new ChessMove(myPosition, newPosition, null));
                }
            }
        }

        return rookMoves;
    }

}
