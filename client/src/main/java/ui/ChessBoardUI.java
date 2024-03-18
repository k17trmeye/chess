package ui;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;

public class ChessBoardUI {
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 4; // Adjusted for better square representation
    private static final int LINE_WIDTH_IN_CHARS = 1;
    private static final String EMPTY = "    ";
    private static Random rand = new Random();

    public static void main() {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawHeaders(out);

        drawChessBoard(out);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawHeaders(PrintStream out) {
        setBlack(out);

        String[] headers = { "a", "b", "c", "d", "e", "f", "g", "h" };
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            drawHeader(out, headers[boardCol]);

            if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
                out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
            }
        }

        out.println();
    }

    private static void drawHeader(PrintStream out, String headerText) {
        int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
        int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - headerText.length();

        out.print(EMPTY.repeat(prefixLength));
        printHeaderText(out, headerText);
        out.print(EMPTY.repeat(suffixLength));
    }

    private static void printHeaderText(PrintStream out, String headerText) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_GREEN);

        out.print(headerText);

        setBlack(out);
    }

    private static void drawChessBoard(PrintStream out) {
        boolean isBlackSquare = false; // Flag to alternate between black and white squares

        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {
            for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_CHARS; ++squareRow) {
                for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                    setWhite(out);

                    if (isBlackSquare) {
                        out.print(SET_BG_COLOR_BLACK);
                    }

                    out.print(EMPTY);

                    if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
                        out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
                    }

                    setBlack(out);
                }

                isBlackSquare = !isBlackSquare;
                out.println();
            }
        }
    }

    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }
}
