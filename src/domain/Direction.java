package domain;

public enum Direction {
    CLOCKWISE(-90, -1),
    COUNTER_CLOCKWISE(90, 1);

    final int theta;
    final int x;
    Direction(int theta, int x) {
        this.theta = theta;
        this.x = x;
    }
}
