package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {
    private int pos_row;
    private int pos_col;
    public ChessPosition(int row, int col) {
        pos_row = row;
        pos_col = col;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return pos_row;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return pos_col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPosition that = (ChessPosition) o;
        return pos_row == that.pos_row && pos_col == that.pos_col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos_row, pos_col);
    }

    @Override
    public String toString() {
        return "ChessPosition{" +
                "pos_row=" + pos_row +
                ", pos_col=" + pos_col +
                '}';
    }
}
