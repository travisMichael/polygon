package domain;

public class Edge {
    public Label north;
    public Vertex east;
    public Label south;
    public Vertex west;
    public EdgeType edgeType;

    public Edge(Label north, Vertex east, Label south, Vertex west, EdgeType edgeType) {
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
    }

    public Vertex getAssociatedVertex(Vertex vertex) {
        if (vertex == east) {
            return west;
        }
        if (vertex == west) {
            return east;
        }
        throw new RuntimeException("No associated vertex");
    }



    public double orientation(Vertex origin) {
        Vertex end = getAssociatedVertex(origin);

        double x = end.x - origin.x;
        double y = end.y - origin.y;
        double answer = Math.atan2(y, x);

        if (answer < 0) {
            answer += 2 * Math.PI;
        }

        return answer;
    }

    double offset() {
        // todo
        return 0;
    }

    public Label getLabel(Vertex pivot, Direction direction) {

        if (pivot == east) {
            if (direction == Direction.CLOCKWISE) {
                return north;
            }
            if (direction == Direction.COUNTER_CLOCKWISE) {
                return south;
            }
        }

        if (pivot == west) {
            if (direction == Direction.CLOCKWISE) {
                return south;
            }
            if (direction == Direction.COUNTER_CLOCKWISE) {
                return north;
            }
        }

        throw new RuntimeException("No pivot exception");
    }

    public Edge nextEdge(Vertex pivot, Direction direction) {
        return pivot.nextEdge(this, direction);
    }

    // In theory, the area defined by the trace is the same after splitting an edge. Therefore, the split point
    // should be on the line defined by the edge




}
