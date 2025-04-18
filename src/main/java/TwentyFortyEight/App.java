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
    private int numStartCells;
    private boolean gameOver;
    private boolean isAnimating = false;
    public static Random random = new Random();

    private PFont font;
    public PImage eight;

    // Feel free to add any additional methods or attributes you want. Please put
    // classes in different files.

    public App() {
        this.board = new Board();
        this.score = new Score(0);
        this.timer = new Timer(0);
        this.numStartCells = 2;
    }

    /**
     * Initialise the setting of the window size.
     */
    @Override
    public void settings() {
        size(WIDTH, HEIGHT + TOP_SIZE);
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
        this.eight = loadImage(
                this.getClass().getResource("8.png").getPath().toLowerCase(Locale.ROOT).replace("%20", ""));
        // " "));

        // create attributes for data storage, eg board
        for (int i = 0; i < this.board.getSize(); i++) {
            for (int i2 = 0; i2 < this.board.getSize(); i2++) {
                board.cells[i][i2] = new Cell(i, i2);
            }
        }

        for (int i = 0; i < numStartCells; i++) {
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
                new Vector(0, -1), // 0: UP
                new Vector(1, 0), // 1: RIGHT
                new Vector(0, 1), // 2: DOWN
                new Vector(-1, 0) // 3: LEFT
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

        // if left or up, reverse the list
        if (vector.getX() == -1)
            traversal.reverseX();
        if (vector.getY() == -1)
            traversal.reverseY();

        return traversal;
    }

    private FarthestPosition findFarthestPosition(int x, int y, Vector vector) {
        // 2 _ 2 _
        int prevX = x, prevY = y;
        int nextX = x;
        int nextY = y;

        do {
            nextX += vector.getX();
            nextY += vector.getY();

            if (!this.board.inBounds(nextY, nextX) || this.board.cells[nextY][nextX].getValue() != 0) {
                break;
            }

            prevX = nextX;
            prevY = nextY;
        } while (true);

        nextX = prevX + vector.getX();
        nextY = prevY + vector.getY();

        if (!this.board.inBounds(nextY, nextX) || this.board.cells[nextY][nextX].getValue() != 0) {
            nextX = prevX;
            nextY = prevY;
        }

        System.out.println("DEBUG - prevX: " + prevX + ", prevY: " + prevY);
        System.out.println("DEBUG - nextX: " + nextX + ", nextY: " + nextY);
        return new FarthestPosition(prevX, prevY, nextX, nextY);
    }

    private boolean move(int direction) { // 0, 1, 2, 3
        // 0: UP
        // 1: RIGHT
        // 2: DOWN
        // 3: LEFT
        if (gameOver)
            return false;

        System.out.println("Direction: " + direction);
        Vector vector = getVector(direction);
        Traversal traversal = buildTraversal(vector);
        System.out.println("\nTraversal x:");
        for (int i : traversal.getX()) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("Traversal y:");
        for (int i : traversal.getY()) {
            System.out.print(i + " ");
        }
        System.out.println("\n");
        boolean moved = false;

        this.board.prepareCells();

        for (int i = 0; i < this.board.getSize(); i++) {
            for (int j = 0; j < this.board.getSize(); j++) {
                System.out.print(this.board.cells[j][i].getValue() + " ");
            }
            System.out.println();
        }

        System.out.println();
        for (int i : traversal.getX()) {
            for (int j : traversal.getY()) {
                int x = i;
                int y = j;

                Cell cell = this.board.cells[y][x];
                if (cell.getValue() == 0 || cell.movedThisTurn())
                    continue;
                System.out.println("x: " + cell.getX() + ", y: " + cell.getY() + ", value: " + cell.getValue());

                // 2 _ 2 _
                FarthestPosition farthestPosition = findFarthestPosition(cell.getX(), cell.getY(), vector);
                System.out.println("farthestPosition - x: " + farthestPosition.getFarthestX() + ", y: "
                        + farthestPosition.getFarthestY());
                System.out.println("next - x: " + farthestPosition.getNextX() + ", y: " + farthestPosition.getNextY());
                if (farthestPosition.getFarthestX() == cell.getX() && farthestPosition.getFarthestY() == cell.getY()) {
                    System.out.println("Skip cell");
                    continue;
                }
                if (this.board.inBounds(farthestPosition.getNextY(), farthestPosition.getNextX())) {
                    Cell next = this.board.cells[farthestPosition.getNextX()][farthestPosition.getNextY()];
                    if (next.getValue() != 0 && next.getValue() == cell.getValue() && next.getmergedFrom() == null) {
                        // Merge cells: cell with next
                        System.out.println("DEBUG - Merging cell at x: " + cell.getX() + ", y: " + cell.getY()
                                + ", value: " + cell.getValue() + " with next - x: " + next.getX() + ", y: "
                                + next.getY() + ", value: " + next.getValue());

                        Cell mergedCell = new Cell(farthestPosition.getNextX(), farthestPosition.getNextY(),
                                cell.getValue() * 2);
                        mergedCell.setmergedFrom(new Cell[] { cell, next });
                        mergedCell.setisMerged(true);
                        mergedCell.setmovedThisTurn(true);

                        cell.startAnimation(farthestPosition.getNextX(), farthestPosition.getNextY());

                        System.out.println("mergedCell - x: " + mergedCell.getX() + ", y: " + mergedCell.getY()
                                + ", value: " + mergedCell.getValue());

                        // Dont need to remove for 'next' because it insertCell will overwrite it
                        this.board.removeCell(cell);
                        this.board.insertCell(mergedCell);

                        cell.updatePosition(farthestPosition.getNextX(), farthestPosition.getNextY());

                        // increase the score
                        this.score.addScore(mergedCell.getValue());
                        moved = true;
                        System.out.println();
                    } else {
                        System.out.println("Moving cell to - x: " + farthestPosition.getFarthestX() + ", y: "
                                + farthestPosition.getFarthestY());
                        this.board.moveCell(cell, farthestPosition.getFarthestX(), farthestPosition.getFarthestY(),
                                vector);
                        cell.setmovedThisTurn(true);
                        moved = true;
                        System.out.println();
                    }
                }
            }
        }

        if (moved) {
            this.board.addRandomCell();
            if (checkGameOver()) {
                this.gameOver = true;
            }
        }

        return true;
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
    @Override
    public void keyPressed(KeyEvent event) {
        if (isAnimating) return;

        int key = event.getKeyCode();

        switch (key) {
            case java.awt.event.KeyEvent.VK_LEFT:
                move(3);
                break;
            case java.awt.event.KeyEvent.VK_RIGHT:
                move(1);
                break;
            case java.awt.event.KeyEvent.VK_UP:
                move(0);
                break;
            case java.awt.event.KeyEvent.VK_DOWN:
                move(2);
                break;
            case 'R':
                setup();
            case 'r':
                setup();
        }
    }

    /**
     * Receive key released signal from the keyboard.
     */
    @Override
    public void keyReleased() { }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isAnimating) return;

        if (e.getButton() == PConstants.LEFT) {
            if (e.getY() < App.TOP_SIZE)
                return;

            int x = e.getX() / App.CELLSIZE;
            int y = (e.getY() - App.TOP_SIZE) / App.CELLSIZE;
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

        boolean stillAnimating = false;
        for (int i = 0; i < this.board.getSize(); i++) {
            for (int i2 = 0; i2 < this.board.getSize(); i2++) {
                Cell cell = this.board.cells[i][i2];
                if (cell.getValue() > 0 && cell.isAnimating()) {
                    boolean done = cell.updateAnimation();
                    if (!done) stillAnimating = true;
                }
                cell.draw(this);
            }
        }
        isAnimating = stillAnimating;

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
