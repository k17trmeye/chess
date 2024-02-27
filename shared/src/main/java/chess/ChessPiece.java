package chess;

import chess.moves.MoveCalculator;

import java.util.ArrayList;
import java.util.Collection;

import java.util.Objects;

public class ChessPiece {
    private ChessGame.TeamColor teamColor;
    private ChessPiece.PieceType pieceType;
    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        teamColor = pieceColor;
        pieceType = type;
    }
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    public enum TeamColor {
        BLACK,
        WHITE
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() { return teamColor; }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() { return pieceType; }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        // Collection of valid moves
        ArrayList<ChessMove> validMoves = new ArrayList<>();

        MoveCalculator moveCalculator = new MoveCalculator(myPosition.getRow(), myPosition.getColumn());

        if (pieceType == PieceType.BISHOP) {
            validMoves = moveCalculator.bishopMoveCalculator(board, teamColor, myPosition);
        }
        if (pieceType == PieceType.PAWN) {
            validMoves = moveCalculator.pawnMoveCalculator(board, teamColor, pieceType, myPosition);
        }
        if (pieceType == PieceType.KING) {
            validMoves = moveCalculator.kingMoveCalculator(board, teamColor, myPosition);
        }
        if (pieceType == PieceType.KNIGHT) {
            validMoves = moveCalculator.knightMoveCalculator(board, teamColor, myPosition);
        }
        if (pieceType == PieceType.ROOK) {
            validMoves = moveCalculator.rookMoveCalculator(board, teamColor, myPosition);
        }
        if (pieceType == PieceType.QUEEN) {
            validMoves = moveCalculator.queenMoveCalculator(board, teamColor, myPosition);
        }
        return validMoves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece piece = (ChessPiece) o;
        return teamColor == piece.teamColor && pieceType == piece.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamColor, pieceType);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "team_color=" + teamColor +
                ", piece_type=" + pieceType +
                '}';
    }
}
