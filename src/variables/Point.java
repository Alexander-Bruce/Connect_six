package variables;

import java.util.Objects;

public final class Point {
    private final int row;
    private final int column;

    public Point(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int row() {
        return row;
    }

    public int column() {
        return column;
    }

    public boolean sameCell(Point other) {
        return other != null && sameCell(other.row, other.column);
    }

    public boolean sameCell(int otherRow, int otherColumn) {
        return row == otherRow && column == otherColumn;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Point)) {
            return false;
        }
        Point point = (Point) other;
        return row == point.row && column == point.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return (row + 1) + " " + (column + 1);
    }
}