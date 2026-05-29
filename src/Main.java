import evaluation.ConnectSixAi;
import variables.Step;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        int blackCount = readRequiredInt("black stone count");
        int whiteCount = readRequiredInt("white stone count");
        int sideCode = readRequiredInt("side");

        int[][] board = createEmptyBoard();
        if (sideCode != 0) {
            readStones(board, blackCount, ConnectSixAi.BLACK);
            readStones(board, whiteCount, ConnectSixAi.WHITE);
        }

        if (sideCode == 0) {
            System.out.println(ConnectSixAi.openingMove());
            return;
        }

        int side = parseSide(sideCode);
        Step nextStep = ConnectSixAi.chooseMove(board, side);
        System.out.println(nextStep);
    }

    private static int[][] createEmptyBoard() {
        int[][] board = new int[ConnectSixAi.BOARD_SIZE][ConnectSixAi.BOARD_SIZE];
        for (int row = 0; row < ConnectSixAi.BOARD_SIZE; row++) {
            Arrays.fill(board[row], ConnectSixAi.EMPTY);
        }
        return board;
    }

    private static void readStones(int[][] board, int stoneCount, int color) {
        for (int stoneIndex = 0; stoneIndex < stoneCount; stoneIndex++) {
            int row = readRequiredInt("row") - 1;
            int column = readRequiredInt("column") - 1;
            validateCoordinate(row, column);
            if (board[row][column] != ConnectSixAi.EMPTY) {
                throw new IllegalArgumentException("Duplicate stone at " + (row + 1) + " " + (column + 1));
            }
            board[row][column] = color;
        }
    }

    private static int readRequiredInt(String fieldName) {
        if (!SCANNER.hasNextInt()) {
            throw new IllegalArgumentException("Missing input: " + fieldName);
        }
        return SCANNER.nextInt();
    }

    private static void validateCoordinate(int row, int column) {
        if (row < 0 || row >= ConnectSixAi.BOARD_SIZE || column < 0 || column >= ConnectSixAi.BOARD_SIZE) {
            throw new IllegalArgumentException("Coordinate must be between 1 and " + ConnectSixAi.BOARD_SIZE);
        }
    }

    private static int parseSide(int sideCode) {
        if (sideCode == 1) {
            return ConnectSixAi.BLACK;
        }
        if (sideCode == 2) {
            return ConnectSixAi.WHITE;
        }
        throw new IllegalArgumentException("Side must be 0, 1, or 2");
    }
}