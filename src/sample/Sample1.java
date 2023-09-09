package sample;

import domain.*;
import function.Trace;

public class Sample1 {

    public static void main(String[] args) {
        Grid grid = new Grid(12, 10);
        Label white = new Label(255, 255, 255);
        Graph graph = buildGraph(grid, white);

        // addBoundaryToGraph(graph, grid, white);

        Edge edge = graph.getEdges().get(0);
        Trace.apply(edge, edge.east, Direction.CLOCKWISE);


        System.out.println("----------------------");
        // todo: figure out how to grow grid without passing in the openLabel
        // todo: ^^^ it should work for grids that do not contain the full graph as well
        graph.growGrid(white);
        edge = graph.getEdges().get(graph.getEdges().size()-1);

        Trace.apply(edge, edge.east, Direction.CLOCKWISE);

        System.out.println("----------------------");

        Trace.apply(edge, edge.east, Direction.COUNTER_CLOCKWISE);

        System.out.println("----------------------");

    }

    // x= 4.0 y = 3.475
    static Graph buildGraph(Grid grid, Label white) {
        Graph graph = new Graph(grid);

        // add vertices
        graph.addVertex(new Vertex(1,2.1, 7.4));
        graph.addVertex(new Vertex(2,4.3, 7.2));
        graph.addVertex(new Vertex(3,8.2, 6.8));
        graph.addVertex(new Vertex(4,8.5, 3.66));
        graph.addVertex(new Vertex(5,4.4, 2.35));
        graph.addVertex(new Vertex(6,1.45, 3.32));
        graph.addVertex(new Vertex(7,3.6, 4.6));
//        Vertex one = new Vertex(1,2.1, 7.4);
//        Vertex two = new Vertex(2,4.3, 7.2);
//        Vertex three = new Vertex(3,8.2, 6.8);
//        Vertex four = new Vertex(4,8.5, 3.66);
//        Vertex five = new Vertex(5,4.4, 2.35);
//        Vertex six = new Vertex(6,1.45, 3.32);
//        Vertex seven = new Vertex(7,3.6, 4.6);

        Label red = new Label(255, 0, 0);

        // add edges

        // Edge edge1 = new Edge(north, graph.getVertex(4), south, west);

        graph.addEdge(
                red,
                graph.getVertex(4),
                white,
                graph.getVertex(5),
                EdgeType.CURVE
        );

        Edge edge = graph.getEdges().get(0);
        // Trace.apply(edge, edge.east, Direction.CLOCKWISE);

        graph.addEdge(
                red,
                graph.getVertex(3),
                white,
                graph.getVertex(4),
                EdgeType.CURVE
        );
        graph.addEdge(
                red,
                graph.getVertex(2),
                white,
                graph.getVertex(3),
                EdgeType.CURVE
        );
        graph.addEdge(
                red,
                graph.getVertex(7),
                white,
                graph.getVertex(2),
                EdgeType.CURVE
        );
        graph.addEdge(
                red,
                graph.getVertex(5),
                white,
                graph.getVertex(7),
                EdgeType.CURVE
        );

        return graph;
    }
}
