package TwentyFortyEight;

import org.checkerframework.checker.units.qual.A;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import java.io.*;
import java.util.*;

public class App extends PApplet {

    public static final int GRID_SIZE = 4; // 4x4 grid
    public static final int CELLSIZE = 100; // Cell size in pixels
    public static final int CELL_BUFFER = 8; // Space between cells
    public static final int WIDTH = GRID_SIZE * CELLSIZE;
    public static final int HEIGHT = GRID_SIZE * CELLSIZE;
    public static final int FPS = 30;
    public static final int TOP_SIZE = 200;
    

    private Board board;
    private Score score;
    private Timer timer;
    private boolean gameOver;
    public static Random random = new Random();

    private PFont font;
    public PImage eight;

    // Feel free to add any additional methods or attributes you want. Please put
    // classes in different files.

    public App() {
        this.board = new Board();
        this.score = new Score(0);
        this.timer = new Timer(0);
    }

    /**
     * Initialise the setting of the window size.
     */
    @Override
    public void settings() {
        size(WIDTH, HEIGHT+TOP_SIZE);
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player
     * and map elements.
     */
    @Override
    public void setup() {
        frameRate(FPS);
        // See PApplet javadoc:
        // loadJSONObject(configPath)
        this.eight = loadImage(this.getClass().getResource("8.png").getPath().toLowerCase(Locale.ROOT).replace("%20", ""));
        // " "));

        // create attributes for data storage, eg board
        for (int i = 0; i < this.board.getSize(); i++) {
            for (int i2 = 0; i2 < this.board.getSize(); i2++) {
                board.cells[i][i2] = new Cell(i, i2);
            }
        }

        for (int i = 0; i < 1; i++) { // twice
            this.board.addRandomCell();
        }

        timer.reset();
        score.reset();

        gameOver = false; // game has not over
    }

    private boolean checkGameOver() {
        if (!this.board.isFull())
            return false;

        for (int y = 0; y < this.board.getSize(); y++) {
            for (int x = 0; x < this.board.getSize(); x++) {
                Cell current = board.cells[y][x];

                // Check if adjacent cell can be merged
                for (Direction dir : Direction.values()) {
                    int nx = x + dir.getVector().getX();
                    int ny = y + dir.getVector().getY();

                    if (this.board.inBounds(nx, ny) && board.cells[ny][nx].getValue() == current.getValue()) {
                        return false; // Merge is possible
                    }
                }
            }
        }
        return true; // No moves or merges = Game Over
    }

    public Vector getVector(int direction) {
        Vector[] map = {
            new Vector(-1, 0), // 0: UP
            new Vector(0, 1), // 1: RIGHT
            new Vector(1, 0), // 2: DOWN
            new Vector(0, -1) // 3: LEFT
        };
        return map[direction];
    }

    private Traversal buildTraversal(Vector vector) {
        Traversal traversal = new Traversal();

        for (int i = 0; i < this.board.getSize(); i++) {
            traversal.addX(i);
            traversal.addY(i);
        }
        // x: 0 1 2 3
        // y: 0 1 2 3

        // if right or down, reverse the list
        if (vector.getX() == -1) traversal.reverseX();
        if (vector.getY() == -1) traversal.reverseY();

        return traversal;
    }

    private FarthestPosition findFarthestPosition(int x, int y, Vector vector) {
        // 2 _ 2 _
        int prevX = x, prevY = y;
        int nextX = x;
        int nextY = y;

        do {
            prevX = nextX;
            prevY = nextY;
            nextX += vector.getX();
            nextY += vector.getY();
        } while (this.board.inBounds(nextY, nextX) && this.board.cells[nextY][nextX].getValue() == 0);

        return new FarthestPosition(prevX, prevY, x + vector.getX(), y + vector.getY());
    }

    private boolean move(int direction) { // 0, 1, 2, 3
        // 0: UP
        // 1: RIGHT
        // 2: DOWN
        // 3: LEFT
        // if (gameOver) return false;

        System.out.println("Direction: " + direction);
        Vector vector = getVector(direction);
        Traversal traversal = buildTraversal(vector);
        boolean moved = false;

        this.board.prepareCells();

        for (int i: traversal.getX()) {
            for (int j: traversal.getY()) {
                int x = i;
                int y = j;

                Cell cell = this.board.cells[x][y];
                if (cell.getValue() == 0) continue;
                System.out.println("x: " + x + ", y: " + y + ", value: " + cell.getValue());

                // 2 _ 2 _
                FarthestPosition farthestPosition = findFarthestPosition(x, y, vector);
                System.out.println("farthestPosition - x: " + farthestPosition.getFarthestX() + ", y: " + farthestPosition.getFarthestY());
                System.out.println("next - x: " + farthestPosition.getNextX() + ", y: " + farthestPosition.getNextY());
                Cell next = this.board.cells[farthestPosition.getNextX()][farthestPosition.getNextY()];

                // if (next != null && next.getValue() != 0 && next.getValue() == cell.getValue() && next.getmergedFrom() == null) {
                //     // Merge them
                //     Cell mergedCell = new Cell(farthestPosition.getFarthestY(), farthestPosition.getFarthestX(), cell.getValue() * 2);
                //     System.out.println("mergedCell - x: " + mergedCell.getX() + ", y: " + mergedCell.getY());
                //     mergedCell.setmergedFrom(new Cell[] {cell, next});
                //     mergedCell.setisMerged(true);

                //     this.board.insertCell(mergedCell);
                //     this.board.removeCell(cell);

                //     cell.updatePosition(farthestPosition.getNextY(), farthestPosition.getNextX());

                //     // increase the score
                //     this.score.addScore(mergedCell.getValue());
                // } else {
                    // this.board.moveCell(cell, farthestPosition.getFarthestY(), farthestPosition.getFarthestX());
                // }

                // check if tile moved
                // if (cell.getX() != x || cell.getY() != y) {
                //     moved = true;
                // }
            }
        }

        // if (moved) {
        //     this.board.addRandomCell();
        //     if (checkGameOver()) {
        //         this.gameOver = true;
        //     }
        // }

        return true;
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
    @Override
    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();

        switch (key) {
            case java.awt.event.KeyEvent.VK_LEFT: move(3); break;
            case java.awt.event.KeyEvent.VK_RIGHT: move(1); break;
            case java.awt.event.KeyEvent.VK_UP: move(0); break;
            case java.awt.event.KeyEvent.VK_DOWN: move(2); break;
            case 'R': setup();
            case 'r': setup();
        }
    }

    /**
     * Receive key released signal from the keyboard.
     */
    @Override
    public void keyReleased() {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == PConstants.LEFT) {
            if (e.getY() < App.TOP_SIZE) return;

            int x = e.getX()/App.CELLSIZE;
            int y = (e.getY() - App.TOP_SIZE)/App.CELLSIZE;
            System.out.println(e.getX());
            System.out.println(e.getY());
            this.board.addCellAt(x, y);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Draw all elements in the game by current frame.
     */
    @Override
    public void draw() {
        // draw game board
        this.textSize(40);
        this.strokeWeight(15);

        this.timer.draw(this);
        this.score.draw(this);
        
        for (int i = 0; i < this.board.getSize(); i++) {
            for (int i2 = 0; i2 < this.board.getSize(); i2++) {
                this.board.cells[i][i2].draw(this);
                // System.out.println(this.board.cells[i][i2].getValue());
            }
        }

        if (this.gameOver) {
            fill(255, 0, 0);
            textSize(48);
            textAlign(CENTER, CENTER);
            text("GAME OVER", WIDTH / 2f, HEIGHT / 2f + TOP_SIZE / 2f);
        }
    }

    public static void main(String[] args) {
        PApplet.main("TwentyFortyEight.App");
    }
}
