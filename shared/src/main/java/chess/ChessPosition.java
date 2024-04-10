package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {

    int row;
    int column;

    //using this as a setter
    public ChessPosition(int row, int col)
    {
        this.row = row;
        this.column = col;

    }

    public static ChessPosition convertToPosition(String position)
    {
        char colChar = position.charAt(0);
        Integer rowInt = Character.getNumericValue(position.charAt(1));
        Integer newRow;
        Integer newCol = 0;

        switch (colChar)
        {
            case 'a':
                newCol = 1;
                break;
            case 'b':
                newCol = 2;
                break;
            case 'c':
                newCol = 3;
                break;
            case 'd':
                newCol = 4;
                break;
            case 'e':
                newCol = 5;
                break;
            case 'f':
                newCol = 6;
                break;
            case 'g':
                newCol = 7;
                break;
            case 'h':
                newCol = 8;
                break;

        }

        newRow = rowInt;
        ChessPosition newPosition = new ChessPosition(newRow, newCol);

        return newPosition;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return this.row;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return this.column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPosition that = (ChessPosition) o;
        return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString()
    {
        return row + "," + column;
    }
}
