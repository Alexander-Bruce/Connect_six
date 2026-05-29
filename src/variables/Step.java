package variables;

public final class Step {
    private final Point first;
    private final Point second;
    private final int score;

    public Step(Point first, Point second, int score) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Both points are required");
        }
        if (first.sameCell(second)) {
            throw new IllegalArgumentException("A step must contain two distinct points");
        }
        this.first = first;
        this.second = second;
        this.score = score;
    }

    public Point first() {
        return first;
    }

    public Point second() {
        return second;
    }

    public int score() {
        return score;
    }

    @Override
    public String toString() {
        return first + " " + second;
    }
}