package domain;

public class Grid {
    public int x;
    public int y;

    public Grid(int x, int y) {
        assert x >= 1;
        assert y >= 1;
        this.x = x;
        this.y = y;
    }
}
