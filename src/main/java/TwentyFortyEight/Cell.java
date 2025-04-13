package TwentyFortyEight;
import java.util.HashMap;
public class Cell {
    public static int GRID_SIZE = 4;
    private int x;
    private int y;
    private int value;
    private int prevX;
    private int prevY;
    private Cell[] mergedFrom; // Cell = 8 = 4 + 4 = 2 + 2 + 2 + 2
    private boolean isNew;
    private boolean isMerged;
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
        this.prevX = -1;
        this.prevY = -1;
        this.mergedFrom = null;
        this.isNew = false;
        this.isMerged = false;
    }

    public Cell(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
        this.prevX = -1;
        this.prevY = -1;
        this.mergedFrom = null;
        this.isNew = false;
        this.isMerged = false; 
    }

    // getters: x, y, value, prevX, prevY, mergedFrom, isNew, isMerged
    public int getX() { return x; }
    public int getY() { return y; }
    public int getValue() { return value; }
    public int getprevX() { return prevX; }
    public int getprevY() { return prevY; }
    public Cell[] getmergedFrom() { return mergedFrom; }
    public boolean isNew() { return isNew; }
    public boolean isMerged() { return isMerged; }

    // and setters
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setValue(int value) { this.value = value; }
    public void setprevX(int prevX) { this.prevX = prevX; }
    public void setprevY(int prevY) { this.prevY=prevY; }
    public void setmergedFrom(Cell[] mergedFrom){ this.mergedFrom = mergedFrom; }
    public void setisNew(boolean isNew){ this.isNew = isNew; }
    public void setisMerged(boolean isMerged){ this.isMerged = isMerged; }

    public void place() {
        if (this.value == 0) {
            this.value = (App.random.nextInt(2)+1)*2;
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

    /**
     * This draws the cell
     */
    public void draw(App app) {
        app.stroke(156, 139, 124);

        if (app.mouseX > x*App.CELLSIZE && app.mouseX < (x+1)*App.CELLSIZE 
            && app.mouseY > y*App.CELLSIZE+App.TOP_SIZE && app.mouseY < (y+1)*App.CELLSIZE+App.TOP_SIZE) {
            Integer[] col = COLORS.getOrDefault(this.value, new Integer[]{189, 172, 151});
            app.fill(col[0], col[1], col[2]);
        } else {
            Integer[] col = COLORS.getOrDefault(this.value, new Integer[]{189, 172, 151});
            app.fill(col[0], col[1], col[2]);
        }

        // Write text
        app.rect(x*App.CELLSIZE, y*App.CELLSIZE+ App.TOP_SIZE, App.CELLSIZE, App.CELLSIZE);
        if (this.value > 0) {
            app.fill(0, 0, 0);
            app.text(String.valueOf(this.value), (x+0.4f)*App.CELLSIZE, (y+0.6f)*App.CELLSIZE+App.TOP_SIZE);
        }
    }
}
