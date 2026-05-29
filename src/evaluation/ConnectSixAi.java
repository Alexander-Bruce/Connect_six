package evaluation;

import variables.Point;
import variables.Step;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class ConnectSixAi {
    public static final int BLACK = 0;
    public static final int WHITE = 1;
    public static final int EMPTY = 2;
    public static final int BOARD_SIZE = 17;

    private static final int NEIGHBOR_RADIUS = 3;
    private static final int FIRST_CANDIDATE_LIMIT = 80;
    private static final BoardEvaluator EVALUATOR = new BoardEvaluator();

    private ConnectSixAi() {
    }

    public static Point openingMove() {
        int center = BOARD_SIZE / 2;
        return new Point(center, center);
    }

    public static Step chooseMove(int[][] board, int side) {
        validateBoard(board);
        validateSide(side);

        List<ScoredPoint> firstCandidates = scoreCandidates(board, side);
        if (firstCandidates.isEmpty()) {
            throw new IllegalStateException("No legal move is available");
        }

        Step bestStep = null;
        int bestScore = Integer.MIN_VALUE;
        int firstLimit = Math.min(FIRST_CANDIDATE_LIMIT, firstCandidates.size());

        for (int firstIndex = 0; firstIndex < firstLimit; firstIndex++) {
            ScoredPoint firstCandidate = firstCandidates.get(firstIndex);
            Point firstPoint = firstCandidate.point;
            board[firstPoint.row()][firstPoint.column()] = side;

            List<ScoredPoint> secondCandidates = scoreCandidates(board, side);
            if (!secondCandidates.isEmpty()) {
                ScoredPoint secondCandidate = secondCandidates.get(0);
                Point secondPoint = secondCandidate.point;
                int pairScore = firstCandidate.score + secondCandidate.score + scorePair(board, firstPoint, secondPoint, side);
                if (pairScore > bestScore || isTieBreakerBetter(firstPoint, secondPoint, bestStep, pairScore, bestScore)) {
                    bestScore = pairScore;
                    bestStep = new Step(firstPoint, secondPoint, pairScore);
                }
            }

            board[firstPoint.row()][firstPoint.column()] = EMPTY;
        }

        if (bestStep != null) {
            return bestStep;
        }

        return fallbackStep(board);
    }

    private static List<ScoredPoint> scoreCandidates(int[][] board, int side) {
        List<Point> candidates = collectCandidatePoints(board);
        List<ScoredPoint> scoredCandidates = new ArrayList<>();
        int rivalSide = opponentOf(side);
        for (Point candidate : candidates) {
            int attackScore = EVALUATOR.scorePoint(board, candidate, side);
            int defenseScore = EVALUATOR.scorePoint(board, candidate, rivalSide);
            int totalScore = attackScore + defenseScore + defenseScore / 5 + centerBias(candidate);
            scoredCandidates.add(new ScoredPoint(candidate, totalScore));
        }

        scoredCandidates.sort(Comparator
                .comparingInt((ScoredPoint candidate) -> candidate.score).reversed()
                .thenComparingInt(candidate -> centerDistance(candidate.point))
                .thenComparingInt(candidate -> candidate.point.row())
                .thenComparingInt(candidate -> candidate.point.column()));
        return scoredCandidates;
    }

    private static List<Point> collectCandidatePoints(int[][] board) {
        List<Point> candidates = new ArrayList<>();
        boolean hasStone = false;

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                if (board[row][column] != EMPTY) {
                    hasStone = true;
                }
            }
        }

        if (!hasStone) {
            candidates.add(openingMove());
            return candidates;
        }

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                if (board[row][column] == EMPTY && hasNeighbor(board, row, column)) {
                    candidates.add(new Point(row, column));
                }
            }
        }

        if (candidates.isEmpty()) {
            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int column = 0; column < BOARD_SIZE; column++) {
                    if (board[row][column] == EMPTY) {
                        candidates.add(new Point(row, column));
                    }
                }
            }
        }
        return candidates;
    }

    private static boolean hasNeighbor(int[][] board, int row, int column) {
        for (int rowOffset = -NEIGHBOR_RADIUS; rowOffset <= NEIGHBOR_RADIUS; rowOffset++) {
            for (int columnOffset = -NEIGHBOR_RADIUS; columnOffset <= NEIGHBOR_RADIUS; columnOffset++) {
                if (rowOffset == 0 && columnOffset == 0) {
                    continue;
                }

                int neighborRow = row + rowOffset;
                int neighborColumn = column + columnOffset;
                if (isInside(neighborRow, neighborColumn) && board[neighborRow][neighborColumn] != EMPTY) {
                    return true;
                }
            }
        }
        return false;
    }

    private static int scorePair(int[][] board, Point firstPoint, Point secondPoint, int side) {
        board[secondPoint.row()][secondPoint.column()] = side;
        try {
            int firstScore = EVALUATOR.scoreExistingPoint(board, firstPoint, side);
            int secondScore = EVALUATOR.scoreExistingPoint(board, secondPoint, side);
            return firstScore + secondScore;
        } finally {
            board[secondPoint.row()][secondPoint.column()] = EMPTY;
        }
    }

    private static boolean isTieBreakerBetter(Point firstPoint, Point secondPoint, Step bestStep, int pairScore, int bestScore) {
        if (pairScore != bestScore || bestStep == null) {
            return false;
        }
        int currentDistance = centerDistance(firstPoint) + centerDistance(secondPoint);
        int bestDistance = centerDistance(bestStep.first()) + centerDistance(bestStep.second());
        return currentDistance < bestDistance;
    }

    private static Step fallbackStep(int[][] board) {
        Point firstPoint = null;
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                if (board[row][column] == EMPTY) {
                    if (firstPoint == null) {
                        firstPoint = new Point(row, column);
                    } else {
                        Point secondPoint = new Point(row, column);
                        return new Step(firstPoint, secondPoint, 0);
                    }
                }
            }
        }
        throw new IllegalStateException("No legal pair is available");
    }

    private static int centerBias(Point point) {
        return (BOARD_SIZE * 2 - centerDistance(point)) * 2;
    }

    private static int centerDistance(Point point) {
        int center = BOARD_SIZE / 2;
        return Math.abs(point.row() - center) + Math.abs(point.column() - center);
    }

    private static int opponentOf(int side) {
        return side == BLACK ? WHITE : BLACK;
    }

    private static void validateBoard(int[][] board) {
        if (board == null || board.length != BOARD_SIZE) {
            throw new IllegalArgumentException("Board must be 17 x 17");
        }
        for (int row = 0; row < BOARD_SIZE; row++) {
            if (board[row] == null || board[row].length != BOARD_SIZE) {
                throw new IllegalArgumentException("Board must be 17 x 17");
            }
            for (int column = 0; column < BOARD_SIZE; column++) {
                int value = board[row][column];
                if (value != BLACK && value != WHITE && value != EMPTY) {
                    throw new IllegalArgumentException("Board contains an invalid value");
                }
            }
        }
    }

    private static void validateSide(int side) {
        if (side != BLACK && side != WHITE) {
            throw new IllegalArgumentException("Side must be BLACK or WHITE");
        }
    }

    private static boolean isInside(int row, int column) {
        return row >= 0 && row < BOARD_SIZE && column >= 0 && column < BOARD_SIZE;
    }

    private static final class ScoredPoint {
        private final Point point;
        private final int score;

        private ScoredPoint(Point point, int score) {
            this.point = point;
            this.score = score;
        }
    }
}