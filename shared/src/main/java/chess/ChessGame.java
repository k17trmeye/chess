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
    // Working
    private TeamColor teamColor;
    private ChessBoard chessBoard;

    public ChessGame() {
        chessBoard = new ChessBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamColor;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamColor = team;
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
        ChessPiece selectedPiece = chessBoard.getPiece(startPosition);
        Collection<ChessMove> allMoves;
        ArrayList<ChessMove> validMoves = new ArrayList<>();
        ChessBoard tempBoard = chessBoard;

        char[][] printBoard;
        ChessPiece enemyPiece = null;
        boolean enemyHere = false;

        // If there is a piece, return the positions it can take given the board and the start position
        if (selectedPiece != null) {
            // Grab all moves
            allMoves =  selectedPiece.pieceMoves(chessBoard, startPosition);

            // Iterate through all moves to check which ones don't leave the King in check
            for (ChessMove eachMove : allMoves) {
                // Check if end position is on an enemy
                if (chessBoard.getPiece(eachMove.getEndPosition()) != null) {
                    if (chessBoard.getPiece(eachMove.getEndPosition()).getTeamColor() != selectedPiece.getTeamColor()) {
                        enemyPiece = chessBoard.getPiece(eachMove.getEndPosition());
                        enemyHere = true;
                    }
                }

                // Add a move the piece to the new position on the board
                tempBoard.addPiece(eachMove.getEndPosition(), new ChessPiece(selectedPiece.getTeamColor(), selectedPiece.getPieceType()));

                // Add a null piece to the previous position
                tempBoard.addPiece(eachMove.getStartPosition(), null);

                // Make sure that move doesn't leave the king in check
                if (isInCheck(selectedPiece.getTeamColor())) {
                    // Undo previous move
                    // Add a move the piece to the new position on the board
                    tempBoard.addPiece(eachMove.getEndPosition(), null);

                    // Add a null piece to the previous position
                    tempBoard.addPiece(eachMove.getStartPosition(), new ChessPiece(selectedPiece.getTeamColor(), selectedPiece.getPieceType()));
                }
                else {
                    // Undo previous move
                    // Add a move the piece to the new position on the board
                    if (enemyHere) {
                        tempBoard.addPiece(eachMove.getEndPosition(), enemyPiece);
                        enemyHere = false;
                    } else {
                        tempBoard.addPiece(eachMove.getEndPosition(), null);
                    }

                    // Add a null piece to the previous position
                    tempBoard.addPiece(eachMove.getStartPosition(), new ChessPiece(selectedPiece.getTeamColor(), selectedPiece.getPieceType()));

                    // Add the valid move to the Collection
                    validMoves.add(eachMove);
                }
            }
        }
        if (!validMoves.isEmpty()) {
            return validMoves;
        }

        // If there is no piece there, return null
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        // Grabs a collection of valid_moves
        Collection<ChessMove> validMoves;
        validMoves = validMoves(move.getStartPosition());

        // Boolean to set if any pieces are added
        boolean piecesAdded = false;

        ChessPiece.PieceType pieceType = chessBoard.getPiece(move.getStartPosition()).getPieceType();
        ChessPiece.PieceType promotionType = move.getPromotionPiece();

        // Check to see that the move is the right team color
        if (validMoves == null) {
            throw new InvalidMoveException();
        }
        else {
            if ((chessBoard.getPiece(move.getStartPosition()).getTeamColor() == teamColor) &&
                    (chessBoard.getPiece(move.getStartPosition()) != null)) {
                // Loop through the Collection
                for (ChessMove singleMove : validMoves) {
                    // If the move is in the collection of valid_moves, make the move
                    if ((move.getEndPosition().getRow() == singleMove.getEndPosition().getRow()) &&
                            (move.getEndPosition().getColumn() == singleMove.getEndPosition().getColumn())) {
                        if ((pieceType == ChessPiece.PieceType.PAWN) && (promotionType != null)) {
                            // Add a move the piece to the new position on the board
                            chessBoard.addPiece(move.getEndPosition(), new ChessPiece(getTeamTurn(), promotionType));

                            // Add a null piece to the previous position
                            chessBoard.addPiece(move.getStartPosition(), null);

                            // Set boolean true
                            piecesAdded = true;

                            // Make sure that move doesn't leave the king in check
                            if (isInCheck(teamColor)) {
                                // If it enters in this loop, undo previous move
                                // Add a move the piece to the new position on the board
                                chessBoard.addPiece(move.getEndPosition(), null);

                                // Add a null piece to the previous position
                                chessBoard.addPiece(move.getStartPosition(), new ChessPiece(getTeamTurn(), promotionType));
                                throw new InvalidMoveException();
                            }
                        }
                        else {

                            // Add a move the piece to the new position on the board
                            chessBoard.addPiece(move.getEndPosition(), new ChessPiece(getTeamTurn(), pieceType));

                            // Add a null piece to the previous position
                            chessBoard.addPiece(move.getStartPosition(), null);

                            // Set boolean true
                            piecesAdded = true;

                            // Make sure that move doesn't leave the king in check
                            if (isInCheck(teamColor)) {
                                // If it enters in this loop, undo previous move
                                // Add a move the piece to the new position on the board
                                chessBoard.addPiece(move.getEndPosition(), null);

                                // Add a null piece to the previous position
                                chessBoard.addPiece(move.getStartPosition(), new ChessPiece(getTeamTurn(), pieceType));
                                throw new InvalidMoveException();
                            }
                        }
                    }
                }
                if (!piecesAdded) {
                    throw new InvalidMoveException();
                }
            }
            else {
                throw new InvalidMoveException();
            }
        }

        // Change move to other team
        if (teamColor == TeamColor.WHITE) {
            setTeamTurn(TeamColor.BLACK);
        } else if (teamColor == TeamColor.BLACK) {
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
        ChessPiece tempPiece = null;
        int myRow = 0;
        int myCol = 0;

        // Find the king given the team color on the board
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                // Gets a piece from the board
                tempPiece = chessBoard.getPiece(new ChessPosition(i, j));

                // If the piece is a King with the same color, assign it to kingPiece
                if (tempPiece != null) {
                    if ((tempPiece.getPieceType() != null) && (tempPiece.getTeamColor() != null)) {
                        if (tempPiece.getTeamColor() == teamColor) {
                            if (tempPiece.getPieceType() == ChessPiece.PieceType.KING) {
                                myRow = i;
                                myCol = j;
                                break;
                            }
                        }
                    }
                }
            }
        }

        // Create position of king
        ChessPosition kingPos = new ChessPosition(myRow, myCol);

        // Get the valid move from the other team and put into a Collection
        Collection<ChessMove> opposingMoves;

        // Find iterate through all enemy pieces on the board
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                // Gets a piece from the board
                tempPiece = chessBoard.getPiece(new ChessPosition(i, j));
                // If there is a piece there
                if (tempPiece != null) {
                    // If the piece is an enemy
                    if ((teamColor != tempPiece.getTeamColor()) && (null != tempPiece.getTeamColor())) {
                        // Get all moves the enemy piece can take
                        opposingMoves = tempPiece.pieceMoves(chessBoard, new ChessPosition(i, j));
                        // If there are moves that piece can make
                        if (!opposingMoves.isEmpty()) {
                            // Check each moves end position
                            for (ChessMove newMove : opposingMoves) {
                                // If the end position is equal to the king's position
                                if ((newMove.getEndPosition().getRow() == kingPos.getRow()) &&
                                        (newMove.getEndPosition().getColumn() == kingPos.getColumn())) {
                                    return true;
                                }
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
        ChessPiece kingPiece = null;
        ChessPiece tempPiece;
        int myRow = 0;
        int myCol = 0;

        // Find the king given the team color on the board
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                // Gets a piece from the board
                tempPiece = chessBoard.getPiece(new ChessPosition(i, j));
                // If the piece is a King with the same color, assign it to kingPiece
                if (tempPiece != null) {
                    if (tempPiece.getTeamColor() == teamColor) {
                        if (tempPiece.getPieceType() == ChessPiece.PieceType.KING) {
                            kingPiece = tempPiece;
                            // Save the position to myRow, myCol
                            myRow = i;
                            myCol = j;
                        }
                    }
                }
            }
        }

        // Find what moves the King can make
        Collection<ChessMove> kingMoves = null;
        if(kingPiece.getPieceType() == ChessPiece.PieceType.KING) {
            kingMoves = kingPiece.pieceMoves(chessBoard, new ChessPosition(myRow, myCol));
        }

        // Makes a move for each move in kingMoves and checks if he is inCheck
        int counter = 0;
        if ((kingMoves != null) && (!kingMoves.isEmpty())) {
//            System.out.println(kingMoves.size());
            for (ChessMove myMove : kingMoves) {
                // Add a move the piece to the new position on the board
                chessBoard.addPiece(myMove.getEndPosition(), new ChessPiece(getTeamTurn(), ChessPiece.PieceType.KING));

                // Add a null piece to the previous position
                chessBoard.addPiece(myMove.getStartPosition(), null);

                if (isInCheck(teamColor)) {
                    // If it enters in this loop, undo previous move
                    // Add a move the piece to the new position on the board
                    chessBoard.addPiece(myMove.getEndPosition(), null);

                    // Add a null piece to the previous position
                    chessBoard.addPiece(myMove.getStartPosition(), new ChessPiece(getTeamTurn(), ChessPiece.PieceType.KING));

                    counter += 1;

                }
            }
        }
        else {
            return false;
        }

        // If he cannot make moves, return false
        if (kingMoves.size() == counter) {
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
        Collection<ChessMove> myMoves = null;
        ChessPiece myPiece = null;
        ChessPiece tempPiece;
        int myRow = 0;
        int myCol = 0;

        // Find the king given the team color on the board
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                // Gets a piece from the board
                tempPiece = chessBoard.getPiece(new ChessPosition(i, j));
                // If the piece is the same color as teamColor, assign it to tempPiece
                if (tempPiece != null) {
                    if (tempPiece.getTeamColor() == teamColor) {
                        myPiece = tempPiece;
                        myMoves = myPiece.pieceMoves(chessBoard, new ChessPosition(i, j));
                    }
                }
            }
        }

        // If he cannot make moves, return false
        if (!myMoves.isEmpty()) {
            if (this.teamColor == teamColor) {
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
        chessBoard = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return chessBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return teamColor == chessGame.teamColor && Objects.equals(chessBoard, chessGame.chessBoard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamColor, chessBoard);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "team_color=" + teamColor +
                ", game_board=" + chessBoard +
                '}';
    }
}
