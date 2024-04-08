package ui;

import chess.ChessGame;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static ui.EscapeSequences.*;
import static ui.EscapeSequences.SET_TEXT_COLOR_RED;
public class ChessBoardUIBlack {
    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 3;
    private static final int LINE_WIDTH_IN_CHARS = 1;
    private static final String EMPTY = "   ";
    private static boolean alternate = false;
    private static List<String> chessPieces = new ArrayList<>();
    private static ChessGame chessGame;
    public static void main(ChessGame newChessGame, String currColor) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        init();
        chessGame = newChessGame;
        out.print(ERASE_SCREEN);
        if (currColor == null) {
            drawHeadersWhite(out);
            drawTicTacToeBoardWhite(out);
            drawHeadersWhite(out);
        } else if (currColor.toLowerCase().contains("white")) {
            drawHeadersWhite(out);
            drawTicTacToeBoardWhite(out);
            drawHeadersWhite(out);
        } else if (currColor.toLowerCase().contains("black")) {
            drawHeadersBlack(out);
            drawTicTacToeBoardBlack(out);
            drawHeadersBlack(out);
        }
        out.print("\u001B[0m");
        out.print("\u001B[0m");
        out.println();
        out.println();
        System.out.print("[GAMEPLAY] >>> ");
    }
    public static void showMoves(ChessGame newChessGame, List<int[]> moves, String currColor) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        init();
        chessGame = newChessGame;
        out.print(ERASE_SCREEN);
        if (currColor == null) {
            drawHeadersWhite(out);
            drawMovesWhite(out, moves);
            drawHeadersWhite(out);
        } else if (currColor.toLowerCase().contains("white")) {
            drawHeadersWhite(out);
            drawMovesWhite(out, moves);
            drawHeadersWhite(out);
        } else if (currColor.toLowerCase().contains("black")) {
            drawHeadersBlack(out);
            drawMovesBlack(out, moves);
            drawHeadersBlack(out);
        }
        out.print("\u001B[0m");
        out.print("\u001B[0m");
        out.println();
        out.println();
    }
    public static void init() {
        chessPieces.add("  R");
        chessPieces.add("  N");
        chessPieces.add("  B");
        chessPieces.add("  Q");
        chessPieces.add("  K");
        chessPieces.add("  B");
        chessPieces.add("  N");
        chessPieces.add("  R");
    }
    private static void drawHeadersWhite(PrintStream out) {
        out.print("\u001B[0m");
        out.print(EMPTY);
        String[] headers = { " a", "  b", "  c", "  d", "  e", "  f", "  g", "  h" };
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            drawHeader(out, headers[boardCol]);
            if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
                out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
            }
        }
        out.println();
    }
    private static void drawHeadersBlack(PrintStream out) {
        out.print("\u001B[0m");
        out.print(EMPTY);
        String[] headers = { " h", "  g", "  f", "  e", "  d", "  c", "  b", "  a" };
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
        int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;
        out.print(EMPTY.repeat(prefixLength));
        printHeaderText(out, headerText);
        out.print(EMPTY.repeat(suffixLength));
    }
    private static void printHeaderText(PrintStream out, String player) {
        out.print("\u001B[0m");
        out.print(SET_TEXT_COLOR_WHITE);
        out.print(player);

    }
    private static void printRowNum(PrintStream out, String player) {
        out.print("\u001B[0m");
        out.print(SET_TEXT_COLOR_WHITE);
        out.print(player);
    }
    private static void drawTicTacToeBoardWhite(PrintStream out) {
        for (int boardRow = BOARD_SIZE_IN_SQUARES - 1; boardRow >= 0 ; --boardRow) {
            alternate = !alternate;
            for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_CHARS; ++squareRow) {
                if (squareRow == 1) {
                    Integer newRow = boardRow + 1;
                    printRowNum(out, newRow.toString() + " ");
                }
                else {
                    printRowNum(out, "  ");
                }
                for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                    if (alternate) {
                        setBlack(out);
                    }
                    else {
                        setWhite(out);
                    }
                    alternate = !alternate;
                    if (squareRow == SQUARE_SIZE_IN_CHARS / 2) {
                        int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
                        int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

                        drawPlayerWhite(boardRow, boardCol, out, prefixLength, suffixLength);
                    }
                    else {
                        out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                    }
                    if (boardCol < BOARD_SIZE_IN_SQUARES) {
                        out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
                    }
                    out.print("\u001B[0m");
                }
                if (squareRow == 1) {
                    Integer newRow = boardRow + 1;
                    printRowNum(out, " " + newRow.toString());
                }
                else {
                    printRowNum(out, "  ");
                }
                out.println();
            }
            if (boardRow < BOARD_SIZE_IN_SQUARES - 1) {
                setBlack(out);
            }
        }
    }
    private static void drawTicTacToeBoardBlack(PrintStream out) {
        for (int i1 = 0; i1 < BOARD_SIZE_IN_SQUARES; ++i1) {
            alternate = !alternate;
            for (int i2 = 0; i2 < SQUARE_SIZE_IN_CHARS; ++i2) {
                if (i2 == 1) {
                    Integer newRow = i1 + 1;
                    printRowNum(out, newRow.toString() + " ");
                }
                else {
                    printRowNum(out, "  ");
                }
                for (int i = BOARD_SIZE_IN_SQUARES - 1; i >= 0; --i) {
                    if (alternate) {
                        setBlack(out);
                    }
                    else {
                        setWhite(out);
                    }
                    alternate = !alternate;
                    if (i2 == SQUARE_SIZE_IN_CHARS / 2) {
                        int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
                        int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

                        drawPlayerWhite(i1, i, out, prefixLength, suffixLength);
                    }
                    else {
                        out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                    }
                    if (i < BOARD_SIZE_IN_SQUARES) {
                        out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
                    }
                    out.print("\u001B[0m");
                }
                if (i2 == 1) {
                    Integer newRow = i1 + 1;
                    printRowNum(out, " " + newRow.toString());
                }
                else {
                    printRowNum(out, "  ");
                }
                out.println();
            }
            if (i1 < BOARD_SIZE_IN_SQUARES - 1) {
                setBlack(out);
            }
        }
    }

    private static void drawMovesWhite(PrintStream out, List<int[]> moves) {
        for (int boardRow = BOARD_SIZE_IN_SQUARES - 1; boardRow >= 0 ; --boardRow) {
            alternate = !alternate;
            for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_CHARS; ++squareRow) {
                if (squareRow == 1) {
                    Integer newRow = boardRow + 1;
                    printRowNum(out, newRow.toString() + " ");
                }
                else {
                    printRowNum(out, "  ");
                }
                for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                    boolean green = false;
                    for (int[] coordinate : moves) {
                        int row =coordinate[0];
                        int col = coordinate[1];
                        if (row == (boardRow + 1) && col == (boardCol + 1)) {
                            green = true;
                            setGreen(out);
                            break;
                        }
                    }
                    if (!green) {
                        if (alternate) {
                            setBlack(out);
                        } else {
                            setWhite(out);
                        }
                    }
                    alternate = !alternate;
                    if (squareRow == SQUARE_SIZE_IN_CHARS / 2) {
                        int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
                        int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

                        drawPlayerWhite(boardRow, boardCol, out, prefixLength, suffixLength);
                    }
                    else {
                        out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                    }
                    if (boardCol < BOARD_SIZE_IN_SQUARES) {
                        out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
                    }
                    out.print("\u001B[0m");
                }
                if (squareRow == 1) {
                    Integer newRow = boardRow + 1;
                    printRowNum(out, " " + newRow.toString());
                }
                else {
                    printRowNum(out, "  ");
                }
                out.println();
            }
            if (boardRow < BOARD_SIZE_IN_SQUARES - 1) {
                setBlack(out);
            }
        }
    }
    private static void drawMovesBlack(PrintStream out, List<int[]> list) {
        for (int i2 = 0; i2 < BOARD_SIZE_IN_SQUARES; ++i2) {
            alternate = !alternate;
            for (int i1 = 0; i1 < SQUARE_SIZE_IN_CHARS; ++i1) {
                if (i1 == 1) {
                    Integer newRow = i2 + 1;
                    printRowNum(out, newRow.toString() + " ");
                }
                else {
                    printRowNum(out, "  ");
                }
                for (int i = BOARD_SIZE_IN_SQUARES - 1; i >= 0; --i) {
                    boolean b = false;
                    for (int[] ints : list) {
                        int anInt =ints[0];
                        int anInt1 = ints[1];
                        if (anInt == (i2 + 1)&& anInt1 == (i + 1)) {
                            b = true;
                            setGreen(out);
                            break;
                        }
                    }
                    if (!b) {
                        if (alternate) {
                            setBlack(out);
                        } else {
                            setWhite(out);
                        }
                    }
                    alternate = !alternate;
                    if (i1 == SQUARE_SIZE_IN_CHARS / 2) {
                        int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
                        int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;
                        // Drawing players
                        drawPlayerWhite(i2, i, out, prefixLength, suffixLength);
                    }
                    else {
                        out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                    }
                    if (i < BOARD_SIZE_IN_SQUARES) {
                        out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
                    }
                    out.print("\u001B[0m");
                }
                if (i1 == 1) {
                    Integer newRow = i2 + 1;
                    printRowNum(out, " " + newRow.toString());
                }
                else {
                    printRowNum(out, "  ");
                }
                out.println();
            }
            if (i2 < BOARD_SIZE_IN_SQUARES - 1) {
                setBlack(out);
            }
        }
    }
    public static void drawPlayerWhite(int boardRow, int boardCol, PrintStream out, int prefixLength, int suffixLength) {
        if (chessGame.getBoard().getBoard()[boardRow][boardCol] == 'p') {
            out.print(EMPTY.repeat(prefixLength));
            printPlayer(out, "  p", "black");
            out.print(EMPTY.repeat(suffixLength));
        } else if (chessGame.getBoard().getBoard()[boardRow][boardCol] == 'q') {
            out.print(EMPTY.repeat(prefixLength));
            printPlayer(out, "  q", "black");
            out.print(EMPTY.repeat(suffixLength));
        } else if (chessGame.getBoard().getBoard()[boardRow][boardCol] == 'k') {
            out.print(EMPTY.repeat(prefixLength));
            printPlayer(out, "  k", "black");
            out.print(EMPTY.repeat(suffixLength));
        } else if (chessGame.getBoard().getBoard()[boardRow][boardCol] == 'r') {
            out.print(EMPTY.repeat(prefixLength));
            printPlayer(out, "  r", "black");
            out.print(EMPTY.repeat(suffixLength));
        } else if (chessGame.getBoard().getBoard()[boardRow][boardCol] == 'n') {
            out.print(EMPTY.repeat(prefixLength));
            printPlayer(out, "  n", "black");
            out.print(EMPTY.repeat(suffixLength));
        } else if (chessGame.getBoard().getBoard()[boardRow][boardCol] == 'b') {
            out.print(EMPTY.repeat(prefixLength));
            printPlayer(out, "  b", "black");
            out.print(EMPTY.repeat(suffixLength));
        } else if (chessGame.getBoard().getBoard()[boardRow][boardCol] == 'P') {
            out.print(EMPTY.repeat(prefixLength));
            printPlayer(out, "  P", "white");
            out.print(EMPTY.repeat(suffixLength));
        } else if (chessGame.getBoard().getBoard()[boardRow][boardCol] == 'Q') {
            out.print(EMPTY.repeat(prefixLength));
            printPlayer(out, "  Q", "white");
            out.print(EMPTY.repeat(suffixLength));
        } else if (chessGame.getBoard().getBoard()[boardRow][boardCol] == 'K') {
            out.print(EMPTY.repeat(prefixLength));
            printPlayer(out, "  K", "white");
            out.print(EMPTY.repeat(suffixLength));
        } else if (chessGame.getBoard().getBoard()[boardRow][boardCol] == 'R') {
            out.print(EMPTY.repeat(prefixLength));
            printPlayer(out, "  R", "white");
            out.print(EMPTY.repeat(suffixLength));
        } else if (chessGame.getBoard().getBoard()[boardRow][boardCol] == 'N') {
            out.print(EMPTY.repeat(prefixLength));
            printPlayer(out, "  N", "white");
            out.print(EMPTY.repeat(suffixLength));
        } else if (chessGame.getBoard().getBoard()[boardRow][boardCol] == 'B') {
            out.print(EMPTY.repeat(prefixLength));
            printPlayer(out, "  B", "white");
            out.print(EMPTY.repeat(suffixLength));
        } else {
            out.print(EMPTY.repeat(prefixLength));
            printPlayer(out, EMPTY, "");
            out.print(EMPTY.repeat(suffixLength));
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
    private static void setGreen(PrintStream out) {
        out.print(SET_BG_COLOR_GREEN);
        out.print(SET_TEXT_COLOR_GREEN);
    }
    private static void printPlayer(PrintStream out, String player, String color) {
        if (Objects.equals(color, "white")) {
            out.print(SET_TEXT_COLOR_BLUE);
        } else if (Objects.equals(color, "black")) {
            out.print(SET_TEXT_COLOR_RED);
        }
        out.print(player);
    }
}