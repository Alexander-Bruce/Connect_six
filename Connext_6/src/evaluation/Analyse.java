package evaluation;

import tools.Analyse_Point;

public class Analyse implements Analyse_Point {
    private final static int BOARD_SIZE = 17;
    private final int [][] board;
    private final int [] result = new int[15];

    public int []getResults(int side, int x, int y){
        analyse_horizontal(side, x, y);
        analyse_vertical(side, x, y);
        analyse_diagonal(side, x, y);
        analyse_antidiagonal(side, x, y);
        return result;
    }

    private void analyse_horizontal(int side, int x, int y){
        int []array = new int[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) array[i] = board[x][i];
        analyse_point(array, side, result);
    }

    private void analyse_vertical(int side, int x, int y){
        int []array = new int[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) array[i] = board[i][y];
        analyse_point(array, side, result);
    }

    private void analyse_diagonal(int side, int x, int y){
        int []array = new int[BOARD_SIZE];
        for (int i = 5; i < BOARD_SIZE; i++){
            int flag = 0;
            for (int j = 0; j < BOARD_SIZE; j++) array[j] = -1;
            for (int j = 0; j <= i; j++)
            {
                if (x == j && y == BOARD_SIZE - 1 - i + j)
                {
                    flag = 1;
                    break;
                }
            }
            if (flag == 1)
            {
                for (int j = 0; j <= i; j++) array[j] = board[j][BOARD_SIZE - 1 - i + j];
                analyse_point(array, side, result);
                return;
            }
        }

        for (int i = 1; i < 12; i++)
        {
            int flag = 0;
            for (int j = 0; j < BOARD_SIZE; j++) array[j] = -1;
            for (int j = 0; j <= BOARD_SIZE - i - 1; j++)
            {
                if (x == i + j && y == j)
                {
                    flag = 1;
                    break;
                }
            }
            if (flag == 1)
            {
                for (int j = 0; j <= BOARD_SIZE - i - 1; j++)
                    array[j] = board[i + j][j];
                analyse_point(array, side, result);
                return;
            }
        }
    }

    private void analyse_antidiagonal(int side, int x, int y){
        int [] array = new int[BOARD_SIZE];
        for (int i = 5; i < BOARD_SIZE; i++)
        {
            int flag = 0;
            for (int j = 0; j < BOARD_SIZE; j++) array[j] = -1;
            for (int j = 0; j <= i; j++)
            {
                if (x == j && y == i - j)
                {
                    flag = 1;
                    break;
                }
            }
            if (flag == 1)
            {
                for (int j = 0; j <= i; j++) array[j] = board[j][i - j];
                analyse_point(array, side, result);
                return;
            }
        }
        for (int i = 1; i < 12; i++)
        {
            int judge = 0;
            for (int j = 0; j < BOARD_SIZE; j++) array[j] = -1;
            for (int j = 0; j <= BOARD_SIZE - i - 1; j++)
            {
                if (x == i + j && y == BOARD_SIZE - j - 1)
                {
                    judge = 1;
                    break;
                }
            }
            if (judge == 1)
            {
                for (int j = 0; j <= BOARD_SIZE - i - 1; j++) array[j] = board[i + j][BOARD_SIZE - 1 - j];
                analyse_point(array, side, result);
                return;
            }
        }
    }

    public Analyse(int[][] board){
        this.board = board;
    }

}
