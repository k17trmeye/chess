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

    public ArrayList<ChessMove> BishopMoveCalculator(ChessBoard board, ChessGame.TeamColor teamColor,
                                                     ChessPosition myPosition) {
        BishopMove bishopMove = new BishopMove();
        return bishopMove.BishopMoveCal(board, row, col, teamColor, myPosition);
    }

    public ArrayList<ChessMove> PawnMoveCalculator(ChessBoard board, ChessGame.TeamColor teamColor,
                                                     ChessPiece.PieceType pieceType, ChessPosition myPosition) {
        PawnMove pawnMove = new PawnMove();
        return pawnMove.PawnMoveCal(board, row, col, teamColor, pieceType, myPosition);
    }

    public ArrayList<ChessMove> KingMoveCalculator(ChessBoard board, ChessGame.TeamColor teamColor,
                                                   ChessPosition myPosition) {
        KingMove kingMove = new KingMove();
        return kingMove.KingMoveCal(board, row, col, teamColor, myPosition);
    }

    public ArrayList<ChessMove> KnightMoveCalculator(ChessBoard board, ChessGame.TeamColor teamColor,
                                                   ChessPosition myPosition) {
        KnightMove knightMove = new KnightMove();
        return knightMove.KnighMoveCal(board, row, col, teamColor, myPosition);
    }

    public ArrayList<ChessMove> RookMoveCalculator(ChessBoard board, ChessGame.TeamColor teamColor,
                                                     ChessPosition myPosition) {
        RookMove rookMove = new RookMove();
        return rookMove.RookMoveCal(board, row, col, teamColor, myPosition);
    }

    public ArrayList<ChessMove> QueenMoveCalculator(ChessBoard board, ChessGame.TeamColor teamColor,
                                                   ChessPosition myPosition) {
        QueenMove queenMove = new QueenMove();
        return queenMove.QueenMoveCal(board, row, col, teamColor, myPosition);
    }

}
