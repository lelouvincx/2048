package TwentyFortyEight;
import java.util.HashMap;
public class Cell {
    public static int GRID_SIZE = 4;
    private int x;
    private int y;
    private int value;
    private Cell[] mergedFrom; // Cell = 8 = 4 + 4 = 2 + 2 + 2 + 2
    private boolean isNew;
    private boolean isMerged;
    private boolean movedThisTurn;
    private float animX;
    private float animY;
    private boolean isAnimating;
    private float animProgress;
    private static final float ANIMATION_SPEED = 0.2f;
    private static final HashMap<Integer, Integer[]> COLORS = new HashMap<Integer, Integer[]>(); 

    static{
        COLORS.put(2,    new Integer[]{238, 228, 218});
        COLORS.put(4,    new Integer[]{237, 224, 200});
        COLORS.put(8,    new Integer[]{242, 177, 121});
        COLORS.put(16,   new Integer[]{245, 149, 99});
        COLORS.put(32,   new Integer[]{246, 124, 95});
        COLORS.put(64,   new Integer[]{246, 94, 59});
        COLORS.put(128,  new Integer[]{237, 207, 114});
        COLORS.put(256,  new Integer[]{237, 204, 97});
        COLORS.put(512,  new Integer[]{237, 200, 80});
        COLORS.put(1024, new Integer[]{237, 197, 63});
        COLORS.put(2048, new Integer[]{237, 194, 46});
    };

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.value = 0;
        this.mergedFrom = null;
        this.isNew = false;
        this.isMerged = false;
        this.movedThisTurn = false;
        this.animX = x;
        this.animY = y;
        this.isAnimating = false;
        this.animProgress = 0;
    }

    public Cell(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.mergedFrom = null;
        this.isNew = false;
        this.isMerged = false; 
        this.movedThisTurn = false;
        this.animX = x;
        this.animY = y;
        this.isAnimating = false;
        this.animProgress = 0;
    }

    // getters: x, y, value, prevX, prevY, mergedFrom, isNew, isMerged
    public int getX() { return x; }
    public int getY() { return y; }
    public int getValue() { return value; }
    public Cell[] getmergedFrom() { return mergedFrom; }
    public boolean isNew() { return isNew; }
    public boolean isMerged() { return isMerged; }
    public boolean movedThisTurn() { return movedThisTurn; }
    public float getAnimX() { return animX; }
    public float getAnimY() { return animY; }
    public boolean isAnimating() { return isAnimating; }
    public float getAnimProgress() { return animProgress; }

    // and setters
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setValue(int value) { this.value = value; }
    public void setmergedFrom(Cell[] mergedFrom) { this.mergedFrom = mergedFrom; }
    public void setisNew(boolean isNew) { this.isNew = isNew; }
    public void setisMerged(boolean isMerged) { this.isMerged = isMerged; }
    public void setmovedThisTurn(boolean movedThisTurn) { this.movedThisTurn = movedThisTurn; }
    public void setAnimX(float animX) { this.animX = animX; }
    public void setAnimY(float animY) { this.animY = animY; }
    public void setAnimating(boolean isAnimating) { this.isAnimating = isAnimating; }
    public void setAnimProgress(float animProgress) { this.animProgress = animProgress; }

    public void place() {
        if (this.value == 0) {
            this.value = (App.random.nextInt(2)+1)*2;
        }
    }

    public void savePosition() {
    }

    public void startAnimation(int targetX, int targetY) {
        this.animX = this.x;
        this.animY = this.y;
        this.x = targetX;
        this.y = targetY;
        this.isAnimating = true;
        this.animProgress = 0;
    }

    public boolean updateAnimation() {
        if (!isAnimating) return false;

        animProgress += ANIMATION_SPEED;
        if (animProgress >= 1) {
            animProgress = 1;
            isAnimating = false;
            return true;
        }
        return false;
    }

    public void updatePosition(int x, int y) {
        savePosition();
        this.x = x;
        this.y = y;
    }

    private float easeInOutQuad(float t) {
        return t < 0.5f ? 2 * t * t : 1 - (float)Math.pow(-2 * t + 2, 2) / 2;
    }

    /**
     * This draws the cell
     */
    public void draw(App app) {
        app.stroke(0, 0, 0);

        // Calculate the pixel position of the cell
        float drawX, drawY;
        if (isAnimating) {
            float eased = easeInOutQuad(animProgress);
            drawX = animX + (x - animX) * eased;
            drawY = animY + (y - animY) * eased;
        } else {
            drawX = x;
            drawY = y;
        }

        // Check for mouse hover using actual coordinates
        Integer[] col = COLORS.getOrDefault(this.value, new Integer[]{189, 172, 151});
        if (app.mouseX > drawX * App.CELLSIZE && app.mouseX < (drawX + 1) * App.CELLSIZE
                && app.mouseY > drawY * App.CELLSIZE + App.TOP_SIZE && app.mouseY < (drawY + 1) * App.CELLSIZE + App.TOP_SIZE) {
            app.fill(col[0], col[1], col[2]);
        } else {
            app.fill(col[0], col[1], col[2]);
        }

        // Draw rectangle
        app.rect(drawX * App.CELLSIZE, drawY * App.CELLSIZE + App.TOP_SIZE, App.CELLSIZE, App.CELLSIZE);

        // Write text
        if (this.value > 0) {
            app.fill(0, 0, 0);
            app.text(String.valueOf(this.value), x*App.CELLSIZE+40, y*App.CELLSIZE+App.TOP_SIZE+60);
        }

        // if (isMoving() && this.lastDirection != null) {
        //     prevXPixel += this.lastDirection.getX();
        //     prevYPixel += this.lastDirection.getY();
        // }
    }
}
