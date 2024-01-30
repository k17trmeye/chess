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

        // If there is a piece, return the positions it can take given the board and the start position
        if (selected_piece != null) {
            if (!isInCheck(team_color)) {
                return selected_piece.pieceMoves(game_board, startPosition);
            }
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
        Collection<ChessMove> valid_moves = validMoves(move.getStartPosition());

        // Check to see that the move is the right team color
        if ((game_board.getPiece(move.getStartPosition()).getTeamColor() == team_color) && (valid_moves != null)) {
            // Loop through the Collection
            for (ChessMove single_move : valid_moves) {
                // If the move is in the collection of valid_moves, make the move
                if ((move.getEndPosition().getRow() == single_move.getEndPosition().getRow()) &&
                        (move.getEndPosition().getColumn() == single_move.getEndPosition().getColumn())) {
                    // Add a move the piece to the new position on the board
                    game_board.addPiece(move.getEndPosition(), new ChessPiece(getTeamTurn(), game_board.getPiece(move.getStartPosition()).getPieceType()));

                    // Add a null piece to the previous position
                    game_board.addPiece(move.getStartPosition(), null);

                    // Make sure that move doesn't leave the king in check
                    if (isInCheck(team_color)) {
                        // If it enters in this loop, undo previous move
                        // Add a move the piece to the new position on the board
                        game_board.addPiece(move.getEndPosition(), null);

                        // Add a null piece to the previous position
                        game_board.addPiece(move.getStartPosition(), new ChessPiece(getTeamTurn(),
                                game_board.getPiece(move.getStartPosition()).getPieceType()));
                        throw new InvalidMoveException();
                    }
                }
            }
            if (valid_moves == null) {
                throw new InvalidMoveException();
            }

        }
        else {
            throw new InvalidMoveException();
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
                    if (temp_piece.getTeamColor() == teamColor) {
                        if (temp_piece.getPieceType() == ChessPiece.PieceType.KING) {
                            my_row = i;
                            my_col = j;
                            break;
                        }
                    }
                }
            }
        }

        // Create position of king
        ChessPosition king_pos = new ChessPosition(my_row, my_col);

        // Get the valid move from the other team and put into a Collection
        Collection<ChessMove> opposing_moves = new ArrayList<>();
        Collection<ChessMove> total_moves = new ArrayList<>();
        // Find the king given the team color on the board
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                // Gets a piece from the board
                temp_piece = game_board.getPiece(new ChessPosition(i, j));
                // If the piece is a King with the same color, assign it to king_piece
                if (temp_piece != null) {
                    if (teamColor != temp_piece.getTeamColor()) {
                        opposing_moves = temp_piece.pieceMoves(game_board, new ChessPosition(i, j));
                        if (!opposing_moves.isEmpty()) {
                            for (ChessMove new_move : opposing_moves) {
                                if ((new_move.getEndPosition().getRow() == king_pos.getRow()) &&
                                        (new_move.getEndPosition().getColumn() == king_pos.getColumn())) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
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

        // Find what moves the King can make
        Collection<ChessMove> king_moves = null;
        if(king_piece.getPieceType() != null) {
            king_moves = king_piece.pieceMoves(game_board, new ChessPosition(my_row, my_col));
        }

        // If he cannot make moves, return false
        if (king_moves.isEmpty()) {
            return false;
        }
        // If he can make moves, return true
        else {
            return true;
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
                if (temp_piece.getTeamColor() == teamColor) {
                    my_piece = temp_piece;
                    my_moves = my_piece.pieceMoves(game_board, new ChessPosition(i, j));


                }
            }
        }

        // If he cannot make moves, return false
        if (my_moves.isEmpty()) {
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
}
