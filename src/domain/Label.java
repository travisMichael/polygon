package domain;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Label {

    List<Edge> edges;
    // add reference to edges
    // group edges by pixel
    // group edges by their trace
    static AtomicInteger counter = new AtomicInteger(0);

    int key;

    int red;
    int blue;
    int green;

    public Label(int red, int blue, int green) {
        key = counter.addAndGet(1);
        this.red = red;
        this.blue = blue;
        this.green = green;
    }

    @Override
    public String toString() {
        return String.valueOf(key);
//        return "Label{" +
//                "red=" + red +
//                ", blue=" + blue +
//                ", green=" + green +
//                '}';
    }
}
