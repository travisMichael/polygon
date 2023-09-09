package function;

import domain.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CollinearPoints {

    public static void main(String[] args) {
        System.out.println(Math.ceil(5.5));
    }

    public static List<CollinearVertex> apply(Edge edge) {
        // assert that the distance between the two points is not zero!
        Vertex sink = edge.east;
        Vertex source = edge.west;

        if (sink.y == 10 || source.y == 10) {
            System.out.println();
        }
        // get coordinates of all new points
        double tempX = source.x;
        double tempY = source.y;

        double xIncrement = (sink.x - source.x) >= 0 ? 1 : -1;
        double yIncrement = (sink.y - source.y) >= 0 ? 1 : -1;
        tempX = Math.ceil(xIncrement * tempX);
        tempY = Math.ceil(yIncrement * tempY);
        List<Double> xList = new ArrayList<>();
        List<Double> yList = new ArrayList<>();

        while (tempX < sink.x * xIncrement) {
            if (tempX != xIncrement * source.x) {
                xList.add(Math.abs(tempX));
            } else {
                System.out.println();
            }
            tempX += 1;
        }

        while (tempY < sink.y * yIncrement) {
            if (tempY != yIncrement * source.y) {
                yList.add(Math.abs(tempY));
            } else {
                System.out.println();
            }

            tempY += 1;
        }

        double run = (sink.x - source.x);
        double m = (sink.y - source.y) / run;
        double b = sink.y - (m * sink.x);

        List<CollinearVertex> collinearVertices = new ArrayList<>();

        double length = Math.sqrt(Math.pow(sink.x - source.x, 2) + Math.pow(sink.y - source.y, 2));

        for (Double coordinate: xList)  {
            if (run ==0){
                System.out.println();
            }
            double newY = (m * coordinate) + b;
            double ratio = Math.sqrt(Math.pow(source.x - coordinate, 2) + Math.pow(source.y - newY, 2)) / length;
            collinearVertices.add(new CollinearVertex(coordinate, newY, ratio));
        }

        for (Double coordinate: yList)  {
            double newX;
            if (run == 0) {
                newX = sink.x;
            } else {
                newX = (coordinate - b) / m;
            }
            double ratio = Math.sqrt(Math.pow(source.x - newX, 2) + Math.pow(source.y - coordinate, 2)) / length;
            collinearVertices.add(new CollinearVertex(newX, coordinate, ratio));
        }

        collinearVertices.sort(Comparator.comparingDouble(CollinearVertex::getRatio));

        return collinearVertices;

//        while (xIncrement < xMax) {
//            xList.add(xIncrement);
//            xIncrement += 1;
//        }
//
//        double xMin = Math.min(source.x, sink.x);
//        double xMax = Math.max(source.x, sink.x);
//        List<Double> xList = new ArrayList<>();
//
//        double yMin = Math.min(source.y, sink.y);
//        double yMax = Math.max(source.y, sink.y);
//        List<Double> yList = new ArrayList<>();
//
//        double xIncrement = Math.ceil(xMin);
//        while (xIncrement < xMax) {
//            xList.add(xIncrement);
//            xIncrement += 1;
//        }
//
//        double yIncrement = Math.ceil(yMin);
//        while (yIncrement < yMax) {
//            yList.add(yIncrement);
//            yIncrement += 1;
//        }
//        double run = sink.x - source.x;
//
////        if (run == 0) {
////            return handleVerticalLine(edge);
////        }
//
//        double m = (sink.y - source.y) / run;
//        double b = sink.y - (m * sink.x);
//
//        double x = xMax - xMin;
//        double y = yMax - yMin;
//        List<CollinearVertex> collinearVertices = new ArrayList<>();
//        // collinearVertices.add(new CollinearVertex(source.x, source.y, 0));
//        // collinearVertices.add(new CollinearVertex(sink.x, sink.y, 1.0));
//        double length = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
//
//        for (Double coordinate: xList)  {
//            double newY = (m * coordinate) + b;
//            x = coordinate - xMin;
//            y = newY - yMin;
//            double ratio = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) / length;
//            collinearVertices.add(new CollinearVertex(coordinate, newY, ratio));
//        }
//
//        for (Double coordinate: yList)  {
//            double newX = (coordinate - b) / m;
//            x = newX - xMin;
//            y = coordinate - yMin;
//            double ratio = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) / length;
//            collinearVertices.add(new CollinearVertex(newX, coordinate, ratio));
//        }
//
//        collinearVertices.sort(Comparator.comparingDouble(CollinearVertex::getRatio));
//
//        return collinearVertices;

    }

//    public static List<CollinearVertex> apply(Edge edge) {
//        Vertex sink = edge.east;
//        Vertex source = edge.west;
//        // get coordinates of all new points
//        double xMin = Math.min(source.x, sink.x);
//        double xMax = Math.max(source.x, sink.x);
//        List<Double> xList = new ArrayList<>();
//
//        double yMin = Math.min(source.y, sink.y);
//        double yMax = Math.max(source.y, sink.y);
//        List<Double> yList = new ArrayList<>();
//
//        double xIncrement = Math.ceil(xMin);
//        while (xIncrement < xMax) {
//            xList.add(xIncrement);
//            xIncrement += 1;
//        }
//
//        double yIncrement = Math.ceil(yMin);
//        while (yIncrement < yMax) {
//            yList.add(yIncrement);
//            yIncrement += 1;
//        }
//        double run = sink.x - source.x;
//
//        if (run == 0) {
//            return handleVerticalLine(edge);
//        }
//
//        double m = (sink.y - source.y) / run;
//        double b = sink.y - (m * sink.x);
//
//        double x = xMax - xMin;
//        double y = yMax - yMin;
//        List<CollinearVertex> collinearVertices = new ArrayList<>();
//        // collinearVertices.add(new CollinearVertex(source.x, source.y, 0));
//        // collinearVertices.add(new CollinearVertex(sink.x, sink.y, 1.0));
//        double length = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
//
//        for (Double coordinate: xList)  {
//            double newY = (m * coordinate) + b;
//            x = coordinate - xMin;
//            y = newY - yMin;
//            double ratio = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) / length;
//            collinearVertices.add(new CollinearVertex(coordinate, newY, ratio));
//        }
//
//        for (Double coordinate: yList)  {
//            double newX = (coordinate - b) / m;
//            x = newX - xMin;
//            y = coordinate - yMin;
//            double ratio = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) / length;
//            collinearVertices.add(new CollinearVertex(newX, coordinate, ratio));
//        }
//
//        collinearVertices.sort(Comparator.comparingDouble(CollinearVertex::getRatio));
//
//        return collinearVertices;
    //}
}
