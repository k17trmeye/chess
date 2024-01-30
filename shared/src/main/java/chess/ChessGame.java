package chess;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Collection;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor team_color;
    private ChessBoard game_board;

    public ChessGame() {
        game_board = new ChessBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return team_color;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        team_color = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        // Get the piece at that position
        ChessPiece selected_piece = game_board.getPiece(startPosition);
        Collection<ChessMove> all_moves;
        Collection<ChessMove> valid_moves = new ArrayList<>();
        ChessBoard temp_board = game_board;

        char[][] printBoard;
        ChessPiece enemyPiece = null;
        boolean enemyHere = false;

        // If there is a piece, return the positions it can take given the board and the start position
        if (selected_piece != null) {
            // Grab all moves
            all_moves =  selected_piece.pieceMoves(game_board, startPosition);

            // Iterate through all moves to check which ones don't leave the King in check
            for (ChessMove each_move : all_moves) {
                // Print the board
//                printBoard = temp_board.getBoard();
//                printCharArray(printBoard);

                // Check if end position is on an enemy
                if (game_board.getPiece(each_move.getEndPosition()) != null) {
                    if (game_board.getPiece(each_move.getEndPosition()).getTeamColor() != selected_piece.getTeamColor()) {
                        enemyPiece = game_board.getPiece(each_move.getEndPosition());
                        enemyHere = true;
                    }
                }

                // Add a move the piece to the new position on the board
                temp_board.addPiece(each_move.getEndPosition(), new ChessPiece(selected_piece.getTeamColor(), selected_piece.getPieceType()));

                // Add a null piece to the previous position
                temp_board.addPiece(each_move.getStartPosition(), null);

//                System.out.println("Is " + selected_piece.getTeamColor() + " in check after " + selected_piece.getTeamColor() + " " +
//                        selected_piece.getPieceType() + " moves from " + each_move.getStartPosition() + " to " + each_move.getEndPosition());
                // Make sure that move doesn't leave the king in check
                if (isInCheck(selected_piece.getTeamColor())) {
                    // Undo previous move
                    // Add a move the piece to the new position on the board
                    temp_board.addPiece(each_move.getEndPosition(), null);

                    // Add a null piece to the previous position
                    temp_board.addPiece(each_move.getStartPosition(), new ChessPiece(selected_piece.getTeamColor(), selected_piece.getPieceType()));
                }
                else {
                    // Undo previous move
                    // Add a move the piece to the new position on the board
                    if (enemyHere) {
                        temp_board.addPiece(each_move.getEndPosition(), enemyPiece);
                        enemyHere = false;
                    } else {
                        temp_board.addPiece(each_move.getEndPosition(), null);
                    }

                    // Add a null piece to the previous position
                    temp_board.addPiece(each_move.getStartPosition(), new ChessPiece(selected_piece.getTeamColor(), selected_piece.getPieceType()));

                    // Add the valid move to the Collection
//                    System.out.println("Valid Move Added: " + each_move.getStartPosition() + "; " + each_move.getEndPosition());
//                    System.out.println();
                    valid_moves.add(each_move);
                }
            }
        }
        if (!valid_moves.isEmpty()) {
            return valid_moves;
        }

        // If there is no piece there, return null
        return null;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        // Grabs a collection of valid_moves
        Collection<ChessMove> valid_moves;
        valid_moves = validMoves(move.getStartPosition());

        // Boolean to set if any pieces are added
        boolean pieces_added = false;

        ChessPiece.PieceType piece_type = game_board.getPiece(move.getStartPosition()).getPieceType();
        ChessPiece.PieceType promotion_type = move.getPromotionPiece();

        // Check to see that the move is the right team color
        if (valid_moves == null) {
            throw new InvalidMoveException();
        }
        else {
            if ((game_board.getPiece(move.getStartPosition()).getTeamColor() == team_color) &&
                    (game_board.getPiece(move.getStartPosition()) != null)) {
                // Loop through the Collection
                for (ChessMove single_move : valid_moves) {
                    // If the move is in the collection of valid_moves, make the move
                    if ((move.getEndPosition().getRow() == single_move.getEndPosition().getRow()) &&
                            (move.getEndPosition().getColumn() == single_move.getEndPosition().getColumn())) {
                        if ((piece_type == ChessPiece.PieceType.PAWN) && (promotion_type != null)) {
                            // Add a move the piece to the new position on the board
                            game_board.addPiece(move.getEndPosition(), new ChessPiece(getTeamTurn(), promotion_type));

                            // Add a null piece to the previous position
                            game_board.addPiece(move.getStartPosition(), null);

                            // Set boolean true
                            pieces_added = true;

                            // Make sure that move doesn't leave the king in check
                            if (isInCheck(team_color)) {
                                // If it enters in this loop, undo previous move
                                // Add a move the piece to the new position on the board
                                game_board.addPiece(move.getEndPosition(), null);

                                // Add a null piece to the previous position
                                game_board.addPiece(move.getStartPosition(), new ChessPiece(getTeamTurn(), promotion_type));
                                throw new InvalidMoveException();
                            }
                        }
                        else {

                            // Add a move the piece to the new position on the board
                            game_board.addPiece(move.getEndPosition(), new ChessPiece(getTeamTurn(), piece_type));

                            // Add a null piece to the previous position
                            game_board.addPiece(move.getStartPosition(), null);

                            // Set boolean true
                            pieces_added = true;

                            // Make sure that move doesn't leave the king in check
                            if (isInCheck(team_color)) {
                                // If it enters in this loop, undo previous move
                                // Add a move the piece to the new position on the board
                                game_board.addPiece(move.getEndPosition(), null);

                                // Add a null piece to the previous position
                                game_board.addPiece(move.getStartPosition(), new ChessPiece(getTeamTurn(), piece_type));
                                throw new InvalidMoveException();
                            }
                        }
                    }
                }
                if (!pieces_added) {
                    throw new InvalidMoveException();
                }
            }
            else {
                throw new InvalidMoveException();
            }
        }

        // Change move to other team
        if (team_color == TeamColor.WHITE) {
            setTeamTurn(TeamColor.BLACK);
        } else if (team_color == TeamColor.BLACK) {
            setTeamTurn(TeamColor.WHITE);
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        // Variable to hold the King piece and temp piece
        ChessPiece temp_piece = null;
        int my_row = 0;
        int my_col = 0;

        // Find the king given the team color on the board
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                // Gets a piece from the board
                temp_piece = game_board.getPiece(new ChessPosition(i, j));

                // If the piece is a King with the same color, assign it to king_piece
                if (temp_piece != null) {
                    if ((temp_piece.getPieceType() != null) && (temp_piece.getTeamColor() != null)) {
                        if (temp_piece.getTeamColor() == teamColor) {
                            if (temp_piece.getPieceType() == ChessPiece.PieceType.KING) {
                                my_row = i;
                                my_col = j;
//                                System.out.println("King Found: " + my_row + ", " + my_col);
                                break;
                            }
                        }
                    }
                }
            }
        }

        // Create position of king
        ChessPosition king_pos = new ChessPosition(my_row, my_col);

        // Get the valid move from the other team and put into a Collection
        Collection<ChessMove> opposing_moves;

        // Find iterate through all enemy pieces on the board
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                // Gets a piece from the board
                temp_piece = game_board.getPiece(new ChessPosition(i, j));
                // If there is a piece there
                if (temp_piece != null) {
//                    System.out.println("Checking piece " + temp_piece.getTeamColor() + " " + temp_piece.getPieceType() + ": " + i + ", " + j);
                    // If the piece is an enemy
                    if ((teamColor != temp_piece.getTeamColor()) && (null != temp_piece.getTeamColor())) {
                        // Get all moves the enemy piece can take
                        opposing_moves = temp_piece.pieceMoves(game_board, new ChessPosition(i, j));
                        // If there are moves that piece can make
                        if (!opposing_moves.isEmpty()) {
                            // Check each moves end position
                            for (ChessMove new_move : opposing_moves) {
                                // If the end position is equal to the king's position
                                if ((new_move.getEndPosition().getRow() == king_pos.getRow()) &&
                                        (new_move.getEndPosition().getColumn() == king_pos.getColumn())) {
//                                    System.out.println("In Check: Start_pos: " + new_move.getStartPosition().getRow() + ", " + new_move.getStartPosition().getColumn()
//                                            + " End_pos: "+ new_move.getEndPosition().getRow() + ", " + new_move.getEndPosition().getColumn());
//                                    System.out.println();
                                    return true;
                                }
//                                System.out.println("No Danger: Start_pos: " + new_move.getStartPosition().getRow() + ", " + new_move.getStartPosition().getColumn()
//                                        + "; End_pos: "+ new_move.getEndPosition().getRow() + ", " + new_move.getEndPosition().getColumn());
                            }
                        }
                    }
                }
            }
        }
//        System.out.println("Not in check");
//        System.out.println();
        return false;

    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        // Variable to hold the King piece and temp piece
        ChessPiece king_piece = null;
        ChessPiece temp_piece;
        int my_row = 0;
        int my_col = 0;

        // Find the king given the team color on the board
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                // Gets a piece from the board
                temp_piece = game_board.getPiece(new ChessPosition(i, j));
                // If the piece is a King with the same color, assign it to king_piece
                if (temp_piece != null) {
                    if (temp_piece.getTeamColor() == teamColor) {
                        if (temp_piece.getPieceType() == ChessPiece.PieceType.KING) {
                            king_piece = temp_piece;
                            // Save the position to my_row, my_col
                            my_row = i;
                            my_col = j;
                        }
                    }
                }
            }
        }

        // Find what moves the King can make
        Collection<ChessMove> king_moves = null;
        if(king_piece.getPieceType() == ChessPiece.PieceType.KING) {
            king_moves = king_piece.pieceMoves(game_board, new ChessPosition(my_row, my_col));
        }

        // Makes a move for each move in king_moves and checks if he is inCheck
        int counter = 0;
        if ((king_moves != null) && (!king_moves.isEmpty())) {
//            System.out.println(king_moves.size());
            for (ChessMove my_move : king_moves) {
                // Add a move the piece to the new position on the board
                game_board.addPiece(my_move.getEndPosition(), new ChessPiece(getTeamTurn(), ChessPiece.PieceType.KING));

                // Add a null piece to the previous position
                game_board.addPiece(my_move.getStartPosition(), null);

                if (isInCheck(teamColor)) {
                    // If it enters in this loop, undo previous move
                    // Add a move the piece to the new position on the board
                    game_board.addPiece(my_move.getEndPosition(), null);

                    // Add a null piece to the previous position
                    game_board.addPiece(my_move.getStartPosition(), new ChessPiece(getTeamTurn(), ChessPiece.PieceType.KING));

                    counter += 1;

                }
            }
        }
        else {
            return false;
        }

        // If he cannot make moves, return false
        if (king_moves.size() == counter) {
            return true;
        }
        // If he can make moves, return true
        else {
            return false;
        }
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        // Variable to hold the King piece and temp piece
        Collection<ChessMove> my_moves = null;
        ChessPiece my_piece = null;
        ChessPiece temp_piece;
        int my_row = 0;
        int my_col = 0;

        // Find the king given the team color on the board
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                // Gets a piece from the board
                temp_piece = game_board.getPiece(new ChessPosition(i, j));
                // If the piece is the same color as teamColor, assign it to temp_piece
                if (temp_piece != null) {
                    if (temp_piece.getTeamColor() == teamColor) {
                        my_piece = temp_piece;
                        my_moves = my_piece.pieceMoves(game_board, new ChessPosition(i, j));


                    }
                }
            }
        }

        // If he cannot make moves, return false
        if (!my_moves.isEmpty()) {
            if (team_color == teamColor) {
                return true;
            }
            return false;
        }
        // If he can make moves, return true
        else {
            return true;
        }
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        game_board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return game_board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return team_color == chessGame.team_color && Objects.equals(game_board, chessGame.game_board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team_color, game_board);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "team_color=" + team_color +
                ", game_board=" + game_board +
                '}';
    }
    public static void printCharArray(char[][] array) {
        for (int row = 0; row < array.length; row++) {
            for (int col = 0; col < array[row].length; col++) {
//                System.out.print(array[row][col] + " ");
            }
//            System.out.println();  // Move to the next line after printing each row
        }
    }
}
