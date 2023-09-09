package helper;

public class UnitCircle {

    public static Pair<Double, Double> angleToPoint(double angle) {
        double x = Math.cos(angle);
        double y = Math.sin(angle);
        return new Pair<>(x, y);
    }
}
