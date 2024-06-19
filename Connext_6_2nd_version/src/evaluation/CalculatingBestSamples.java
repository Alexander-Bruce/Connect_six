package evaluation;

import tools.Judge_Position;
import tools.Mark_Point;
import variables.Point;
import variables.Step;

import java.util.ArrayList;

public class CalculatingBestSamples implements Judge_Position, Mark_Point {

    private final Step results = new Step();
    private final int rivalSide;
    private final static int BLACK = 0;
    private final static int WHITE = 1;
    private final static int EMPTY = 2;
    private final static int BOARD_SIZE = 17;
    private final ArrayList<Point> max_marks_points = new ArrayList<>();
    private int [][]board_tree = new int[BOARD_SIZE][BOARD_SIZE];
    private int [][]aux_board = new int[BOARD_SIZE][BOARD_SIZE];

    private void considering_position(int[][] board, int side, int[] marks){
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
                        marks[i * BOARD_SIZE + j] = mark_enemy_second + mark_self_second - mark_enemy_first;
                    }
                }
            }
        }
    }

    private Point find_max_marks_point(int []marks){
        int max_mark = -1, max_point_x = 0, max_point_y = 0;
        for(int i = 0; i < BOARD_SIZE * BOARD_SIZE; i++){
            if(marks[i] > max_mark){
                max_mark = marks[i];
                max_point_x = i / BOARD_SIZE;
                max_point_y = i % BOARD_SIZE;
            }
        }

        return new Point(max_point_x, max_point_y, max_mark);
    }


    private void find_best_step(ArrayList<Point> Points){
        //get the best step having same index and max score
        int aitree_temp_max = -1;
        for (int i = 0; i < Points.size() && i < max_marks_points.size(); i++)
        {
            Point max = Points.get(i);
            Point maxtree = max_marks_points.get(i);
            if (max.mark >= 0 && maxtree.mark >= 0) {
                if (max.mark + maxtree.mark > aitree_temp_max && !(max.x == maxtree.x && max.y == maxtree.y)) {
                    results.setFirststep(max.x, max.y);
                    results.setSecondstep(maxtree.x, maxtree.y);
                    aitree_temp_max = max.mark + maxtree.mark;
                }
            }
            else break;
        }
    }

    public Step getResults(){
        return results;
    }

    public CalculatingBestSamples(int [][]board, int side, ArrayList<Point> Points){
        //determine the color of rival
        rivalSide = (side == BLACK) ? WHITE : BLACK;

        //enumerate the point in points
        for (Point point : Points) {
            board_tree = copy_board(board, board_tree);

            if (point.mark >= 0) board_tree[point.x][point.y] = side;
            else break;

            //clarify the marks of the points
            int[]marks = new int[BOARD_SIZE * BOARD_SIZE];

            //find best point based on first point
            considering_position(board_tree, side, marks);
            Point max_marks_point = find_max_marks_point(marks);
            max_marks_points.add(max_marks_point);
        }
        find_best_step(Points);
    }

}