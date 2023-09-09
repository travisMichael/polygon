package domain;

public class CollinearVertex {
    public double x;
    public double y;
    public double ratio;

    public CollinearVertex(double x, double y, double ratio) {
        this.x = x;
        this.y = y;
        this.ratio = ratio;
    }

    public double getRatio() {
        return ratio;
    }
}
