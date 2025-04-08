package TwentyFortyEight;

public class Tile {
    private Position position;
    private int value;
    private Position prevPosition;
    private Tile[] mergedFrom;
    private boolean isNew;
    private boolean isMerged;
    private float animationProgress;

    public Tile(Position position, int value) {
        this.position = position;
        this.value = value;
        this.prevPosition = null;
        this.mergedFrom = null;
        this.isNew = false;
        this.isMerged = false;
        this.animationProgress = 0;
    }

    public void savePosition() {
        this.prevPosition = new Position(position.getX(), position.getY());
    }

    public void updatePosition(Position position) {
        this.position = position;
    }

    // Getters and setters
    public int getX() { return position.getX(); }

    public int getY() { return position.getY(); }

    public int getValue() { return value; }
    public void setValue(int value) { this.value = value; }

    public Position getPosition() { return position; }

    public Position getPrevPosition() { return prevPosition; }

    public Tile[] getMergedFrom() { return mergedFrom; }

    public boolean isNew() { return isNew; }
    public void setIsNew(boolean isNew) { this.isNew = isNew; }

    public boolean isMerged() { return isMerged; }
    public void setIsMerged(boolean isMerged) { this.isMerged = isMerged; }

    public float getAnimationProgress() { return animationProgress; }
    public void setAnimationProgress(float animationProgress) {
        this.animationProgress = animationProgress;
    }
}