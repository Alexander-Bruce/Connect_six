import evaluation.CalculatingBestSamples;
import evaluation.MarkingBoard;
import variables.Point;
import variables.Step;

import java.util.*;

public class Main {
    private final static Step move = new Step();
    private final static int[][] board = new int[17][17];
    private final static int BLACK = 0;
    private final static int WHITE = 1;
    private final static int EMPTY = 2;
    private static final Scanner scanner = new Scanner(System.in);

    private static void initBoard(){
        for(int i = 0; i < 17; i++){
            for(int j = 0; j < 17; j++){
                board[i][j] = EMPTY;
            }
        }
    }
    private static void inputBoard(int N, int color){
        if(N == 0) return;
        for(int i = 0; i < N; i++){
            int x, y;
            x = scanner.nextInt();
            y = scanner.nextInt();
            board[x - 1][y - 1] = color;
        }
    }

    private static Point find_accessible_point(int x, int y){
        //generate all the available points
        for(int i = 0; i < 17; i++){
            for(int j = 0; j < 17; j++){
                if(board[i][j] == EMPTY && !(x == i && y == j)){
                    return new Point(i, j);
                }
            }
        }
        return null;
    }

    private static void CheckStep(Step step, int color){
        int x1 = step.getFirststep().x, y1 = step.getFirststep().y;
        int x2 = step.getSecondstep().x, y2 = step.getSecondstep().y;

        //generate point1
        if(board[x1][y1] != EMPTY){
            Point point = find_accessible_point(x2, y2);
            if(point != null) step.setFirststep(point.x, point.y);
            board[step.getFirststep().x][step.getFirststep().y] = color;
        }

        //generate point2
        if(board[x2][y2] != EMPTY){
            Point point = find_accessible_point(x1, y1);
            if(point != null) step.setSecondstep(point.x, point.y);
            board[step.getSecondstep().x][step.getSecondstep().y] = color;
        }

    }

    public static void main(String []args){
        int side, whiteN, blackN;
        blackN = scanner.nextInt();
        whiteN = scanner.nextInt();
        side = scanner.nextInt();
        scanner.nextLine();

        //initiate the board
        initBoard();

        //input the points
        if(side != 0) {
            inputBoard(blackN, BLACK);
            inputBoard(whiteN, WHITE);
        }

        if(side == 0){
            //black first
            move.setFirststep(8, 10);
            board[move.getFirststep().x][move.getFirststep().y] = BLACK;
            System.out.println((move.getFirststep().x + 1) + " " + (move.getFirststep().y + 1));
        } else {
            //side = 1, black turn
            //side = 2, white turn
            if(side == 1) side = BLACK;
            else side = WHITE;
            //calculate the best step for now
            MarkingBoard MarkingBoard = new MarkingBoard(board, side);
            CalculatingBestSamples CalculatingBestSamples = new CalculatingBestSamples(board, side, MarkingBoard.getPoints());
            Step nextSteps = CalculatingBestSamples.getResults();
            CheckStep(nextSteps, side);
            board[nextSteps.getFirststep().x][nextSteps.getFirststep().y] = side;
            board[nextSteps.getSecondstep().x][nextSteps.getSecondstep().y] = side;
            System.out.println((nextSteps.getFirststep().x + 1) + " " + (nextSteps.getFirststep().y + 1) + " " + (nextSteps.getSecondstep().x + 1) + " " + (nextSteps.getSecondstep().y + 1));
        }
    }
}
