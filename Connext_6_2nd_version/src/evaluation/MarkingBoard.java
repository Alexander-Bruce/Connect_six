package evaluation;

import tools.Judge_Position;
import tools.Mark_Point;
import variables.Point;
import variables.Step;

import java.util.ArrayList;

public class MarkingBoard implements Mark_Point, Judge_Position {

    private final int rivalSide;
    private final ArrayList<Step> results;
    private final static int BLACK = 0;
    private final static int WHITE = 1;
    private final static int EMPTY = 2;
    private final static int BOARD_SIZE = 17;
    private final int [][] board;
    private int [][]aux_board = new int[BOARD_SIZE][BOARD_SIZE];

    private void findPoints(int side){
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == EMPTY && surround(board, i, j, side, BLACK, WHITE)) { //只考虑棋盘的空位
                    {
                        aux_board = copy_board(board, aux_board);
                        Analyse analyse = new Analyse(aux_board);
                        int mark_enemy_first = markenemy(analyse.getResults(rivalSide, i, j));
                        aux_board[i][j] = rivalSide;
                        int mark_enemy_second = markenemy(analyse.getResults(rivalSide, i, j));
                        aux_board[i][j] = side;
                        int mark_self_second = markself(analyse.getResults(side, i, j));

                        // if(side == BLACK) Points.add(new Point(i, j, mark_enemy_second + mark_self_second - mark_enemy_first));
                        Point point = new Point(i, j,  mark_self_second + mark_enemy_second - mark_enemy_first);
                        results.add(new Step(point, new Point(0, 0, 0), point.mark));
                    }
                }
            }
        }
    }

    public ArrayList<Step> getResults(){ return results; }

    public MarkingBoard(int[][] board, int side, ArrayList<Step> results){
        this.board = board;
        this.results = results;
        rivalSide = (side == BLACK) ? WHITE : BLACK;
        findPoints(side);
    }
}
