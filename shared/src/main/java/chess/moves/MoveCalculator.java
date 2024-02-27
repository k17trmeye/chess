package chess.moves;

import chess.*;

import java.util.ArrayList;

public class MoveCalculator {
    public Integer row;
    public Integer col;

    public MoveCalculator(Integer row, Integer col) {
        this.row = row;
        this.col = col;
    }

    public ArrayList<ChessMove> bishopMoveCalculator(ChessBoard board, ChessGame.TeamColor teamColor,
                                                     ChessPosition myPosition) {
        BishopMove bishopMove = new BishopMove();
        return bishopMove.bishopMoveCal(board, row, col, teamColor, myPosition);
    }

    public ArrayList<ChessMove> pawnMoveCalculator(ChessBoard board, ChessGame.TeamColor teamColor,
                                                   ChessPiece.PieceType pieceType, ChessPosition myPosition) {
        PawnMove pawnMove = new PawnMove();
        return pawnMove.pawnMoveCal(board, row, col, teamColor, pieceType, myPosition);
    }

    public ArrayList<ChessMove> kingMoveCalculator(ChessBoard board, ChessGame.TeamColor teamColor,
                                                   ChessPosition myPosition) {
        KingMove kingMove = new KingMove();
        return kingMove.kingMoveCal(board, row, col, teamColor, myPosition);
    }

    public ArrayList<ChessMove> knightMoveCalculator(ChessBoard board, ChessGame.TeamColor teamColor,
                                                     ChessPosition myPosition) {
        KnightMove knightMove = new KnightMove();
        return knightMove.knightMoveCal(board, row, col, teamColor, myPosition);
    }

    public ArrayList<ChessMove> rookMoveCalculator(ChessBoard board, ChessGame.TeamColor teamColor,
                                                   ChessPosition myPosition) {
        RookMove rookMove = new RookMove();
        return rookMove.rookMoveCal(board, row, col, teamColor, myPosition);
    }

    public ArrayList<ChessMove> queenMoveCalculator(ChessBoard board, ChessGame.TeamColor teamColor,
                                                    ChessPosition myPosition) {
        QueenMove queenMove = new QueenMove();
        return queenMove.queenMoveCal(board, row, col, teamColor, myPosition);
    }

}
