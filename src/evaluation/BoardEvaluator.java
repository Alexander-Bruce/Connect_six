package evaluation;

import variables.Point;

final class BoardEvaluator {
    private static final int[][] DIRECTIONS = {
            {1, 0},
            {0, 1},
            {1, 1},
            {1, -1}
    };

    int scorePoint(int[][] board, Point point, int side) {
        if (board[point.row()][point.column()] != ConnectSixAi.EMPTY) {
            return Integer.MIN_VALUE / 4;
        }

        board[point.row()][point.column()] = side;
        try {
            return scoreExistingPoint(board, point, side);
        } finally {
            board[point.row()][point.column()] = ConnectSixAi.EMPTY;
        }
    }

    int scoreExistingPoint(int[][] board, Point point, int side) {
        int score = 0;
        for (int[] direction : DIRECTIONS) {
            score += scoreDirection(board, point.row(), point.column(), direction[0], direction[1], side);
        }
        return score;
    }

    private int scoreDirection(int[][] board, int row, int column, int rowDelta, int columnDelta, int side) {
        int forwardCount = countConsecutive(board, row, column, rowDelta, columnDelta, side);
        int backwardCount = countConsecutive(board, row, column, -rowDelta, -columnDelta, side);
        int totalStones = 1 + forwardCount + backwardCount;

        int openEnds = 0;
        if (isOpen(board, row + (forwardCount + 1) * rowDelta, column + (forwardCount + 1) * columnDelta)) {
            openEnds++;
        }
        if (isOpen(board, row - (backwardCount + 1) * rowDelta, column - (backwardCount + 1) * columnDelta)) {
            openEnds++;
        }

        return lineScore(totalStones, openEnds);
    }

    private int countConsecutive(int[][] board, int row, int column, int rowDelta, int columnDelta, int side) {
        int count = 0;
        int currentRow = row + rowDelta;
        int currentColumn = column + columnDelta;
        while (isInside(currentRow, currentColumn) && board[currentRow][currentColumn] == side) {
            count++;
            currentRow += rowDelta;
            currentColumn += columnDelta;
        }
        return count;
    }

    private boolean isOpen(int[][] board, int row, int column) {
        return isInside(row, column) && board[row][column] == ConnectSixAi.EMPTY;
    }

    private boolean isInside(int row, int column) {
        return row >= 0 && row < ConnectSixAi.BOARD_SIZE && column >= 0 && column < ConnectSixAi.BOARD_SIZE;
    }

    private int lineScore(int totalStones, int openEnds) {
        if (totalStones >= 6) {
            return 1_000_000;
        }
        if (totalStones == 5) {
            return openEnds == 2 ? 250_000 : openEnds == 1 ? 90_000 : 10_000;
        }
        if (totalStones == 4) {
            return openEnds == 2 ? 35_000 : openEnds == 1 ? 9_000 : 1_000;
        }
        if (totalStones == 3) {
            return openEnds == 2 ? 4_500 : openEnds == 1 ? 900 : 120;
        }
        if (totalStones == 2) {
            return openEnds == 2 ? 450 : openEnds == 1 ? 90 : 10;
        }
        return openEnds == 2 ? 30 : openEnds == 1 ? 8 : 0;
    }
}