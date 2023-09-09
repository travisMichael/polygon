package function;

import domain.Direction;
import domain.Edge;
import domain.Vertex;

import java.util.ArrayList;
import java.util.List;


public class Trace  {
    /**
     * Collect all vertices associated to tracing a closed curve
     * @param start
     * @param pivot
     * @param direction
     * @return
     */
    public static List<Vertex> apply(Edge start, Vertex pivot, Direction direction) {

        Vertex end = start.getAssociatedVertex(pivot);
        List<Vertex> vertices = new ArrayList<>();
        vertices.add(end);
        vertices.add(pivot);
        System.out.println(start.west.id + " " + start.getLabel(pivot, direction) + " " + start.east.id);

        Edge edge = start;
        while (pivot != end) {
            edge = edge.nextEdge(pivot, direction);
            pivot = edge.getAssociatedVertex(pivot);
            if (pivot != end) {
                vertices.add(pivot);
            }
            System.out.println(edge.west.id + " " + edge.getLabel(pivot, direction) + " " + edge.east.id);
        }

        return vertices;
    }

}