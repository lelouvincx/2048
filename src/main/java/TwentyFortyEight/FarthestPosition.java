package TwentyFortyEight;

public class FarthestPosition {
    private int farthestX;
    private int farthestY;
    private int x;
    private int y;

    public FarthestPosition(int x, int y) {
        this.farthestX = x;
        this.farthestY = y;
        this.x = x;
        this.y = y;
    }

    public int getFarthestX() {
        return farthestX;
    }

    public int getFarthestY() {
        return farthestY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
