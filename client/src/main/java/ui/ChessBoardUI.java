package ui;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;

public class ChessBoardUI {private static final int BOARD_SIZE = 8;
    private static final int TILE_SIZE = 5; // Each tile will be a 5x5 square

    public static void main() {
        PrintStream out = System.out;

        printChessBoard(out);
    }

    private static void printChessBoard(PrintStream out) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int tileRow = 0; tileRow < TILE_SIZE; tileRow++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
                    printTile(out, row, col, tileRow);
                }
                out.println();
            }
        }
    }

    private static void printTile(PrintStream out, int row, int col, int tileRow) {
        boolean isBlack = (row + col) % 2 == 1;
        String tileColor = isBlack ? "BLACK" : "WHITE";

        String tileContent = getTileContent(tileRow);

        out.print(tileContent);
        out.print(tileContent);
        out.print(tileContent);
        out.print(tileContent);
        out.print(tileContent);

        // Adding an extra space for visual separation of tiles
        out.print(" ");
    }

    private static String getTileContent(int tileRow) {
        return tileRow % 2 == 0 ? "███" : "   ";
    }
}



