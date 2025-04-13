package TwentyFortyEight;

// 2 _ 2 _
// _ 2 2 _
// _ _ 4 _
// _ _ _ 4

class FarthestPosition {
    private int farthestX;
    private int farthestY;
    private int nextX;
    private int nextY;

    public FarthestPosition(int farthestX, int farthestY, int nextX, int nextY) {
        this.farthestX = farthestX;
        this.farthestY = farthestY;
        this.nextX = nextX;
        this.nextY = nextY;
    }

    public int getFarthestX() { return farthestX; }
    public int getFarthestY() { return farthestY; }
    public int getNextX() { return nextX; }
    public int getNextY() { return nextY; }
}