package chess;

import java.util.Collection;

import java.util.HashSet;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private ChessGame.TeamColor team_color;
    private ChessPiece.PieceType piece_type;
    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        team_color = pieceColor;
        piece_type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * The various different chess piece options
     */
    public enum TeamColor {
        BLACK,
        WHITE
    }



    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() { return team_color; }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() { return piece_type; }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int my_row = myPosition.getRow();
        int my_col = myPosition.getColumn();


        // Collection of valid moves
        var valid_moves = new HashSet<ChessMove>();

        // Variables to create each move
        int new_row;
        int new_col;
        ChessPosition new_position;
        ChessMove new_move;

        // Moves for Bishop
        if (piece_type == PieceType.BISHOP) {

            // Move up right till you can't anymore
            new_col = my_col;
            new_row = my_row;
            while (true) {
                new_col++;
                new_row++;
                if ((new_col > 8) || (new_row > 8)) {
                    break;
                }

                // Creates new position
                new_position = new ChessPosition(new_row, new_col);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(new_position);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (team_color == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
                    }
                    break;
                }
                else {

                    // Adds the new position as a possible move
                    new_move = new ChessMove(myPosition, new_position, null);
                    valid_moves.add(new_move);
                }
            }

            // Move right down till you can't anymore
            new_col = my_col;
            new_row = my_row;
            while (true) {
                new_col++;
                new_row--;
                if ((new_col > 8) || (new_row < 1)) {
                    break;
                }

                // Creates new position
                new_position = new ChessPosition(new_row, new_col);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(new_position);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (team_color == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
                    }
                    break;
                }
                else {
                    // Adds the new position as a possible move
                    new_move = new ChessMove(myPosition, new_position, null);
                    valid_moves.add(new_move);
                }
            }

            // Move left down till you can't anymore
            new_col = my_col;
            new_row = my_row;
            while (true) {
                new_col--;
                new_row--;
                if ((new_col < 1) || (new_row < 1)) {
                    break;
                }

                // Creates new position
                new_position = new ChessPosition(new_row, new_col);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(new_position);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (team_color == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
                    }
                    break;
                }
                else {
                    // Adds the new position as a possible move
                    new_move = new ChessMove(myPosition, new_position, null);
                    valid_moves.add(new_move);
                }
            }

            // Move down left up till you can't anymore
            new_col = my_col;
            new_row = my_row;
            while (true) {
                new_col--;
                new_row++;
                if ((new_col < 1) || (new_row > 8)) {
                    break;
                }

                // Creates new position
                new_position = new ChessPosition(new_row, new_col);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(new_position);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (team_color == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
                    }
                    break;
                }
                else {
                    // Adds the new position as a possible move
                    new_move = new ChessMove(myPosition, new_position, null);
                    valid_moves.add(new_move);
                }
            }
        }

        // Moves for a Pawn
        if (piece_type == PieceType.PAWN) {

            // Move forward once, team white
            if (team_color == ChessGame.TeamColor.WHITE) {
                new_col = my_col;
                new_row = my_row + 1;

                if (new_row > 8) {
                    // Do nothing
                } else {
                    // Creates new position
                    new_position = new ChessPosition(new_row, new_col);

                    // Checks to see if another piece is on the new position
                    ChessPiece piece_inter = board.getPiece(new_position);
                    // If Pawn hits the end, add moves with each type of promotion
                    if (new_row == 8) {
                        for (PieceType type_of_piece: PieceType.values()) {
                            if ((type_of_piece == PieceType.PAWN) || (type_of_piece == PieceType.KING)) {
                                continue;
                            }
                            // Adds the new position as a possible move along with a promotion type
                            new_move = new ChessMove(myPosition, new_position, type_of_piece);
                            valid_moves.add(new_move);
                        }
                    }
                    // No pieces there, add unconditionally
                    else if (piece_inter == null) {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
                    }
                }

                // Check to see if any enemies up right of pawn
                new_col = my_col + 1;
                new_row = my_row + 1;

                if (!(new_col > 8) && !(new_row > 8)) {
                    // Creates new position
                    new_position = new ChessPosition(new_row, new_col);

                    // Checks to see if another piece is on the new position
                    ChessPiece piece_inter = board.getPiece(new_position);

                    if (piece_inter != null) {
                        // If the piece is not on the same team, create move
                        if (team_color != piece_inter.getTeamColor()) {
                            if (new_row == 8) {
                                for (PieceType type_of_piece : PieceType.values()) {
                                    if ((type_of_piece == PieceType.PAWN) || (type_of_piece == PieceType.KING)) {
                                        continue;
                                    }
                                    // Adds the new position as a possible move along with a promotion type
                                    new_move = new ChessMove(myPosition, new_position, type_of_piece);
                                    valid_moves.add(new_move);
                                }
                            } else {
                                // Adds the new position as a possible move along with a promotion type
                                new_move = new ChessMove(myPosition, new_position, piece_type);
                                valid_moves.add(new_move);
                            }
                        }
                    }
                }

                // Check to see if any enemies up left of pawn
                new_col = my_col - 1;
                new_row = my_row + 1;

                if ((new_col >= 1) || !(new_row <= 8)) {
                    // Creates new position
                    new_position = new ChessPosition(new_row, new_col);
//                    System.out.println(new_row + ", " + new_col);

                    // Checks to see if another piece is on the new position
                    ChessPiece piece_inter = board.getPiece(new_position);
                    if (piece_inter != null) {
                        // If the piece is not on the same team, create move
                        if (team_color != piece_inter.getTeamColor()) {
                            if (new_row == 8) {
                                for (PieceType type_of_piece : PieceType.values()) {
                                    if ((type_of_piece == PieceType.PAWN) || (type_of_piece == PieceType.KING)) {
                                        continue;
                                    }
                                    // Adds the new position as a possible move along with a promotion type
                                    new_move = new ChessMove(myPosition, new_position, type_of_piece);
                                    valid_moves.add(new_move);
                                }
                            } else {
                                // Adds the new position as a possible move along with a promotion type
                                new_move = new ChessMove(myPosition, new_position, null);
                                valid_moves.add(new_move);
                            }
                        }
                    }
                }
            }

            // Move downward once, team black
            if (team_color == ChessGame.TeamColor.BLACK) {
                new_col = my_col;
                new_row = my_row - 1;
                if (new_row < 1) {
                    // Do nothing
                } else {
                    // Creates new position
                    new_position = new ChessPosition(new_row, new_col);

                    // Checks to see if another piece is on the new position
                    ChessPiece piece_inter = board.getPiece(new_position);

                    // If Pawn hits the end, add moves with each type of promotion
                    if ((new_row == 1) && (piece_inter == null)) {
                        for (PieceType type_of_piece: PieceType.values()) {
                            if ((type_of_piece == PieceType.PAWN) || (type_of_piece == PieceType.KING)) {
                                continue;
                            }
                            // Adds the new position as a possible move along with a promotion type
                            new_move = new ChessMove(myPosition, new_position, type_of_piece);
                            valid_moves.add(new_move);
                        }
                    }
                    // No pieces there, add unconditionally
                    else if (piece_inter == null) {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
                    }
                }

                // Check to see if any enemies down right of pawn
                new_col = my_col + 1;
                new_row = my_row - 1;

                if (!(new_col > 8) && !(new_row < 1)) {
                    // Creates new position
                    new_position = new ChessPosition(new_row, new_col);

                    // Checks to see if another piece is on the new position
                    ChessPiece piece_inter = board.getPiece(new_position);

                    if (piece_inter != null) {
                        // If the piece is not on the same team, create move
                        if (team_color != piece_inter.getTeamColor()) {
                            if (new_row == 1) {
                                for (PieceType type_of_piece : PieceType.values()) {
                                    if ((type_of_piece == PieceType.PAWN) || (type_of_piece == PieceType.KING)) {
                                        continue;
                                    }
                                    // Adds the new position as a possible move along with a promotion type
                                    new_move = new ChessMove(myPosition, new_position, type_of_piece);
                                    valid_moves.add(new_move);
                                }
                            } else {
                                // Adds the new position as a possible move along with a promotion type
                                new_move = new ChessMove(myPosition, new_position, null);
                                valid_moves.add(new_move);
                            }
                        }
                    }
                }

                // Check to see if any enemies down left of pawn
                new_col = my_col - 1;
                new_row = my_row - 1;

                if ((new_col < 1) || (new_row < 1)) {

                }
                else {
                    // Creates new position
                    new_position = new ChessPosition(new_row, new_col);

                    // Checks to see if another piece is on the new position
                    ChessPiece piece_inter = board.getPiece(new_position);

                    if (piece_inter != null) {
                        // If the piece is not on the same team, create move
                        if (team_color != piece_inter.getTeamColor()) {
                            if (new_row == 1) {
                                for (PieceType type_of_piece : PieceType.values()) {
                                    if ((type_of_piece == PieceType.PAWN) || (type_of_piece == PieceType.KING)) {
                                        continue;
                                    }
                                    // Adds the new position as a possible move along with a promotion type
                                    new_move = new ChessMove(myPosition, new_position, type_of_piece);
                                    valid_moves.add(new_move);
                                }
                            } else {
                                // Adds the new position as a possible move along with a promotion type
                                new_move = new ChessMove(myPosition, new_position, piece_type);
                                valid_moves.add(new_move);
                            }
                        }
                    }
                }
            }


            // Move forward twice if in starting position
            if ((team_color == ChessGame.TeamColor.WHITE) && (myPosition.getRow() == 2)) {
                new_col = my_col;
                new_row = my_row + 2;
                if (new_col > 8) {
                    // Do nothing
                } else {
                    // Creates new position
                    new_position = new ChessPosition(new_row, new_col);

                    // Checks to see if another piece is on the new position
                    ChessPiece piece_inter = board.getPiece(new_position);
                    if (piece_inter == null) {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
                    }
                }

                // Check to see if any enemies up right of pawn
                new_col = my_col + 1;
                new_row = new_row + 1;

                if ((new_col > 8) && (new_row > 8)) {
                    // Creates new position
                    new_position = new ChessPosition(new_row, new_col);

                    // Checks to see if another piece is on the new position
                    ChessPiece piece_inter = board.getPiece(new_position);

                    if (piece_inter != null) {
                        // If the piece is not on the same team, create move
                        if (team_color != piece_inter.getTeamColor()) {
                            if (new_row == 8) {
                                for (PieceType type_of_piece : PieceType.values()) {
                                    if ((type_of_piece == PieceType.PAWN) || (type_of_piece == PieceType.KING)) {
                                        continue;
                                    }
                                    // Adds the new position as a possible move along with a promotion type
                                    new_move = new ChessMove(myPosition, new_position, type_of_piece);
                                    valid_moves.add(new_move);
                                }
                            } else {
                                // Adds the new position as a possible move along with a promotion type
                                new_move = new ChessMove(myPosition, new_position, piece_type);
                                valid_moves.add(new_move);
                            }
                        }
                    }
                }

                // Check to see if any enemies up left of pawn
                new_col = my_col - 1;
                new_row = new_row + 1;

                if ((new_col >= 1) && (new_row <= 8)) {
                    // Creates new position
                    new_position = new ChessPosition(new_row, new_col);

                    // Checks to see if another piece is on the new position
                    ChessPiece piece_inter = board.getPiece(new_position);

                    if (piece_inter != null) {
                        // If the piece is not on the same team, create move
                        if (team_color != piece_inter.getTeamColor()) {
                            if (new_row == 8) {
                                for (PieceType type_of_piece : PieceType.values()) {
                                    if ((type_of_piece == PieceType.PAWN) || (type_of_piece == PieceType.KING)) {
                                        continue;
                                    }
                                    // Adds the new position as a possible move along with a promotion type
                                    new_move = new ChessMove(myPosition, new_position, type_of_piece);
                                    valid_moves.add(new_move);
                                }
                            } else {
                                // Adds the new position as a possible move along with a promotion type
                                new_move = new ChessMove(myPosition, new_position, piece_type);
                                valid_moves.add(new_move);
                            }
                        }
                    }
                }
            }

            // Move forward twice if in starting position
            if ((team_color == ChessGame.TeamColor.BLACK) && (myPosition.getRow() == 7)) {
                new_col = my_col;
                new_row = my_row - 1;

                // Creates new position
                new_position = new ChessPosition(new_row, new_col);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(new_position);

                if (piece_inter != null) {
                    // Do nothing
                } else {
                    // Creates new position
                    new_row = my_row - 2;
                    new_position = new ChessPosition(new_row, new_col);

                    // Checks to see if another piece is on the new position
                    piece_inter = board.getPiece(new_position);
                    if (piece_inter == null) {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
                    }
                }

                // Check to see if any enemies down right of pawn
                new_col = my_col + 1;
                new_row = my_row - 1;

                if ((new_col > 8) && (new_row < 1)) {
                    // Creates new position
                    new_position = new ChessPosition(new_row, new_col);

                    // Checks to see if another piece is on the new position
                    piece_inter = board.getPiece(new_position);

                    if (piece_inter != null) {
                        // If the piece is not on the same team, create move
                        if (team_color != piece_inter.getTeamColor()) {
                            if (new_row == 1) {
                                for (PieceType type_of_piece : PieceType.values()) {
                                    if ((type_of_piece == PieceType.PAWN) || (type_of_piece == PieceType.KING)) {
                                        continue;
                                    }
                                    // Adds the new position as a possible move along with a promotion type
                                    new_move = new ChessMove(myPosition, new_position, type_of_piece);
                                    valid_moves.add(new_move);
                                }
                            } else {
                                // Adds the new position as a possible move along with a promotion type
                                new_move = new ChessMove(myPosition, new_position, piece_type);
                                valid_moves.add(new_move);
                            }
                        }
                    }
                }

                // Check to see if any enemies down left of pawn
                new_col = my_col - 1;
                new_row = my_row - 1;

                if ((new_col >= 1) && (new_row >= 1)) {
                    // Creates new position
                    new_position = new ChessPosition(new_row, new_col);

                    // Checks to see if another piece is on the new position
                    piece_inter = board.getPiece(new_position);

                    if (piece_inter != null) {
                        // If the piece is not on the same team, create move
                        if (team_color != piece_inter.getTeamColor()) {
                            if (new_row == 8) {
                                for (PieceType type_of_piece : PieceType.values()) {
                                    if ((type_of_piece == PieceType.PAWN) || (type_of_piece == PieceType.KING)) {
                                        continue;
                                    }
                                    // Adds the new position as a possible move along with a promotion type
                                    new_move = new ChessMove(myPosition, new_position, type_of_piece);
                                    valid_moves.add(new_move);
                                }
                            } else {
                                // Adds the new position as a possible move along with a promotion type
                                new_move = new ChessMove(myPosition, new_position, piece_type);
                                valid_moves.add(new_move);
                            }
                        }
                    }
                }
            }
        }

        // moves for a King
        if (piece_type == PieceType.KING) {
            // Move up once
            new_col = my_col + 1;
            new_row = my_row;
            if (new_col > 8) {
                // Do nothing
            }
            else {
                // Creates new position
                new_position = new ChessPosition(new_row, new_col);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(new_position);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (team_color != piece_inter.getTeamColor()) {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
//                        System.out.println(new_row + ", " + new_col);
                    }
                }
                else {
                    // Adds the new position as a possible move
                    new_move = new ChessMove(myPosition, new_position, null);
                    valid_moves.add(new_move);
//                    System.out.println(new_row + ", " + new_col);
                }
            }

            // Move down once
            new_row = my_row;
            new_col = my_col - 1;
            if (new_col < 1) {
                // Do nothing
            }
            else {
                // Creates new position
                new_position = new ChessPosition(new_row, new_col);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(new_position);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (team_color != piece_inter.getTeamColor()) {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
//                        System.out.println(new_row + ", " + new_col);
                    }
                }
                else {
                    // Adds the new position as a possible move
                    new_move = new ChessMove(myPosition, new_position, null);
                    valid_moves.add(new_move);
//                    System.out.println(new_row + ", " + new_col);
                }
            }

            // Move right once
            new_col = my_col;
            new_row = my_row + 1;
            if (new_row > 8) {
                // Do nothing
            }
            else {
                // Creates new position
                new_position = new ChessPosition(new_row, new_col);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(new_position);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (team_color != piece_inter.getTeamColor()) {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
//                        System.out.println(new_row + ", " + new_col);
                    }
                }
                else {
                    // Adds the new position as a possible move
                    new_move = new ChessMove(myPosition, new_position, null);
                    valid_moves.add(new_move);
//                    System.out.println(new_row + ", " + new_col);
                }
            }

            // Move left once
            new_col = my_col;
            new_row = my_row - 1;
            if (new_row < 1) {
                // Do nothing
            }
            else {
                // Creates new position
                new_position = new ChessPosition(new_row, new_col);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(new_position);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (team_color != piece_inter.getTeamColor()) {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
//                        System.out.println(new_row + ", " + new_col);
                    }
                }
                else {
                    // Adds the new position as a possible move
                    new_move = new ChessMove(myPosition, new_position, null);
                    valid_moves.add(new_move);
//                    System.out.println(new_row + ", " + new_col);
                }
            }

            // Move up left once
            new_col = my_col + 1;
            new_row = my_row + 1;
            if ((new_col > 8) || (new_row > 8)) {
                // Do nothing
            }
            else {
                // Creates new position
                new_position = new ChessPosition(new_row, new_col);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(new_position);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (team_color != piece_inter.getTeamColor()) {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
//                        System.out.println(new_row + ", " + new_col);
                    }
                }
                else {
                    // Adds the new position as a possible move
                    new_move = new ChessMove(myPosition, new_position, null);
                    valid_moves.add(new_move);
//                    System.out.println(new_row + ", " + new_col);
                }
            }

            // Move down left once
            new_col = my_col + 1;
            new_row = my_row - 1;
            if ((new_col > 8) || (new_row < 1)) {
                // Do nothing
            }
            else {
                // Creates new position
                new_position = new ChessPosition(new_row, new_col);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(new_position);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (team_color != piece_inter.getTeamColor()) {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
//                        System.out.println(new_row + ", " + new_col);
                    }
                }
                else {
                    // Adds the new position as a possible move
                    new_move = new ChessMove(myPosition, new_position, null);
                    valid_moves.add(new_move);
//                    System.out.println(new_row + ", " + new_col);
                }
            }

            // Move up right once
            new_col = my_col - 1;
            new_row = my_row + 1;
            if ((new_col < 1) || (new_row > 8)) {
                // Do nothing
            }
            else {
                // Creates new position
                new_position = new ChessPosition(new_row, new_col);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(new_position);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (team_color != piece_inter.getTeamColor()) {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
//                        System.out.println(new_row + ", " + new_col);
                    }
                }
                else {
                    // Adds the new position as a possible move
                    new_move = new ChessMove(myPosition, new_position, null);
                    valid_moves.add(new_move);
//                    System.out.println(new_row + ", " + new_col);
                }
            }

            // Move down right once
            new_col = my_col - 1;
            new_row = my_row - 1;
            if ((new_col < 1) || (new_row < 1)) {
                // Do nothing
            }
            else {
                // Creates new position
                new_position = new ChessPosition(new_row, new_col);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(new_position);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (team_color != piece_inter.getTeamColor()) {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
//                        System.out.println(new_row + ", " + new_col);
                    }
                }
                else {
                    // Adds the new position as a possible move
                    new_move = new ChessMove(myPosition, new_position, null);
                    valid_moves.add(new_move);
//                    System.out.println(new_row + ", " + new_col);
                }
            }


        }
        if (piece_type == PieceType.KNIGHT) {
            // Move right twice
            new_col = my_col + 2;
            if (new_col > 8) {
                // Do nothing
            }
            else {
                // Create position up
                new_row = my_row + 1;
                if (new_row <= 8) {
                    // Creates new position
                    new_position = new ChessPosition(new_row, new_col);

                    // Checks to see if another piece is on the new position
                    ChessPiece piece_inter = board.getPiece(new_position);
                    if (piece_inter != null) {
                        // Check to see team_color
                        if (team_color != piece_inter.getTeamColor()) {
                            // Adds the new position as a possible move
                            new_move = new ChessMove(myPosition, new_position, null);
                            valid_moves.add(new_move);
//                        System.out.println(new_row + ", " + new_col);
                        }
                    } else {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
//                    System.out.println(new_row + ", " + new_col);
                    }
                }

                // Create position down
                new_row = my_row - 1;
                if (new_row >= 1) {
                    // Creates new position
                    new_position = new ChessPosition(new_row, new_col);

                    // Checks to see if another piece is on the new position
                    ChessPiece piece_inter = board.getPiece(new_position);
                    if (piece_inter != null) {
                        // Check to see team_color
                        if (team_color != piece_inter.getTeamColor()) {
                            // Adds the new position as a possible move
                            new_move = new ChessMove(myPosition, new_position, null);
                            valid_moves.add(new_move);
//                        System.out.println(new_row + ", " + new_col);
                        }
                    } else {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
//                    System.out.println(new_row + ", " + new_col);
                    }
                }
            }

            // Move left twice
            new_col = my_col - 2;
            if (new_col < 1) {
                // Do nothing
            }
            else {
                // Create position up
                new_row = my_row + 1;
                if (new_row <= 8) {
                    // Creates new position
                    new_position = new ChessPosition(new_row, new_col);

                    // Checks to see if another piece is on the new position
                    ChessPiece piece_inter = board.getPiece(new_position);
                    if (piece_inter != null) {
                        // Check to see team_color
                        if (team_color != piece_inter.getTeamColor()) {
                            // Adds the new position as a possible move
                            new_move = new ChessMove(myPosition, new_position, null);
                            valid_moves.add(new_move);
//                            System.out.println(new_row + ", " + new_col);
                        }
                    } else {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
//                        System.out.println(new_row + ", " + new_col);
                    }
                }

                // Create position down
                new_row = my_row - 1;
                if (new_row >= 1) {
                    // Creates new position
                    new_position = new ChessPosition(new_row, new_col);

                    // Checks to see if another piece is on the new position
                    ChessPiece piece_inter = board.getPiece(new_position);
                    if (piece_inter != null) {
                        // Check to see team_color
                        if (team_color != piece_inter.getTeamColor()) {
                            // Adds the new position as a possible move
                            new_move = new ChessMove(myPosition, new_position, null);
                            valid_moves.add(new_move);
//                            System.out.println(new_row + ", " + new_col);
                        }
                    } else {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
//                        System.out.println(new_row + ", " + new_col);
                    }
                }
            }


            // Move up twice
            new_row = my_row + 2;
            if (new_row > 8) {
                // Do nothing
            }
            else {
                // Create position right
                new_col = my_col + 1;
                if (new_col <= 8) {
                    // Creates new position
                    new_position = new ChessPosition(new_row, new_col);
                    // Checks to see if another piece is on the new position
                    ChessPiece piece_inter = board.getPiece(new_position);
                    if (piece_inter != null) {
                        // Check to see team_color
                        if (team_color != piece_inter.getTeamColor()) {
                            // Adds the new position as a possible move
                            new_move = new ChessMove(myPosition, new_position, null);
                            valid_moves.add(new_move);
//                          System.out.println(new_row + ", " + new_col);
                        }
                    } else {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
//                      System.out.println(new_row + ", " + new_col);
                    }
                }

                // Create position left
                new_col = my_col - 1;
                if (new_col >= 1) {
                    // Creates new position
                    new_position = new ChessPosition(new_row, new_col);
                    // Checks to see if another piece is on the new position
                    ChessPiece piece_inter = board.getPiece(new_position);
                    if (piece_inter != null) {
                        // Check to see team_color
                        if (team_color != piece_inter.getTeamColor()) {
                            // Adds the new position as a possible move
                            new_move = new ChessMove(myPosition, new_position, null);
                            valid_moves.add(new_move);
//                          System.out.println(new_row + ", " + new_col);
                        }
                    } else {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
//                      System.out.println(new_row + ", " + new_col);
                    }
                }
            }

            // Move down twice
            new_row = my_row - 2;
            if (new_row < 1) {
                // Do nothing
            }
            else {
                // Create position right
                new_col = my_col + 1;
                if (new_col <= 8) {
                    // Creates new position
                    new_position = new ChessPosition(new_row, new_col);

                    // Checks to see if another piece is on the new position
                    ChessPiece piece_inter = board.getPiece(new_position);
                    if (piece_inter != null) {
                        // Check to see team_color
                        if (team_color != piece_inter.getTeamColor()) {
                            // Adds the new position as a possible move
                            new_move = new ChessMove(myPosition, new_position, null);
                            valid_moves.add(new_move);
//                          System.out.println(new_row + ", " + new_col);
                        }
                    } else {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
//                      System.out.println(new_row + ", " + new_col);
                    }
                }

                // Create position left
                new_col = my_col - 1;
                if (new_col >= 1) {
                    // Creates new position
                    new_position = new ChessPosition(new_row, new_col);

                    // Checks to see if another piece is on the new position
                    ChessPiece piece_inter = board.getPiece(new_position);
                    if (piece_inter != null) {
                        // Check to see team_color
                        if (team_color != piece_inter.getTeamColor()) {
                            // Adds the new position as a possible move
                            new_move = new ChessMove(myPosition, new_position, null);
                            valid_moves.add(new_move);
//                          System.out.println(new_row + ", " + new_col);
                        }
                    } else {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
//                      System.out.println(new_row + ", " + new_col);
                    }
                }
            }
        }

        if (piece_type == PieceType.ROOK) {
            // Move up till you can't anymore
            new_col = my_col;
            new_row = my_row;
            while (true) {
                new_col++;
                if (new_col > 8) {
                    break;
                }

                // Creates new position
                new_position = new ChessPosition(new_row, new_col);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(new_position);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (team_color == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
                    }
                    break;
                }
                else {

                    // Adds the new position as a possible move
                    new_move = new ChessMove(myPosition, new_position, null);
                    valid_moves.add(new_move);
                }
            }

            // Move down till you can't anymore
            new_col = my_col;
            while (true) {
                new_col--;
                if (new_col < 1) {
                    break;
                }

                // Creates new position
                new_position = new ChessPosition(new_row, new_col);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(new_position);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (team_color == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
                    }
                    break;
                }
                else {
                    // Adds the new position as a possible move
                    new_move = new ChessMove(myPosition, new_position, null);
                    valid_moves.add(new_move);
                }
            }

            // Move left till you can't anymore
            new_col = my_col;
            new_row = my_row;
            while (true) {
                new_row--;
                if (new_row < 1) {
                    break;
                }

                // Creates new position
                new_position = new ChessPosition(new_row, new_col);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(new_position);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (team_color == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
                    }
                    break;
                }
                else {
                    // Adds the new position as a possible move
                    new_move = new ChessMove(myPosition, new_position, null);
                    valid_moves.add(new_move);
                }
            }

            // Move down left up till you can't anymore
            new_col = my_col;
            new_row = my_row;
            while (true) {
                new_row++;
                if (new_row > 8) {
                    break;
                }

                // Creates new position
                new_position = new ChessPosition(new_row, new_col);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(new_position);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (team_color == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
                    }
                    break;
                }
                else {
                    // Adds the new position as a possible move
                    new_move = new ChessMove(myPosition, new_position, null);
                    valid_moves.add(new_move);
                }
            }
        }

        if (piece_type == PieceType.QUEEN) {
            // Move up till you can't anymore
            new_col = my_col;
            new_row = my_row;
            while (true) {
                new_col++;
                if (new_col > 8) {
                    break;
                }

                // Creates new position
                new_position = new ChessPosition(new_row, new_col);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(new_position);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (team_color == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
                    }
                    break;
                }
                else {

                    // Adds the new position as a possible move
                    new_move = new ChessMove(myPosition, new_position, null);
                    valid_moves.add(new_move);
                }
            }

            // Move down till you can't anymore
            new_col = my_col;
            while (true) {
                new_col--;
                if (new_col < 1) {
                    break;
                }

                // Creates new position
                new_position = new ChessPosition(new_row, new_col);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(new_position);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (team_color == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
                    }
                    break;
                }
                else {
                    // Adds the new position as a possible move
                    new_move = new ChessMove(myPosition, new_position, null);
                    valid_moves.add(new_move);
                }
            }

            // Move left till you can't anymore
            new_col = my_col;
            new_row = my_row;
            while (true) {
                new_row--;
                if (new_row < 1) {
                    break;
                }

                // Creates new position
                new_position = new ChessPosition(new_row, new_col);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(new_position);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (team_color == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
                    }
                    break;
                }
                else {
                    // Adds the new position as a possible move
                    new_move = new ChessMove(myPosition, new_position, null);
                    valid_moves.add(new_move);
                }
            }

            // Move down left up till you can't anymore
            new_col = my_col;
            new_row = my_row;
            while (true) {
                new_row++;
                if (new_row > 8) {
                    break;
                }

                // Creates new position
                new_position = new ChessPosition(new_row, new_col);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(new_position);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (team_color == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
                    }
                    break;
                }
                else {
                    // Adds the new position as a possible move
                    new_move = new ChessMove(myPosition, new_position, null);
                    valid_moves.add(new_move);
                }
            }

            // Move up right till you can't anymore
            new_col = my_col;
            new_row = my_row;
            while (true) {
                new_col++;
                new_row++;
                if ((new_col > 8) || (new_row > 8)) {
                    break;
                }

                // Creates new position
                new_position = new ChessPosition(new_row, new_col);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(new_position);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (team_color == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
                    }
                    break;
                }
                else {

                    // Adds the new position as a possible move
                    new_move = new ChessMove(myPosition, new_position, null);
                    valid_moves.add(new_move);
                }
            }

            // Move right down till you can't anymore
            new_col = my_col;
            new_row = my_row;
            while (true) {
                new_col++;
                new_row--;
                if ((new_col > 8) || (new_row < 1)) {
                    break;
                }

                // Creates new position
                new_position = new ChessPosition(new_row, new_col);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(new_position);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (team_color == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
                    }
                    break;
                }
                else {
                    // Adds the new position as a possible move
                    new_move = new ChessMove(myPosition, new_position, null);
                    valid_moves.add(new_move);
                }
            }

            // Move left down till you can't anymore
            new_col = my_col;
            new_row = my_row;
            while (true) {
                new_col--;
                new_row--;
                if ((new_col < 1) || (new_row < 1)) {
                    break;
                }

                // Creates new position
                new_position = new ChessPosition(new_row, new_col);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(new_position);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (team_color == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
                    }
                    break;
                }
                else {
                    // Adds the new position as a possible move
                    new_move = new ChessMove(myPosition, new_position, null);
                    valid_moves.add(new_move);
                }
            }

            // Move down left up till you can't anymore
            new_col = my_col;
            new_row = my_row;
            while (true) {
                new_col--;
                new_row++;
                if ((new_col < 1) || (new_row > 8)) {
                    break;
                }

                // Creates new position
                new_position = new ChessPosition(new_row, new_col);

                // Checks to see if another piece is on the new position
                ChessPiece piece_inter = board.getPiece(new_position);
                if (piece_inter != null) {
                    // Check to see team_color
                    if (team_color == piece_inter.getTeamColor()) {
                        break;
                    }
                    else {
                        // Adds the new position as a possible move
                        new_move = new ChessMove(myPosition, new_position, null);
                        valid_moves.add(new_move);
                    }
                    break;
                }
                else {
                    // Adds the new position as a possible move
                    new_move = new ChessMove(myPosition, new_position, null);
                    valid_moves.add(new_move);
                }
            }
        }

        return valid_moves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece piece = (ChessPiece) o;
        return team_color == piece.team_color && piece_type == piece.piece_type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(team_color, piece_type);
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "team_color=" + team_color +
                ", piece_type=" + piece_type +
                '}';
    }
}
