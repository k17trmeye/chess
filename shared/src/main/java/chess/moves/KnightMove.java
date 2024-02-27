package chess.moves;

import chess.*;

import java.util.ArrayList;

public class KnightMove {
    public ArrayList<ChessMove> knightMoves;

    public KnightMove () {
        knightMoves = new ArrayList<>();
    }

    public ArrayList<ChessMove> knightMoveCal(ChessBoard board, Integer row, Integer col,
                                              ChessGame.TeamColor teamColor, ChessPosition myPosition) {
        ArrayList<ChessMove> knightMoves = new ArrayList<>();

        int[] rowOffsets = {2, 2, -2, -2, 1, 1, -1, -1};
        int[] colOffsets = {1, -1, 1, -1, 2, -2, 2, -2};

        for (int i = 0; i < rowOffsets.length; i++) {
            int newRow = row + rowOffsets[i];
            int newCol = col + colOffsets[i];

            if (newRow >= 1 && newRow <= 8 && newCol >= 1 && newCol <= 8) {
                ChessPosition newPosition = new ChessPosition(newRow, newCol);
                ChessPiece pieceInter = board.getPiece(newPosition);

                if (pieceInter == null || teamColor != pieceInter.getTeamColor()) {
                    knightMoves.add(new ChessMove(myPosition, newPosition, null));
                }
            }
        }

        return knightMoves;
    }


//    public ArrayList<ChessMove> knightMoveCal(ChessBoard board, Integer row, Integer col,
//                                              ChessGame.TeamColor teamColor, ChessPosition myPosition) {
//        ChessPosition newPosition;
//        ChessMove newMove;
//        Integer newCol = col;
//        Integer newRow = row;
//        int myRow = myPosition.getRow();
//        int myCol = myPosition.getColumn();
//        // Move right twice
//        newCol = myCol + 2;
//        if (newCol > 8) {
//            // Do nothing
//        }
//        else {
//            // Create position up
//            newRow = myRow + 1;
//            if (newRow <= 8) {
//                // Creates new position
//                newPosition = new ChessPosition(newRow, newCol);
//
//                // Checks to see if another piece is on the new position
//                ChessPiece pieceInter = board.getPiece(newPosition);
//                if (pieceInter != null) {
//                    // Check to see team_color
//                    if (teamColor != pieceInter.getTeamColor()) {
//                        // Adds the new position as a possible move
//                        newMove = new ChessMove(myPosition, newPosition, null);
//                        knightMoves.add(newMove);
//                    }
//                } else {
//                    // Adds the new position as a possible move
//                    newMove = new ChessMove(myPosition, newPosition, null);
//                    knightMoves.add(newMove);
//                }
//            }
//
//            // Create position down
//            newRow = myRow - 1;
//            if (newRow >= 1) {
//                // Creates new position
//                newPosition = new ChessPosition(newRow, newCol);
//
//                // Checks to see if another piece is on the new position
//                ChessPiece pieceInter = board.getPiece(newPosition);
//                if (pieceInter != null) {
//                    // Check to see team_color
//                    if (teamColor != pieceInter.getTeamColor()) {
//                        // Adds the new position as a possible move
//                        newMove = new ChessMove(myPosition, newPosition, null);
//                        knightMoves.add(newMove);
//                    }
//                } else {
//                    // Adds the new position as a possible move
//                    newMove = new ChessMove(myPosition, newPosition, null);
//                    knightMoves.add(newMove);
//                }
//            }
//        }
//
//        // Move left twice
//        newCol = myCol - 2;
//        if (newCol < 1) {
//            // Do nothing
//        }
//        else {
//            // Create position up
//            newRow = myRow + 1;
//            if (newRow <= 8) {
//                // Creates new position
//                newPosition = new ChessPosition(newRow, newCol);
//
//                // Checks to see if another piece is on the new position
//                ChessPiece pieceInter = board.getPiece(newPosition);
//                if (pieceInter != null) {
//                    // Check to see team_color
//                    if (teamColor != pieceInter.getTeamColor()) {
//                        // Adds the new position as a possible move
//                        newMove = new ChessMove(myPosition, newPosition, null);
//                        knightMoves.add(newMove);
//                    }
//                } else {
//                    // Adds the new position as a possible move
//                    newMove = new ChessMove(myPosition, newPosition, null);
//                    knightMoves.add(newMove);
//                }
//            }
//
//            // Create position down
//            newRow = myRow - 1;
//            if (newRow >= 1) {
//                // Creates new position
//                newPosition = new ChessPosition(newRow, newCol);
//
//                // Checks to see if another piece is on the new position
//                ChessPiece pieceInter = board.getPiece(newPosition);
//                if (pieceInter != null) {
//                    // Check to see team_color
//                    if (teamColor != pieceInter.getTeamColor()) {
//                        // Adds the new position as a possible move
//                        newMove = new ChessMove(myPosition, newPosition, null);
//                        knightMoves.add(newMove);
//                    }
//                } else {
//                    // Adds the new position as a possible move
//                    newMove = new ChessMove(myPosition, newPosition, null);
//                    knightMoves.add(newMove);
//                }
//            }
//        }
//
//
//        // Move up twice
//        newRow = myRow + 2;
//        if (newRow > 8) {
//            // Do nothing
//        }
//        else {
//            // Create position right
//            newCol = myCol + 1;
//            if (newCol <= 8) {
//                // Creates new position
//                newPosition = new ChessPosition(newRow, newCol);
//                // Checks to see if another piece is on the new position
//                ChessPiece pieceInter = board.getPiece(newPosition);
//                if (pieceInter != null) {
//                    // Check to see team_color
//                    if (teamColor != pieceInter.getTeamColor()) {
//                        // Adds the new position as a possible move
//                        newMove = new ChessMove(myPosition, newPosition, null);
//                        knightMoves.add(newMove);
//                    }
//                } else {
//                    // Adds the new position as a possible move
//                    newMove = new ChessMove(myPosition, newPosition, null);
//                    knightMoves.add(newMove);
//                }
//            }
//
//            // Create position left
//            newCol = myCol - 1;
//            if (newCol >= 1) {
//                // Creates new position
//                newPosition = new ChessPosition(newRow, newCol);
//                // Checks to see if another piece is on the new position
//                ChessPiece pieceInter = board.getPiece(newPosition);
//                if (pieceInter != null) {
//                    // Check to see team_color
//                    if (teamColor != pieceInter.getTeamColor()) {
//                        // Adds the new position as a possible move
//                        newMove = new ChessMove(myPosition, newPosition, null);
//                        knightMoves.add(newMove);
//                    }
//                } else {
//                    // Adds the new position as a possible move
//                    newMove = new ChessMove(myPosition, newPosition, null);
//                    knightMoves.add(newMove);
//                }
//            }
//        }
//
//        // Move down twice
//        newRow = myRow - 2;
//        if (newRow < 1) {
//            // Do nothing
//        }
//        else {
//            // Create position right
//            newCol = myCol + 1;
//            if (newCol <= 8) {
//                // Creates new position
//                newPosition = new ChessPosition(newRow, newCol);
//
//                // Checks to see if another piece is on the new position
//                ChessPiece pieceInter = board.getPiece(newPosition);
//                if (pieceInter != null) {
//                    // Check to see team_color
//                    if (teamColor != pieceInter.getTeamColor()) {
//                        // Adds the new position as a possible move
//                        newMove = new ChessMove(myPosition, newPosition, null);
//                        knightMoves.add(newMove);
//                    }
//                } else {
//                    // Adds the new position as a possible move
//                    newMove = new ChessMove(myPosition, newPosition, null);
//                    knightMoves.add(newMove);
//                }
//            }
//
//            // Create position left
//            newCol = myCol - 1;
//            if (newCol >= 1) {
//                // Creates new position
//                newPosition = new ChessPosition(newRow, newCol);
//
//                // Checks to see if another piece is on the new position
//                ChessPiece pieceInter = board.getPiece(newPosition);
//                if (pieceInter != null) {
//                    // Check to see team_color
//                    if (teamColor != pieceInter.getTeamColor()) {
//                        // Adds the new position as a possible move
//                        newMove = new ChessMove(myPosition, newPosition, null);
//                        knightMoves.add(newMove);
//                    }
//                } else {
//                    // Adds the new position as a possible move
//                    newMove = new ChessMove(myPosition, newPosition, null);
//                    knightMoves.add(newMove);
//                }
//            }
//        }
//        return knightMoves;
//    }
}
