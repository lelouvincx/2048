package TwentyFortyEight;

public class Tile {
    private int x;
    private int y;
    private int value;
    private int prevX;
    private int prevY;
    private Tile[] mergedFrom;
    private boolean isNew;
    private boolean isMerged;

    public Tile(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.prevX = -1;  // Using -1 to indicate no previous position
        this.prevY = -1;
        this.mergedFrom = null;
        this.isNew = false;
        this.isMerged = false;
    }

    public Tile(int x, int y) {
        this(x, y, 0);
        this.isNew = true;
    }

    // Place a new tile by updating its value to 2 or 4
    void place() {
        if (this.value == 0) {
            this.value = (App.random.nextInt(2) + 1) * 2;
        }
    }

    public void savePosition() {
        this.prevX = this.x;
        this.prevY = this.y;
    }

    public void updatePosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getters and setters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getPrevX() {
        return prevX;
    }

    public int getPrevY() {
        return prevY;
    }

    public Tile[] getMergedFrom() {
        return mergedFrom;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public boolean isMerged() {
        return isMerged;
    }

    public void setIsMerged(boolean isMerged) {
        this.isMerged = isMerged;
    }

    public void draw(App app) {
        app.stroke(156, 139, 124);
        app.rect(x * App.CELLSIZE, y * App.CELLSIZE + App.TOP_SIZE, App.CELLSIZE, App.CELLSIZE);
        if (value > 0) {
            app.fill(255, 255, 255);
            app.text(String.valueOf(value), (x + 0.5f) * App.CELLSIZE, (y + 0.5f) * App.CELLSIZE + App.TOP_SIZE);
        }
    }
}