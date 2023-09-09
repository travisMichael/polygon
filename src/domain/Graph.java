package domain;

import function.CollinearPoints;
import helper.GridUtility;
import helper.Pair;

import java.util.*;

public class Graph {

    // todo: add border to graph before this step
    // todo: enforce grid to be at least 1 pixel wide and 1 pixel high
    // todo: add edge method should assign the edges to a pixel
    // todo: consider not having 'edges' ie a vertex links to other vertices

    // done
    // todo: vertex indexing should be done during vertex insertion
    // todo: edge splitting should be done during edge insertion
    // todo: these should be added the point intersection process

    private final Map<Integer, Vertex> vertices = new HashMap<>();
    private final List<Edge> edges = new ArrayList<>();

    private final Grid grid;
    private final TreeSet<Vertex>[] verticalLinesIndex;
    TreeSet<Vertex>[] horizontalLinesIndex;

    public Integer nextVertexLabel() {
        return this.vertices.size() + 1;
    }

    public Graph(Grid grid) {
        // cannot create a graph without specifying the grid
        this.grid = grid;
        this.verticalLinesIndex = new TreeSet[grid.x + 1];
        this.horizontalLinesIndex = new TreeSet[grid.y + 1];

        // initialize index lines
        for (int i = 0; i <= grid.x; i++) {
            verticalLinesIndex[i] = new TreeSet<>(Comparator.comparingDouble(v -> v.y));
        }

        for (int i = 0; i <= grid.y; i++) {
            horizontalLinesIndex[i] = new TreeSet<>(Comparator.comparingDouble(v -> v.x));
        }
    }

    public void addVertex(Vertex vertex) {
        // if a coordinate is equal to its floor or ceiling, then the pixel lies on a grid line
        Integer xGrid = GridUtility.getGridCoordinateIfExists(vertex.x);
        Integer yGrid = GridUtility.getGridCoordinateIfExists(vertex.y);
        if (xGrid != null) {
            vertex.x = xGrid;
            verticalLinesIndex[xGrid].add(vertex);
        }
        if (yGrid != null) {
            vertex.y = yGrid;
            horizontalLinesIndex[yGrid].add(vertex);
        }

        vertices.put(vertex.id, vertex);
    }

    public Vertex getVertex(Integer key) {
        return vertices.get(key);
    }

    public void addEdge(Label north, Vertex east, Label south, Vertex west, EdgeType edgeType) {
        Edge edge = new Edge(north, east, south, west, edgeType);
        addEdge(edge);
    }

    public void addEdge(Edge edge) {
        // break edge into multiple edges if it spans across grid
        // todo map edge to its pixel
        splitEdgeByGrid(edge);
    }

    private Vertex addVertex(CollinearVertex collinearVertex) {
        Vertex vertex = new Vertex(nextVertexLabel(), collinearVertex.x, collinearVertex.y);
        addVertex(vertex);
        return vertex;
    }

    private void splitEdgeByGrid(Edge edge) {
        // todo: refactor
        // instead of adding and removing edges from the graph, just create all edges from collinear points
        // perform automatic edge splitting
        List<CollinearVertex> collinearVertices = CollinearPoints.apply(edge);
        Iterator<CollinearVertex> iterator = collinearVertices.iterator();
        Vertex west = edge.west;
        Vertex east;
        while (iterator.hasNext()) {
            east = addVertex(iterator.next());
            // do something
            // todo: north and south are flipped here due to collinear point algorithm. fix
            Edge newEdge = new Edge(edge.south, east, edge.north, west, edge.edgeType);
            east.addEdge(newEdge);
            west.addEdge(newEdge);
            // todo: what pixel does the edge belong to?
            edges.add(newEdge);

            west = east;
        }
        east = edge.east;
        Edge newEdge = new Edge(edge.south, east, edge.north, west, edge.edgeType);
        east.addEdge(newEdge);
        west.addEdge(newEdge);
        // todo: what pixel does the edge belong to?
        edges.add(newEdge);
//        for (CollinearVertex collinearVertex: collinearVertices) {
//            Pair<Edge, Edge> pair = split(new Vertex(nextVertexLabel(), collinearVertex.x, collinearVertex.y), edgeToSplitOn, edgeType);
//            // get east edge after splitting
//            edgeToSplitOn = pair.getLeft();
//        }
        // end refactor
    }

    /**
     * splitPoint will be mutated to point to the new edges added
     * @param splitPoint
     * @param edgeToSplit
     */
    public Pair<Edge, Edge> split(Vertex splitPoint, Edge edgeToSplit, EdgeType edgeType) {
        // todo: throw exception if split point is not sufficiently close to edge
        // create two new edges with north and south equal to current edge
        // remove edge from the graph
        // remove the edge from the east vertex
        // remove the edge from the west vertex
        Vertex east = edgeToSplit.east;
        Vertex west = edgeToSplit.west;

        Edge eastEdge = new Edge(edgeToSplit.north, east, edgeToSplit.south, splitPoint, edgeType);
        Edge westEdge = new Edge(edgeToSplit.north, splitPoint, edgeToSplit.south, west, edgeType);

        splitPoint.addEdge(eastEdge);
        splitPoint.addEdge(westEdge);

        east.addEdge(eastEdge);
        west.addEdge(westEdge);

        east.removeEdge(edgeToSplit);
        west.removeEdge(edgeToSplit);

        addVertex(splitPoint);
        edges.add(eastEdge);
        edges.add(westEdge);
        edges.remove(edgeToSplit);

        return new Pair<>(eastEdge, westEdge);
    }

    public List<Edge> getEdges() {
        return edges;
    }


    private void addVirtualEdges(TreeSet<Vertex>[] linesIndex, double radianOffset) {
        // construct top to bottom
        for (int i = 0; i < linesIndex.length; i++) {
            TreeSet<Vertex> lineIndex = linesIndex[i];
            Iterator<Vertex> iterator = lineIndex.descendingIterator();
            Vertex west = iterator.next();
            List<Edge> edgesToAdd = new ArrayList<>();
            while (iterator.hasNext()) {
                Vertex east = iterator.next();
                // north is to the right, south is to the left (for vertical)
                // north is south, south is north (for horizontal)

                // is there an edge between the current vertex and the next vertex?
                // if so, continue. Otherwise, add a new edge between them.
                if (!east.isDirectlyConnectedTo(west)) {
                    // wEdgeL = get first edge from west to the left of offset (moving clockwise)
                    // wEdgeR = get first edge from west to the right of offset (moving counter-clockwise)

                    // 0 : 3 * Math.PI/2
                    Pair<Label, Label> pair = west.getLabelAt(radianOffset);
                    // PI : Math.PI/2
//                    if (north == null || north != south) {
//                        throw new RuntimeException("Error when connecting vertical lines");
//                    }
                    edgesToAdd.add(new Edge(pair.getLeft(), east, pair.getRight(), west, EdgeType.GRID));
                }
                west = east;
            }
            edgesToAdd.forEach(e -> addEdge(e));
        }
    }

    /**
     *  Merges a graph grid into a graph
     *
     */
    public void growGrid(Label openLabel) {

        addBoundaryToGraph(openLabel);

        // add virtual vertical edges: top to bottom
        addVirtualEdges(verticalLinesIndex, 3*Math.PI/2);
        // add virtual horizontal edges: right to left
        addVirtualEdges(horizontalLinesIndex, Math.PI);

        // at this point, all edges have two labels (except grid boundaries) and are assigned to a pixel
        // Next step: For each pixel, group the edges by their trace.
        // assign areas and colors and figure out whether group area color is added or subtracted.

    }


    void addBoundaryToGraph(Label openLabel) {
        // todo: should be able to handle graphs that do not fit nicely in the grid

        Vertex lowerLeft = new Vertex(nextVertexLabel(), 0.0, 0.0);
        Vertex lowerRight = new Vertex(nextVertexLabel(), grid.x, 0.0);
        Vertex upperRight = new Vertex(nextVertexLabel(), grid.x, grid.y);
        Vertex upperLeft = new Vertex(nextVertexLabel(), 0.0, grid.y);
        addVertex(lowerLeft);
        addVertex(lowerRight);
        addVertex(upperRight);
        addVertex(upperLeft);

        addEdge(openLabel, lowerLeft, null, lowerRight, EdgeType.GRID);
        addEdge(openLabel, lowerRight, null, upperRight, EdgeType.GRID);
        addEdge(openLabel, upperRight, null, upperLeft, EdgeType.GRID);
        addEdge(openLabel, upperLeft, null, lowerLeft, EdgeType.GRID);

    }


}
