package evaluation;

import tools.Judge_Position;
import tools.Mark_Point;
import variables.Point;
import variables.Step;

import java.util.ArrayList;


public class CalculatingBestSamples implements Judge_Position, Mark_Point {

    private final int rivalSide;
    private final static int BLACK = 0;
    private final static int WHITE = 1;
    private final static int EMPTY = 2;
    private final static int BOARD_SIZE = 17;
    private final ArrayList<Step> results;
    private int [][]board_tree = new int[BOARD_SIZE][BOARD_SIZE];
    private int [][]aux_board = new int[BOARD_SIZE][BOARD_SIZE];

    private void considering_position(int[][] board, int side, ArrayList<Point> points){
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

                        points.add(new Point(i, j, mark_self_second + mark_enemy_second - mark_enemy_first));
                    }
                }
            }
        }
    }

    private Point find_max_marks_point(ArrayList<Point> points){
        points.sort((o1, o2) -> o2.mark - o1.mark);
        return points.get(0);
    }


    private void find_best_step(){
        //get the best step in results
        if(!results.isEmpty())results.sort((o1, o2) -> o2.value - o1.value);
        else results.add(new Step(new Point(0, 0), new Point(0, 0), 0));
    }

    public CalculatingBestSamples(int [][]board, int side, ArrayList<Step> results){
        //determine the color of rival
        rivalSide = (side == BLACK) ? WHITE : BLACK;
        this.results = results;

        //enumerate the point in points
        for (Step result : results) {
            board_tree = copy_board(board, board_tree);

            //modify the board
            Point point = result.getFirststep();
            board_tree[point.x][point.y] = side;

            //considering the position
            ArrayList<Point> points = new ArrayList<>();
            considering_position(board_tree, side, points);

            //find the max value point
            Point max_value_point = find_max_marks_point(points);

            if (!points.isEmpty()) result.setSecondstep(max_value_point);
            else result.setSecondstep(new Point(0, 0));
        }
        find_best_step();
    }

}