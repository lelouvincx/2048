package TwentyFortyEight;

public enum Direction {
    UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0);
    private final Vector vector;

    Direction(int dx, int dy) {
        this.vector = new Vector(dx, dy);
    }

    public Vector getVector() {
        return vector;
    }
}
