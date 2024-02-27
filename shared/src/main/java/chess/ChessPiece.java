package chess;

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
        int myRow = myPosition.getRow();
        int myCol = myPosition.getColumn();

        // Collection of valid moves
        ArrayList<ChessMove> validMoves = new ArrayList<>();

        // Variables to create each move
        int newRow;
        int newCol;
        ChessPosition newPosition;
        ChessMove newMove;

        // Moves for Bishop
        if (pieceType == PieceType.BISHOP) {

            // Move up right till you can't anymore
            newCol = myCol;
            newRow = myRow;
            while (true) {
                newCol++;
                newRow++;
                if ((newCol > 8) || (newRow > 8)) {
                    break;
                }

                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece pieceInter = board.getPiece(newPosition);
                if (pieceInter != null) {
                    // Check to see team_color
                    if (teamColor == pieceInter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                    break;
                }
                else {

                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(newMove);
                }
            }

            // Move right down till you can't anymore
            newCol = myCol;
            newRow = myRow;
            while (true) {
                newCol++;
                newRow--;
                if ((newCol > 8) || (newRow < 1)) {
                    break;
                }

                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (teamColor == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                    break;
                }
                else {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(newMove);
                }
            }

            // Move left down till you can't anymore
            newCol = myCol;
            newRow = myRow;
            while (true) {
                newCol--;
                newRow--;
                if ((newCol < 1) || (newRow < 1)) {
                    break;
                }

                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (teamColor == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                    break;
                }
                else {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(newMove);
                }
            }

            // Move down left up till you can't anymore
            newCol = myCol;
            newRow = myRow;
            while (true) {
                newCol--;
                newRow++;
                if ((newCol < 1) || (newRow > 8)) {
                    break;
                }

                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (teamColor == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                    break;
                }
                else {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(newMove);
                }
            }
        }

        // Moves for a Pawn
        if (pieceType == PieceType.PAWN) {

            // Move forward once, team white
            if (teamColor == ChessGame.TeamColor.WHITE) {
                newCol = myCol;
                newRow = myRow + 1;

                if (newRow > 8) {
                    // Do nothing
                } else {
                    // Creates new position
                    newPosition = new ChessPosition(newRow, newCol);

                    // Checks to see if another piece is on the new position
                    ChessPiece piece_inter = board.getPiece(newPosition);
                    // If Pawn hits the end, add moves with each type of promotion
                    if (newRow == 8) {
                        for (PieceType type_of_piece: PieceType.values()) {
                            if ((type_of_piece == PieceType.PAWN) || (type_of_piece == PieceType.KING)) {
                                continue;
                            }
                            // Adds the new position as a possible move along with a promotion type
                            newMove = new ChessMove(myPosition, newPosition, type_of_piece);
                            validMoves.add(newMove);
                        }
                    }
                    // No pieces there, add unconditionally
                    else if (piece_inter == null) {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
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
                                for (PieceType type_of_piece : PieceType.values()) {
                                    if ((type_of_piece == PieceType.PAWN) || (type_of_piece == PieceType.KING)) {
                                        continue;
                                    }
                                    // Adds the new position as a possible move along with a promotion type
                                    newMove = new ChessMove(myPosition, newPosition, type_of_piece);
                                    validMoves.add(newMove);
                                }
                            } else {
                                // Adds the new position as a possible move along with a promotion type
                                newMove = new ChessMove(myPosition, newPosition, pieceType);
                                validMoves.add(newMove);
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
                                for (PieceType type_of_piece : PieceType.values()) {
                                    if ((type_of_piece == PieceType.PAWN) || (type_of_piece == PieceType.KING)) {
                                        continue;
                                    }
                                    // Adds the new position as a possible move along with a promotion type
                                    newMove = new ChessMove(myPosition, newPosition, type_of_piece);
                                    validMoves.add(newMove);
                                }
                            } else {
                                // Adds the new position as a possible move along with a promotion type
                                newMove = new ChessMove(myPosition, newPosition, null);
                                validMoves.add(newMove);
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
                        for (PieceType type_of_piece: PieceType.values()) {
                            if ((type_of_piece == PieceType.PAWN) || (type_of_piece == PieceType.KING)) {
                                continue;
                            }
                            // Adds the new position as a possible move along with a promotion type
                            newMove = new ChessMove(myPosition, newPosition, type_of_piece);
                            validMoves.add(newMove);
                        }
                    }
                    // No pieces there, add unconditionally
                    else if (piece_inter == null) {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
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
                                for (PieceType type_of_piece : PieceType.values()) {
                                    if ((type_of_piece == PieceType.PAWN) || (type_of_piece == PieceType.KING)) {
                                        continue;
                                    }
                                    // Adds the new position as a possible move along with a promotion type
                                    newMove = new ChessMove(myPosition, newPosition, type_of_piece);
                                    validMoves.add(newMove);
                                }
                            } else {
                                // Adds the new position as a possible move along with a promotion type
                                newMove = new ChessMove(myPosition, newPosition, null);
                                validMoves.add(newMove);
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
                                for (PieceType type_of_piece : PieceType.values()) {
                                    if ((type_of_piece == PieceType.PAWN) || (type_of_piece == PieceType.KING)) {
                                        continue;
                                    }
                                    // Adds the new position as a possible move along with a promotion type
                                    newMove = new ChessMove(myPosition, newPosition, type_of_piece);
                                    validMoves.add(newMove);
                                }
                            } else {
                                // Adds the new position as a possible move along with a promotion type
                                newMove = new ChessMove(myPosition, newPosition, pieceType);
                                validMoves.add(newMove);
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
                        validMoves.add(newMove);
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
                                for (PieceType type_of_piece : PieceType.values()) {
                                    if ((type_of_piece == PieceType.PAWN) || (type_of_piece == PieceType.KING)) {
                                        continue;
                                    }
                                    // Adds the new position as a possible move along with a promotion type
                                    newMove = new ChessMove(myPosition, newPosition, type_of_piece);
                                    validMoves.add(newMove);
                                }
                            } else {
                                // Adds the new position as a possible move along with a promotion type
                                newMove = new ChessMove(myPosition, newPosition, pieceType);
                                validMoves.add(newMove);
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
                                for (PieceType type_of_piece : PieceType.values()) {
                                    if ((type_of_piece == PieceType.PAWN) || (type_of_piece == PieceType.KING)) {
                                        continue;
                                    }
                                    // Adds the new position as a possible move along with a promotion type
                                    newMove = new ChessMove(myPosition, newPosition, type_of_piece);
                                    validMoves.add(newMove);
                                }
                            } else {
                                // Adds the new position as a possible move along with a promotion type
                                newMove = new ChessMove(myPosition, newPosition, pieceType);
                                validMoves.add(newMove);
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
                        validMoves.add(newMove);
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
                                for (PieceType type_of_piece : PieceType.values()) {
                                    if ((type_of_piece == PieceType.PAWN) || (type_of_piece == PieceType.KING)) {
                                        continue;
                                    }
                                    // Adds the new position as a possible move along with a promotion type
                                    newMove = new ChessMove(myPosition, newPosition, type_of_piece);
                                    validMoves.add(newMove);
                                }
                            } else {
                                // Adds the new position as a possible move along with a promotion type
                                newMove = new ChessMove(myPosition, newPosition, pieceType);
                                validMoves.add(newMove);
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
                                for (PieceType type_of_piece : PieceType.values()) {
                                    if ((type_of_piece == PieceType.PAWN) || (type_of_piece == PieceType.KING)) {
                                        continue;
                                    }
                                    // Adds the new position as a possible move along with a promotion type
                                    newMove = new ChessMove(myPosition, newPosition, type_of_piece);
                                    validMoves.add(newMove);
                                }
                            } else {
                                // Adds the new position as a possible move along with a promotion type
                                newMove = new ChessMove(myPosition, newPosition, pieceType);
                                validMoves.add(newMove);
                            }
                        }
                    }
                }
            }
        }

        // moves for a King
        if (pieceType == PieceType.KING) {
            // Move up once
            newCol = myCol + 1;
            newRow = myRow;
            if (newCol > 8) {
                // Do nothing
            }
            else {
                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (teamColor != piece_inter.getTeamColor()) {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                }
                else {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(newMove);
                }
            }

            // Move down once
            newRow = myRow;
            newCol = myCol - 1;
            if (newCol < 1) {
                // Do nothing
            }
            else {
                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (teamColor != piece_inter.getTeamColor()) {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                }
                else {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(newMove);
                }
            }

            // Move right once
            newCol = myCol;
            newRow = myRow + 1;
            if (newRow > 8) {
                // Do nothing
            }
            else {
                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (teamColor != piece_inter.getTeamColor()) {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                }
                else {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(newMove);
                }
            }

            // Move left once
            newCol = myCol;
            newRow = myRow - 1;
            if (newRow < 1) {
                // Do nothing
            }
            else {
                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (teamColor != piece_inter.getTeamColor()) {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                }
                else {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(newMove);
                }
            }

            // Move up left once
            newCol = myCol + 1;
            newRow = myRow + 1;
            if ((newCol > 8) || (newRow > 8)) {
                // Do nothing
            }
            else {
                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (teamColor != piece_inter.getTeamColor()) {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                }
                else {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(newMove);
                }
            }

            // Move down left once
            newCol = myCol + 1;
            newRow = myRow - 1;
            if ((newCol > 8) || (newRow < 1)) {
                // Do nothing
            }
            else {
                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (teamColor != piece_inter.getTeamColor()) {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                }
                else {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(newMove);
                }
            }

            // Move up right once
            newCol = myCol - 1;
            newRow = myRow + 1;
            if ((newCol < 1) || (newRow > 8)) {
                // Do nothing
            }
            else {
                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (teamColor != piece_inter.getTeamColor()) {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                }
                else {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(newMove);
                }
            }

            // Move down right once
            newCol = myCol - 1;
            newRow = myRow - 1;
            if ((newCol < 1) || (newRow < 1)) {
                // Do nothing
            }
            else {
                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (teamColor != piece_inter.getTeamColor()) {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                }
                else {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(newMove);
                }
            }


        }
        if (pieceType == PieceType.KNIGHT) {
            // Move right twice
            newCol = myCol + 2;
            if (newCol > 8) {
                // Do nothing
            }
            else {
                // Create position up
                newRow = myRow + 1;
                if (newRow <= 8) {
                    // Creates new position
                    newPosition = new ChessPosition(newRow, newCol);

                    // Checks to see if another piece is on the new position
                    ChessPiece piece_inter = board.getPiece(newPosition);
                    if (piece_inter != null) {
                        // Check to see team_color
                        if (teamColor != piece_inter.getTeamColor()) {
                            // Adds the new position as a possible move
                            newMove = new ChessMove(myPosition, newPosition, null);
                            validMoves.add(newMove);
                        }
                    } else {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                }

                // Create position down
                newRow = myRow - 1;
                if (newRow >= 1) {
                    // Creates new position
                    newPosition = new ChessPosition(newRow, newCol);

                    // Checks to see if another piece is on the new position
                    ChessPiece piece_inter = board.getPiece(newPosition);
                    if (piece_inter != null) {
                        // Check to see team_color
                        if (teamColor != piece_inter.getTeamColor()) {
                            // Adds the new position as a possible move
                            newMove = new ChessMove(myPosition, newPosition, null);
                            validMoves.add(newMove);
                        }
                    } else {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                }
            }

            // Move left twice
            newCol = myCol - 2;
            if (newCol < 1) {
                // Do nothing
            }
            else {
                // Create position up
                newRow = myRow + 1;
                if (newRow <= 8) {
                    // Creates new position
                    newPosition = new ChessPosition(newRow, newCol);

                    // Checks to see if another piece is on the new position
                    ChessPiece piece_inter = board.getPiece(newPosition);
                    if (piece_inter != null) {
                        // Check to see team_color
                        if (teamColor != piece_inter.getTeamColor()) {
                            // Adds the new position as a possible move
                            newMove = new ChessMove(myPosition, newPosition, null);
                            validMoves.add(newMove);
                        }
                    } else {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                }

                // Create position down
                newRow = myRow - 1;
                if (newRow >= 1) {
                    // Creates new position
                    newPosition = new ChessPosition(newRow, newCol);

                    // Checks to see if another piece is on the new position
                    ChessPiece piece_inter = board.getPiece(newPosition);
                    if (piece_inter != null) {
                        // Check to see team_color
                        if (teamColor != piece_inter.getTeamColor()) {
                            // Adds the new position as a possible move
                            newMove = new ChessMove(myPosition, newPosition, null);
                            validMoves.add(newMove);
                        }
                    } else {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                }
            }


            // Move up twice
            newRow = myRow + 2;
            if (newRow > 8) {
                // Do nothing
            }
            else {
                // Create position right
                newCol = myCol + 1;
                if (newCol <= 8) {
                    // Creates new position
                    newPosition = new ChessPosition(newRow, newCol);
                    // Checks to see if another piece is on the new position
                    ChessPiece piece_inter = board.getPiece(newPosition);
                    if (piece_inter != null) {
                        // Check to see team_color
                        if (teamColor != piece_inter.getTeamColor()) {
                            // Adds the new position as a possible move
                            newMove = new ChessMove(myPosition, newPosition, null);
                            validMoves.add(newMove);
                        }
                    } else {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                }

                // Create position left
                newCol = myCol - 1;
                if (newCol >= 1) {
                    // Creates new position
                    newPosition = new ChessPosition(newRow, newCol);
                    // Checks to see if another piece is on the new position
                    ChessPiece piece_inter = board.getPiece(newPosition);
                    if (piece_inter != null) {
                        // Check to see team_color
                        if (teamColor != piece_inter.getTeamColor()) {
                            // Adds the new position as a possible move
                            newMove = new ChessMove(myPosition, newPosition, null);
                            validMoves.add(newMove);
                        }
                    } else {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                }
            }

            // Move down twice
            newRow = myRow - 2;
            if (newRow < 1) {
                // Do nothing
            }
            else {
                // Create position right
                newCol = myCol + 1;
                if (newCol <= 8) {
                    // Creates new position
                    newPosition = new ChessPosition(newRow, newCol);

                    // Checks to see if another piece is on the new position
                    ChessPiece piece_inter = board.getPiece(newPosition);
                    if (piece_inter != null) {
                        // Check to see team_color
                        if (teamColor != piece_inter.getTeamColor()) {
                            // Adds the new position as a possible move
                            newMove = new ChessMove(myPosition, newPosition, null);
                            validMoves.add(newMove);
                        }
                    } else {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                }

                // Create position left
                newCol = myCol - 1;
                if (newCol >= 1) {
                    // Creates new position
                    newPosition = new ChessPosition(newRow, newCol);

                    // Checks to see if another piece is on the new position
                    ChessPiece piece_inter = board.getPiece(newPosition);
                    if (piece_inter != null) {
                        // Check to see team_color
                        if (teamColor != piece_inter.getTeamColor()) {
                            // Adds the new position as a possible move
                            newMove = new ChessMove(myPosition, newPosition, null);
                            validMoves.add(newMove);
                        }
                    } else {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                }
            }
        }

        if (pieceType == PieceType.ROOK) {
            // Move up till you can't anymore
            newCol = myCol;
            newRow = myRow;
            while (true) {
                newCol++;
                if (newCol > 8) {
                    break;
                }

                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (teamColor == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                    break;
                }
                else {

                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(newMove);
                }
            }

            // Move down till you can't anymore
            newCol = myCol;
            while (true) {
                newCol--;
                if (newCol < 1) {
                    break;
                }

                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (teamColor == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                    break;
                }
                else {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(newMove);
                }
            }

            // Move left till you can't anymore
            newCol = myCol;
            newRow = myRow;
            while (true) {
                newRow--;
                if (newRow < 1) {
                    break;
                }

                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (teamColor == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                    break;
                }
                else {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(newMove);
                }
            }

            // Move down left up till you can't anymore
            newCol = myCol;
            newRow = myRow;
            while (true) {
                newRow++;
                if (newRow > 8) {
                    break;
                }

                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (teamColor == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                    break;
                }
                else {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(newMove);
                }
            }
        }

        if (pieceType == PieceType.QUEEN) {
            // Move up till you can't anymore
            newCol = myCol;
            newRow = myRow;
            while (true) {
                newCol++;
                if (newCol > 8) {
                    break;
                }

                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (teamColor == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                    break;
                }
                else {

                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(newMove);
                }
            }

            // Move down till you can't anymore
            newCol = myCol;
            while (true) {
                newCol--;
                if (newCol < 1) {
                    break;
                }

                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (teamColor == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                    break;
                }
                else {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(newMove);
                }
            }

            // Move left till you can't anymore
            newCol = myCol;
            newRow = myRow;
            while (true) {
                newRow--;
                if (newRow < 1) {
                    break;
                }

                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (teamColor == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                    break;
                }
                else {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(newMove);
                }
            }

            // Move down left up till you can't anymore
            newCol = myCol;
            newRow = myRow;
            while (true) {
                newRow++;
                if (newRow > 8) {
                    break;
                }

                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (teamColor == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                    break;
                }
                else {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(newMove);
                }
            }

            // Move up right till you can't anymore
            newCol = myCol;
            newRow = myRow;
            while (true) {
                newCol++;
                newRow++;
                if ((newCol > 8) || (newRow > 8)) {
                    break;
                }

                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (teamColor == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                    break;
                }
                else {

                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(newMove);
                }
            }

            // Move right down till you can't anymore
            newCol = myCol;
            newRow = myRow;
            while (true) {
                newCol++;
                newRow--;
                if ((newCol > 8) || (newRow < 1)) {
                    break;
                }

                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (teamColor == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                    break;
                }
                else {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(newMove);
                }
            }

            // Move left down till you can't anymore
            newCol = myCol;
            newRow = myRow;
            while (true) {
                newCol--;
                newRow--;
                if ((newCol < 1) || (newRow < 1)) {
                    break;
                }

                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (teamColor == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                    break;
                }
                else {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(newMove);
                }
            }

            // Move down left up till you can't anymore
            newCol = myCol;
            newRow = myRow;
            while (true) {
                newCol--;
                newRow++;
                if ((newCol < 1) || (newRow > 8)) {
                    break;
                }

                // Creates new position
                newPosition = new ChessPosition(newRow, newCol);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(newPosition);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (teamColor == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        newMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(newMove);
                    }
                    break;
                }
                else {
                    // Adds the new position as a possible move
                    newMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(newMove);
                }
            }
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
