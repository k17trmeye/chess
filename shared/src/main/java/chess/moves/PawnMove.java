package chess.moves;

import chess.*;

import java.util.ArrayList;

public class PawnMove {
    public ArrayList<ChessMove> pawnMoves;

    public PawnMove () {
        pawnMoves = new ArrayList<>();
    }

    public ArrayList<ChessMove> pawnMoveCal(ChessBoard board, Integer row, Integer col,
                                            ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType,
                                            ChessPosition myPosition) {
        ArrayList<ChessMove> pawnMoves = new ArrayList<>();

        int myRow = myPosition.getRow();
        int myCol = myPosition.getColumn();

        // Calculate the direction of movement based on the team color
        int direction = (teamColor == ChessGame.TeamColor.WHITE) ? 1 : -1;

        // Move forward once
        int newRow = myRow + direction;
        if (newRow >= 1 && newRow <= 8) {
            // Check the forward square
            if (board.getPiece(new ChessPosition(newRow, myCol)) == null) {
                // If the forward square is empty, add the move
                if (newRow == 8 || newRow == 1) {
                    // If the pawn reaches the end, add moves with each type of promotion
                    for (ChessPiece.PieceType type : ChessPiece.PieceType.values()) {
                        if (type != ChessPiece.PieceType.PAWN && type != ChessPiece.PieceType.KING) {
                            pawnMoves.add(new ChessMove(myPosition, new ChessPosition(newRow, myCol), type));
                        }
                    }
                } else {
                    pawnMoves.add(new ChessMove(myPosition, new ChessPosition(newRow, myCol), null));
                }
                // Move forward two squares if in starting position
                if ((teamColor == ChessGame.TeamColor.WHITE && myRow == 2) ||
                        (teamColor == ChessGame.TeamColor.BLACK && myRow == 7)) {
                    int doubleMoveRow = newRow + direction;
                    if (board.getPiece(new ChessPosition(doubleMoveRow, myCol)) == null) {
                        pawnMoves.add(new ChessMove(myPosition, new ChessPosition(doubleMoveRow, myCol), null));
                    }
                }
            }
        }

        // Check for capturing moves diagonally
        int[] captureCols = { myCol - 1, myCol + 1 };
        for (int captureCol : captureCols) {
            if (captureCol >= 1 && captureCol <= 8) {
                ChessPosition capturePos = new ChessPosition(newRow, captureCol);
                ChessPiece capturedPiece = board.getPiece(capturePos);
                if (capturedPiece != null && capturedPiece.getTeamColor() != teamColor) {
                    if (newRow == 8 || newRow == 1) {
                        for (ChessPiece.PieceType type : ChessPiece.PieceType.values()) {
                            if (type != ChessPiece.PieceType.PAWN && type != ChessPiece.PieceType.KING) {
                                pawnMoves.add(new ChessMove(myPosition, capturePos, type));
                            }
                        }
                    } else {
                        pawnMoves.add(new ChessMove(myPosition, capturePos, null));
                    }
                }
            }
        }
        return pawnMoves;
    }
}
