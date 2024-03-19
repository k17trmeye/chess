package ui;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ui.EscapeSequences.*;

public class ChessBoardUI {
    private static final int BOARD_SIZE = 10;
    private static final int TILE_SIZE = 5; // Each tile will be a 5x5 square
    private static List<String> whitePieces = new ArrayList<>();
    private static List<String> blackPieces = new ArrayList<>();
    private static List<String> columns = new ArrayList<>();

    public static void main() {
        PrintStream out = System.out;
        InitPieces();

        printChessBoard(out);
        printChessBoardFlipped(out);
    }
    private static void InitPieces() {

        whitePieces.add(WHITE_ROOK);
        whitePieces.add(WHITE_KNIGHT);
        whitePieces.add(WHITE_BISHOP);
        whitePieces.add(WHITE_QUEEN);
        whitePieces.add(WHITE_KING);
        whitePieces.add(WHITE_BISHOP);
        whitePieces.add(WHITE_KNIGHT);
        whitePieces.add(WHITE_ROOK);

        blackPieces.add(BLACK_ROOK);
        blackPieces.add(BLACK_KNIGHT);
        blackPieces.add(BLACK_BISHOP);
        blackPieces.add(BLACK_KING);
        blackPieces.add(BLACK_QUEEN);
        blackPieces.add(BLACK_BISHOP);
        blackPieces.add(BLACK_KNIGHT);
        blackPieces.add(BLACK_ROOK);

        columns.add("a");
        columns.add("b");
        columns.add("c");
        columns.add("d");
        columns.add("e");
        columns.add("f");
        columns.add("g");
        columns.add("h");
    }
    private static void printChessBoard(PrintStream out) {

        for (int row = 0; row < BOARD_SIZE; row++) {
            if (row > 0 && row < 9) {
                out.print((row));
            }
            for (int col = 0; col < 8; col++) {
                if (row == 0) {
                    out.print(EMPTY);
                    out.print(columns.get(col));
                } else if (row == 1) {
                    out.print("|");
                    out.print(whitePieces.get(col));
                } else if (row == 2) {
                    out.print("|");
                    out.print(WHITE_PAWN);
                } else if (row == 7) {
                    out.print("|");
                    out.print(BLACK_PAWN);
                } else if (row ==8) {
                    out.print("|");
                    out.print(blackPieces.get(col));
                }
                else if (row == 9) {
                    out.print(EMPTY);
                    out.print(columns.get(col));
                }
                else {
                    out.print("|");
                    out.print(EMPTY);
                }
            }

            if (row > 0 && row < 9) {
                out.print("|");
                out.print((row));
            }
            out.println();
        }
        out.println();
    }

    private static void printChessBoardFlipped(PrintStream out) {

        for (int row = 9; row >= 0; row--) {
            if (row > 0 && row < 9) {
                out.print((row));
            }
            for (int col = 7; col >= 0; col--) {
                if (row == 0) {
                    out.print(EMPTY);
                    out.print(columns.get(col));
                } else if (row == 1) {
                    out.print("|");
                    out.print(whitePieces.get(col));
                } else if (row == 2) {
                    out.print("|");
                    out.print(WHITE_PAWN);
                } else if (row == 7) {
                    out.print("|");
                    out.print(BLACK_PAWN);
                } else if (row ==8) {
                    out.print("|");
                    out.print(blackPieces.get(col));
                }
                else if (row == 9) {
                    out.print(EMPTY);
                    out.print(columns.get(col));
                }
                else {
                    out.print("|");
                    out.print(EMPTY);
                }
            }

            if (row > 0 && row < 9) {
                out.print("|");
                out.print((row));
            }
            out.println();
        }
    }

}



