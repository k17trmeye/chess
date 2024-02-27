package chess.moves;

import chess.*;

import java.util.ArrayList;

public class BishopMove {
    public ArrayList<ChessMove> bishopMoves;

    public BishopMove() {
        bishopMoves = new ArrayList<>();
    }

    public ArrayList<ChessMove> bishopMoveCal(ChessBoard board, Integer row, Integer col,
                                              ChessGame.TeamColor teamColor, ChessPosition myPosition) {
        ArrayList<ChessMove> bishopMoves = new ArrayList<>();
        ChessMove newMove;
        ChessPosition newPosition;

        for (int rowOffset : new int[]{1, -1}) {
            for (int colOffset : new int[]{1, -1}) {
                int newRow = row + rowOffset;
                int newCol = col + colOffset;

                while (newRow >= 1 && newRow <= 8 && newCol >= 1 && newCol <= 8) {
                    newPosition = new ChessPosition(newRow, newCol);
                    ChessPiece pieceInter = board.getPiece(newPosition);

                    if (pieceInter != null) {
                        if (teamColor != pieceInter.getTeamColor()) {
                            newMove = new ChessMove(myPosition, newPosition, null);
                            bishopMoves.add(newMove);
                        }
                        break;
                    } else {
                        newMove = new ChessMove(myPosition, newPosition, null);
                        bishopMoves.add(newMove);
                    }

                    newRow += rowOffset;
                    newCol += colOffset;
                }
            }
        }
        return bishopMoves;
    }
}

