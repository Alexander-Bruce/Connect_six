import evaluation.ConnectSixAi;
import variables.Point;
import variables.Step;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        testOpeningMove();
        testResponseMoveIsLegal();
        testImmediateThreatIsBlocked();
        System.out.println("All tests passed.");
    }

    private static void testOpeningMove() {
        Point openingMove = ConnectSixAi.openingMove();
        require(openingMove.row() == 8 && openingMove.column() == 8, "opening move should be the board center");
    }

    private static void testResponseMoveIsLegal() {
        int[][] board = createEmptyBoard();
        board[8][8] = ConnectSixAi.BLACK;

        Step response = ConnectSixAi.chooseMove(board, ConnectSixAi.WHITE);
        require(isEmpty(board, response.first()), "first response point should be empty");
        require(isEmpty(board, response.second()), "second response point should be empty");
        require(!response.first().sameCell(response.second()), "response points should be distinct");
    }

    private static void testImmediateThreatIsBlocked() {
        int[][] board = createEmptyBoard();
        int threatRow = 8;
        for (int column = 4; column <= 8; column++) {
            board[threatRow][column] = ConnectSixAi.BLACK;
        }

        Step response = ConnectSixAi.chooseMove(board, ConnectSixAi.WHITE);
        boolean blocksLeftEnd = contains(response, threatRow, 3);
        boolean blocksRightEnd = contains(response, threatRow, 9);
        require(blocksLeftEnd || blocksRightEnd, "white should block an open five immediately");
    }

    private static int[][] createEmptyBoard() {
        int[][] board = new int[ConnectSixAi.BOARD_SIZE][ConnectSixAi.BOARD_SIZE];
        for (int row = 0; row < ConnectSixAi.BOARD_SIZE; row++) {
            Arrays.fill(board[row], ConnectSixAi.EMPTY);
        }
        return board;
    }

    private static boolean contains(Step step, int row, int column) {
        return step.first().sameCell(row, column) || step.second().sameCell(row, column);
    }

    private static boolean isEmpty(int[][] board, Point point) {
        return board[point.row()][point.column()] == ConnectSixAi.EMPTY;
    }

    private static void require(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}