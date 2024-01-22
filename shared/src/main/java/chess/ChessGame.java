package chess;

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
        ChessPiece selected_piece = game_board.getPiece(startPosition);
//        Collection<ChessMove> moves = selected_piece.pieceMoves(game_board, startPosition);
        return selected_piece.pieceMoves(game_board, startPosition);
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());
        // Chess piece cannot move there
        if (validMoves.contains(move)) {
            // Adds a new piece to the board using the end position of the move and the piece at the move
            game_board.addPiece(move.getEndPosition(), game_board.getPiece(move.getStartPosition()));
            // Create a piece at the start position that is null
            game_board.addPiece(move.getStartPosition(), null);
        }
        // Move will leave king in danger
//        else if () {
//
//        }

        // It’s not the corresponding team's turn.
        // Compares the color of the team in turn to the piece's color in the start position
        else if (getTeamTurn() == game_board.getPiece(move.getStartPosition()).getTeamColor()) {
            // Adds a new piece to the board using the end position of the move and the piece at the move
            game_board.addPiece(move.getEndPosition(), game_board.getPiece(move.getStartPosition()));
            // Create a piece at the start position that is null
            game_board.addPiece(move.getStartPosition(), null);
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
    
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        // Find the king given the team color on the board

        // Find what moves the King can make

        // If he can make moves, return true

        // If he cannot make moves, return false
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
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
}
