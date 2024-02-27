package chess.moves;

import chess.*;

import java.util.ArrayList;

public class PawnMove {
    public ArrayList<ChessMove> pawnMoves;

    public PawnMove () {
        pawnMoves = new ArrayList<>();
    }

    public ArrayList<ChessMove> PawnMoveCal(ChessBoard board, Integer row, Integer col,
                                              ChessGame.TeamColor teamColor, ChessPiece.PieceType pieceType,
                                              ChessPosition myPosition) {
        ChessPosition newPosition;
        ChessMove newMove;
        Integer newCol = col;
        Integer newRow = row;
        int myRow = myPosition.getRow();
        int myCol = myPosition.getColumn();
        // Move forward once, team white
        if (teamColor == ChessGame.TeamColor.WHITE) {
            newCol = myCol;
            newRow = myRow + 1;

            if (newRow <= 8) {
                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece pieceInter = board.getPiece(newPosition);
                // If Pawn hits the end, add moves with each type of promotion
                if (newRow == 8) {
                    for (ChessPiece.PieceType type_of_piece: ChessPiece.PieceType.values()) {
                        if ((type_of_piece == ChessPiece.PieceType.PAWN) || (type_of_piece == ChessPiece.PieceType.KING)) {
                            continue;
                        }
                        // Adds the new position as a possible move along with a promotion type
                        newMove = new ChessMove(myPosition, newPosition, type_of_piece);
                        pawnMoves.add(newMove);
                    }
                }
                // No pieces there, add unconditionally
                else if (pieceInter == null) {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    pawnMoves.add(newMove);
                }
            }

            // Check to see if any enemies up right of pawn
            newCol = myCol + 1;
            newRow = myRow + 1;

            if (!(newCol > 8) && !(newRow > 8)) {
                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);

                if (piece_inter != null) {
                    // If the piece is not on the same team, create move
                    if (teamColor != piece_inter.getTeamColor()) {
                        if (newRow == 8) {
                            for (ChessPiece.PieceType type_of_piece : ChessPiece.PieceType.values()) {
                                if ((type_of_piece == ChessPiece.PieceType.PAWN) || (type_of_piece == ChessPiece.PieceType.KING)) {
                                    continue;
                                }
                                // Adds the new position as a possible move along with a promotion type
                                newMove = new ChessMove(myPosition, newPosition, type_of_piece);
                                pawnMoves.add(newMove);
                            }
                        } else {
                            // Adds the new position as a possible move along with a promotion type
                            newMove = new ChessMove(myPosition, newPosition, pieceType);
                            pawnMoves.add(newMove);
                        }
                    }
                }
            }

            // Check to see if any enemies up left of pawn
            newCol = myCol - 1;
            newRow = myRow + 1;

            if ((newCol >= 1) || !(newRow <= 8)) {
                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);
                if (piece_inter != null) {
                    // If the piece is not on the same team, create move
                    if (teamColor != piece_inter.getTeamColor()) {
                        if (newRow == 8) {
                            for (ChessPiece.PieceType type_of_piece : ChessPiece.PieceType.values()) {
                                if ((type_of_piece == ChessPiece.PieceType.PAWN) || (type_of_piece == ChessPiece.PieceType.KING)) {
                                    continue;
                                }
                                // Adds the new position as a possible move along with a promotion type
                                newMove = new ChessMove(myPosition, newPosition, type_of_piece);
                                pawnMoves.add(newMove);
                            }
                        } else {
                            // Adds the new position as a possible move along with a promotion type
                            newMove = new ChessMove(myPosition, newPosition, null);
                            pawnMoves.add(newMove);
                        }
                    }
                }
            }
        }

        // Move downward once, team black
        if (teamColor == ChessGame.TeamColor.BLACK) {
            newCol = myCol;
            newRow = myRow - 1;
            if (newRow < 1) {
                // Do nothing
            } else {
                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);

                // If Pawn hits the end, add moves with each type of promotion
                if ((newRow == 1) && (piece_inter == null)) {
                    for (ChessPiece.PieceType type_of_piece: ChessPiece.PieceType.values()) {
                        if ((type_of_piece == ChessPiece.PieceType.PAWN) || (type_of_piece == ChessPiece.PieceType.KING)) {
                            continue;
                        }
                        // Adds the new position as a possible move along with a promotion type
                        newMove = new ChessMove(myPosition, newPosition, type_of_piece);
                        pawnMoves.add(newMove);
                    }
                }
                // No pieces there, add unconditionally
                else if (piece_inter == null) {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    pawnMoves.add(newMove);
                }
            }

            // Check to see if any enemies down right of pawn
            newCol = myCol + 1;
            newRow = myRow - 1;

            if (!(newCol > 8) && !(newRow < 1)) {
                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);

                if (piece_inter != null) {
                    // If the piece is not on the same team, create move
                    if (teamColor != piece_inter.getTeamColor()) {
                        if (newRow == 1) {
                            for (ChessPiece.PieceType type_of_piece : ChessPiece.PieceType.values()) {
                                if ((type_of_piece == ChessPiece.PieceType.PAWN) || (type_of_piece == ChessPiece.PieceType.KING)) {
                                    continue;
                                }
                                // Adds the new position as a possible move along with a promotion type
                                newMove = new ChessMove(myPosition, newPosition, type_of_piece);
                                pawnMoves.add(newMove);
                            }
                        } else {
                            // Adds the new position as a possible move along with a promotion type
                            newMove = new ChessMove(myPosition, newPosition, null);
                            pawnMoves.add(newMove);
                        }
                    }
                }
            }

            // Check to see if any enemies down left of pawn
            newCol = myCol - 1;
            newRow = myRow - 1;

            if ((newCol < 1) || (newRow < 1)) {

            }
            else {
                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);

                if (piece_inter != null) {
                    // If the piece is not on the same team, create move
                    if (teamColor != piece_inter.getTeamColor()) {
                        if (newRow == 1) {
                            for (ChessPiece.PieceType type_of_piece : ChessPiece.PieceType.values()) {
                                if ((type_of_piece == ChessPiece.PieceType.PAWN) || (type_of_piece == ChessPiece.PieceType.KING)) {
                                    continue;
                                }
                                // Adds the new position as a possible move along with a promotion type
                                newMove = new ChessMove(myPosition, newPosition, type_of_piece);
                                pawnMoves.add(newMove);
                            }
                        } else {
                            // Adds the new position as a possible move along with a promotion type
                            newMove = new ChessMove(myPosition, newPosition, pieceType);
                            pawnMoves.add(newMove);
                        }
                    }
                }
            }
        }


        // Move forward twice if in starting position
        if ((teamColor == ChessGame.TeamColor.WHITE) && (myPosition.getRow() == 2)) {
            newCol = myCol;
            newRow = myRow + 2;
            if (newCol > 8) {
                // Do nothing
            } else {
                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);
                if (piece_inter == null) {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    pawnMoves.add(newMove);
                }
            }

            // Check to see if any enemies up right of pawn
            newCol = myCol + 1;
            newRow = newRow + 1;

            if ((newCol > 8) && (newRow > 8)) {
                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);

                if (piece_inter != null) {
                    // If the piece is not on the same team, create move
                    if (teamColor != piece_inter.getTeamColor()) {
                        if (newRow == 8) {
                            for (ChessPiece.PieceType type_of_piece : ChessPiece.PieceType.values()) {
                                if ((type_of_piece == ChessPiece.PieceType.PAWN) || (type_of_piece == ChessPiece.PieceType.KING)) {
                                    continue;
                                }
                                // Adds the new position as a possible move along with a promotion type
                                newMove = new ChessMove(myPosition, newPosition, type_of_piece);
                                pawnMoves.add(newMove);
                            }
                        } else {
                            // Adds the new position as a possible move along with a promotion type
                            newMove = new ChessMove(myPosition, newPosition, pieceType);
                            pawnMoves.add(newMove);
                        }
                    }
                }
            }

            // Check to see if any enemies up left of pawn
            newCol = myCol - 1;
            newRow = newRow + 1;

            if ((newCol >= 1) && (newRow <= 8)) {
                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);

                if (piece_inter != null) {
                    // If the piece is not on the same team, create move
                    if (teamColor != piece_inter.getTeamColor()) {
                        if (newRow == 8) {
                            for (ChessPiece.PieceType type_of_piece : ChessPiece.PieceType.values()) {
                                if ((type_of_piece == ChessPiece.PieceType.PAWN) || (type_of_piece == ChessPiece.PieceType.KING)) {
                                    continue;
                                }
                                // Adds the new position as a possible move along with a promotion type
                                newMove = new ChessMove(myPosition, newPosition, type_of_piece);
                                pawnMoves.add(newMove);
                            }
                        } else {
                            // Adds the new position as a possible move along with a promotion type
                            newMove = new ChessMove(myPosition, newPosition, pieceType);
                            pawnMoves.add(newMove);
                        }
                    }
                }
            }
        }

        // Move forward twice if in starting position
        if ((teamColor == ChessGame.TeamColor.BLACK) && (myPosition.getRow() == 7)) {
            newCol = myCol;
            newRow = myRow - 1;

            // Creates new position
            newPosition = new ChessPosition(newRow, newCol);

            // Checks to see if another piece is on the new position
            ChessPiece piece_inter = board.getPiece(newPosition);

            if (piece_inter != null) {
                // Do nothing
            } else {
                // Creates new position
                newRow = myRow - 2;
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                piece_inter = board.getPiece(newPosition);
                if (piece_inter == null) {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    pawnMoves.add(newMove);
                }
            }

            // Check to see if any enemies down right of pawn
            newCol = myCol + 1;
            newRow = myRow - 1;

            if ((newCol > 8) && (newRow < 1)) {
                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                piece_inter = board.getPiece(newPosition);

                if (piece_inter != null) {
                    // If the piece is not on the same team, create move
                    if (teamColor != piece_inter.getTeamColor()) {
                        if (newRow == 1) {
                            for (ChessPiece.PieceType type_of_piece : ChessPiece.PieceType.values()) {
                                if ((type_of_piece == ChessPiece.PieceType.PAWN) || (type_of_piece == ChessPiece.PieceType.KING)) {
                                    continue;
                                }
                                // Adds the new position as a possible move along with a promotion type
                                newMove = new ChessMove(myPosition, newPosition, type_of_piece);
                                pawnMoves.add(newMove);
                            }
                        } else {
                            // Adds the new position as a possible move along with a promotion type
                            newMove = new ChessMove(myPosition, newPosition, pieceType);
                            pawnMoves.add(newMove);
                        }
                    }
                }
            }

            // Check to see if any enemies down left of pawn
            newCol = myCol - 1;
            newRow = myRow - 1;

            if ((newCol >= 1) && (newRow >= 1)) {
                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                piece_inter = board.getPiece(newPosition);

                if (piece_inter != null) {
                    // If the piece is not on the same team, create move
                    if (teamColor != piece_inter.getTeamColor()) {
                        if (newRow == 8) {
                            for (ChessPiece.PieceType type_of_piece : ChessPiece.PieceType.values()) {
                                if ((type_of_piece == ChessPiece.PieceType.PAWN) || (type_of_piece == ChessPiece.PieceType.KING)) {
                                    continue;
                                }
                                // Adds the new position as a possible move along with a promotion type
                                newMove = new ChessMove(myPosition, newPosition, type_of_piece);
                                pawnMoves.add(newMove);
                            }
                        } else {
                            // Adds the new position as a possible move along with a promotion type
                            newMove = new ChessMove(myPosition, newPosition, pieceType);
                            pawnMoves.add(newMove);
                        }
                    }
                }
            }
        }
        return pawnMoves;
    }
}
