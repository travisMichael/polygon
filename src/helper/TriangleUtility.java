package helper;

import domain.Direction;

public class TriangleUtility {

    // https://algs4.cs.princeton.edu/91primitives/#:~:text=Use%20the%20following%20determinant%20formula,b%2D%3Ec%20are%20collinear.
    public static Direction getRotation(Pair<Double, Double> a, Pair<Double, Double> b, Pair<Double, Double> c) {
        // (bx - ax)(cy - ay) - (cx - ax)(by - ay)
        double signedArea = (b.left - a.left)*(c.right - a.right) - (c.left - a.left) * (b.right - a.right);

        if (signedArea > 0) {
            return Direction.COUNTER_CLOCKWISE;
        }
        if (signedArea < 0) {
            return Direction.CLOCKWISE;
        }
        throw new RuntimeException("points are co-linear");
    }
}
