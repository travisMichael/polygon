package domain;

import helper.Pair;
import helper.TriangleUtility;
import helper.UnitCircle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Vertex {

    public int id;
    public double x;
    public double y;

    final List<Edge> edges = new ArrayList<>();

    public Vertex(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    void addEdge(Edge newEdge) {
        edges.add(newEdge);
    }

    void removeEdge(Edge edge) {
        edges.remove(edge);
    }


    /**
     *
     * @param radians
     * @return Pair of labels (north, south)
     */
    public Pair<Label, Label> getLabelAt(double radians) {
        // todo: modulo radians by 2 * pi
        // order the edges
        // search for region between two edges
        // return the label of that region
        radians = radians % (2 * Math.PI);
        Pair<Double, Double> b = UnitCircle.angleToPoint(radians);

        List<EdgeReference> edgeReferences = sortEdges();

        int current = 0;
        int next;
        int counter = 0;
        while (true) {
            if (counter > 50) {
                throw new RuntimeException();
            }
            next = (current + 1) % edgeReferences.size();
            Pair<Double, Double> a = UnitCircle.angleToPoint(edgeReferences.get(current).theta);
            Pair<Double, Double> c = UnitCircle.angleToPoint(edgeReferences.get(next).theta);
            Direction direction = TriangleUtility.getRotation(a, b, c);
            if (direction == Direction.COUNTER_CLOCKWISE) {
                Label label = edgeReferences.get(current).edge.getLabel(this, Direction.COUNTER_CLOCKWISE);
                return new Pair<>(label, label);
            }
            counter++;
            current = next;
        }
//        i = i % edgeReferences.size();
//        Edge edge = edgeReferences.get(i).edge;
//        // return edge.getLabel(this, Direction.CLOCKWISE);
//        return new Pair<>(null, null);
    }

    public Edge nextEdge(Edge incomingEdge, Direction direction) {
        if (incomingEdge.east != this && incomingEdge.west != this) {
            throw new RuntimeException("vertex is not part of edge");
        }
        // sort the edges. create circular list
        // find index of referenceEdge
        // newEdgeIndex = index + direction.x
        List<EdgeReference> sortedEdgeReferences = sortEdges();
        int index = 0;
        for (int i = 0; i < edges.size(); i++) {
            if (incomingEdge == sortedEdgeReferences.get(i).edge) {
                index = i;
            }
        }
        index = (index + direction.x) % edges.size();

        if (index < 0) {
            index += edges.size();
        }

        return sortedEdgeReferences.get(index).edge;
    }


    private List<EdgeReference> sortEdges() {
        List<EdgeReference> edgeReferences = new ArrayList<>();

        edges.forEach(e ->
                edgeReferences.add(new EdgeReference(e, e.orientation(this))));

        edgeReferences.sort(Comparator.comparingDouble(EdgeReference::getTheta));
        return edgeReferences;
    }

    public static class EdgeReference {
        Edge edge;
        double theta;

        public EdgeReference(Edge edge, double theta) {
            this.edge = edge;
            this.theta = theta;
        }

        public double getTheta() {
            return theta;
        }
    }

    /**
     * Tests whether the current vertex has an edge between itself and another vertex
     * @param candidate
     * @return
     */
    public boolean isDirectlyConnectedTo(Vertex candidate) {
        return this.edges.stream().anyMatch(edge -> candidate == edge.getAssociatedVertex(this));
    }

}
