package helper;

public class Pair<K, T> {
    K left;
    T right;

    public Pair(K left, T right) {
        this.left = left;
        this.right = right;
    }

    public K getLeft() {
        return left;
    }

    public T getRight() {
        return right;
    }
}
