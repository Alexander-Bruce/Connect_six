import evaluation.CalculatingBestSamples;
import evaluation.MarkingBoard;
import variables.Point;
import variables.Step;

import java.util.ArrayList;
import java.util.Scanner;

public class Test {
    private final static Step move = new Step();
    private final static int[][] board = new int[17][17];
    private final static int BLACK = 0;
    private final static int WHITE = 1;
    private final static int EMPTY = 2;
    private final static int head = 0;
    private final static int follow = 1;
    private static final Scanner scanner = new Scanner(System.in);
    private static Step nextSteps;
    private static Step nextSteps1;
    private static int side;
    private static int start = 0;
    private static int win;
    private static ArrayList<Step> results;

    private static void initBoard(){
        for(int i = 0; i < 17; i++){
            for(int j = 0; j < 17; j++){
                board[i][j] = EMPTY;
            }
        }
    }

    private static void printBoard(){
        // 打印列标头
        System.out.print("   "); // 对齐行标头
        for(int i = 0; i < 17; i++)
            System.out.printf("%2d ", i + 1);
        System.out.println();

        // 打印棋盘
        for(int i = 0; i < 17; i++){
            System.out.printf("%2d ", i + 1); // 打印行标头
            for(int j = 0; j < 17; j++){
                char toPrint;
                switch (board[i][j]) {
                    case 1:
                        toPrint = 'W';
                        break;
                    case 2:
                        toPrint = '*';
                        break;
                    default:
                        toPrint = 'B';
                        break;
                }
                System.out.print(toPrint + "  ");
            }
            System.out.println();
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

    private static int CheckStep(Step step, int color){
        int x1 = step.getFirststep().x, y1 = step.getFirststep().y;
        int x2 = step.getSecondstep().x, y2 = step.getSecondstep().y;

        //generate point1
        if(board[x1][y1] != EMPTY){
            Point point = find_accessible_point(x2, y2);
            if(point != null) step.setFirststep(point.x, point.y);
            else return 2;
            board[step.getFirststep().x][step.getFirststep().y] = color;
        }

        //generate point2
        if(board[x2][y2] != EMPTY){
            Point point = find_accessible_point(x1, y1);
            if(point != null) step.setSecondstep(point.x, point.y);
            else return 2;
            board[step.getSecondstep().x][step.getSecondstep().y] = color;
        }

        board[step.getFirststep().x][step.getFirststep().y] = color;
        board[step.getSecondstep().x][step.getSecondstep().y] = color;
        return checkSixInRow(board);
    }

    public static boolean isBoardFull(){
        for(int i = 0; i < 17; i++){
            for(int j = 0; j < 17; j++){
                if(board[i][j] == EMPTY){
                    return false;
                }
            }
        }
        return true;
    }

    public static int checkSixInRow(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != EMPTY) {
                    if (checkDirection(board, i, j, 1, 0) ||  // Horizontal
                            checkDirection(board, i, j, 0, 1) ||  // Vertical
                            checkDirection(board, i, j, 1, 1) ||  // Diagonal down-right
                            checkDirection(board, i, j, 1, -1)) { // Diagonal down-left
                        return board[i][j];  // 返回当前棋子的值（0 或 1）
                    }
                }
            }
        }
        if(isBoardFull()) return 2;
        return -1;// 如果棋盘满了且没人赢，返回 2；否则返回 -1 表示继续游戏
    }

    public static boolean checkDirection(int[][] board, int row, int col, int deltaRow, int deltaCol) {
        int count = 0;
        int current = board[row][col];
        for (int k = 0; k < 6; k++) {
            int newRow = row + k * deltaRow;
            int newCol = col + k * deltaCol;
            if (newRow < 0 || newRow >= board.length || newCol < 0 || newCol >= board[0].length || board[newRow][newCol] != current) {
                return false;
            }
        }
        return true;
    }

    public static void run_connect_six(int i, int j){

        while(true) {
            if (side == follow && start == 0) {
                //black first
                side = BLACK;
                move.setFirststep(i, j);
                board[move.getFirststep().x][move.getFirststep().y] = BLACK;
                start = 1;
                printBoard();
            } else if (side == head && start == 0) {
                //white first
                side = WHITE;
                System.out.print("Please input the coordinates of the black point: ");
                int x, y;
                x = scanner.nextInt();
                y = scanner.nextInt();
                board[x - 1][y - 1] = BLACK;

                new CalculatingBestSamples(board, side, new MarkingBoard(board, side, results).getResults());
                nextSteps = results.get(0);
                board[nextSteps.getFirststep().x][nextSteps.getFirststep().y] = side;
                board[nextSteps.getSecondstep().x][nextSteps.getSecondstep().y] = side;
                System.out.println((nextSteps.getFirststep().x + 1) + " " + (nextSteps.getFirststep().y + 1));
                System.out.println((nextSteps.getSecondstep().x + 1) + " " + (nextSteps.getSecondstep().y + 1));
                printBoard();
                start = 1;
            } else if (start != 0) {
                //human vs intelligent
//                int x, y;
//                System.out.println("Please input the coordinates of the two points: ");
//                System.out.print("The first point's coordinate separated by space: ");
//                x = scanner.nextInt(); y = scanner.nextInt(); scanner.nextLine();
//                board[x - 1][y - 1] = WHITE;
//                System.out.print("The second point's coordinate separated by space: ");
//                x = scanner.nextInt(); y = scanner.nextInt(); scanner.nextLine();
//                board[x - 1][y - 1] = WHITE;

                //intelligent vs intelligent
                results = new ArrayList<>();
                new CalculatingBestSamples(board, 1 - side, new MarkingBoard(board, 1 - side, results).getResults());
                nextSteps1 = results.get(0);
                int check = CheckStep(nextSteps1, 1 - side);
                board[nextSteps1.getFirststep().x][nextSteps1.getFirststep().y] = 1 - side;
                board[nextSteps1.getSecondstep().x][nextSteps1.getSecondstep().y] = 1 - side;
                System.out.println((nextSteps1.getFirststep().x + 1) + " " + (nextSteps1.getFirststep().y + 1));
                System.out.println((nextSteps1.getSecondstep().x + 1) + " " + (nextSteps1.getSecondstep().y + 1));
                printBoard();
                if (check != -1) {
                    System.out.println("Game over!");
                    win = check;
                    break;
                }

                //ai step
                results = new ArrayList<>();
                new CalculatingBestSamples(board, side, new MarkingBoard(board, side, results).getResults());
                nextSteps = results.get(0);
                int check1 = CheckStep(nextSteps, side);
                board[nextSteps.getFirststep().x][nextSteps.getFirststep().y] = side;
                board[nextSteps.getSecondstep().x][nextSteps.getSecondstep().y] = side;
                System.out.println((nextSteps.getFirststep().x + 1) + " " + (nextSteps.getFirststep().y + 1));
                System.out.println((nextSteps.getSecondstep().x + 1) + " " + (nextSteps.getSecondstep().y + 1));
                printBoard();
                if (check1 != -1){
                    System.out.println("Game over!");
                    win = check1;
                    break;
                }
            }
        }
    }


    public static void main(String []args){
        //initiate the board
        System.out.print("Please input the side(1 represents the defender and 0 represents the offender): ");
        side = scanner.nextInt();

        //only one times
//        start = 0;
//        initBoard();
//        run_connect_six(7, 8);
//        System.out.println(win);
//        int round_black = 0;
//        for(int i = 0; i < 17; i++){
//            for(int j = 0; j < 17; j++){
//                if(board[i][j] == 0) round_black++;
//            }
//        }
//        System.out.println("round: " + round_black/2);

         // multiple times
        int low_bound = 5;
        int upper_bound = 9;

        int [][] visited = new int[17][17];
        for(int i = low_bound; i < upper_bound; i++){
            for(int j = 0; j < 17; j++){
                visited[i][j] = 0;
            }
        }
        for(int i = low_bound; i < upper_bound; i++){
            for(int j = low_bound; j < upper_bound; j++){
                initBoard();
                start = 0;
                side = follow;
                run_connect_six(i, j);
                visited[i][j] = win;
            }
        }

        int winCount = 0, loseCount = 0, drawCount = 0;
        for(int i = low_bound; i < upper_bound; i++){
            for(int j = low_bound; j < upper_bound; j++){
                if(visited[i][j] == 0) winCount++;
                else if(visited[i][j] == 1) loseCount++;
                else drawCount++;
                System.out.print(visited[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("Win count: " + winCount);
        System.out.println("Lose count: " + loseCount);
        System.out.println("Draw count: " + drawCount);

    }
}
