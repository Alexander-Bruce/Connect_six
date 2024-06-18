package tools;

public interface Judge_Position {

    int BOARD_SIZE = 17;
    default boolean surround(int[][] board, int x, int y, int side, int BLACK, int WHITE) {
        for (int i = -3; i < 4; i++)
        {
            if (x + i > 16) break;
            for (int j = -3; j < 4; j++)
            {
                while (x + i < 0) i += 1;
                while (y + j < 0) j += 1;
                //if (i == 0 & j == 0)
                if(i == 0 && j == 0) j += 1;
                if (y + j > 16) break;
                if (board[x + i][y + j] == WHITE || board[x + i][y + j] == BLACK) return true;
            }
        }
        return false;
    }

    default int[][] copy_board(int[][] board, int[][] aux_board){
        for (int i = 0; i < BOARD_SIZE; i++)
        {
            for (int j = 0; j < BOARD_SIZE; j++) {
                aux_board[i][j] = board[i][j];
            }
        }
        return aux_board;
    }
}
