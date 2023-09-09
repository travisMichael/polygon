package helper;

import domain.Edge;

public class GridUtility {

    final static double THRESHOLD = 0.001;
    public static Integer getGridCoordinateIfExists(double coordinate) {
        double floor = Math.floor(coordinate);
        double ceil = Math.ceil(coordinate);
        // double xDistance = Math.min();
        if (coordinate - floor < THRESHOLD) {
            return (int) floor;
        }
        if (ceil - coordinate < THRESHOLD) {
            return (int) ceil;
        }
        return null;
    }

    public Pair<Integer, Integer> mapEdgeToPixel(Edge edge) {
        // both pixels are inside the pixel
        // both pixels are on an edge
        // one pixel is on an edge and the other is in the pixel
        // todo
        return null;
    }
}
