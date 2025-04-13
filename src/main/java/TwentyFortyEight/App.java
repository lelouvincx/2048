package TwentyFortyEight;

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
    private boolean won = false;
    private boolean over = false;
    private boolean keepPlaying = false;
    private int numStartTiles = 2;
    private boolean messageDisplayed = false;
    private String message;
    public static Random random = new Random();

    private PFont font;

    // Feel free to add any additional methods or attributes you want. Please put
    // classes in different files.

    public App() { }

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
        // frameRate(FPS);
        // See PApplet javadoc:
        // loadJSONObject(configPath)
        // loadImage(this.getClass().getResource(filename).getPath().toLowerCase(Locale.ROOT).replace("%20",
        // " "));
        textAlign(CENTER, CENTER);
        rectMode(CENTER);
        this.board = new Board(4);
        this.score = new Score(0);
        this.timer = new Timer(0);
        this.won = false;
        this.over = false;
        this.keepPlaying = false;
        this.messageDisplayed = false;
        this.message = "";

        // Initial 2 start tiles
        for (int i = 0; i < 2; i++) {
            this.board.addRandomTile();
        }
    }


    /**
     * Receive key pressed signal from the keyboard.
     */
    @Override
    public void keyPressed(KeyEvent event) {
        // Handle key press for up, right, down, left
        // If 'r', restart game
        if (event.getKeyCode() == UP) {
            this.move(0);
        }
        else if (event.getKeyCode() == RIGHT) {
            this.move(1);
        }
        else if (event.getKeyCode() == DOWN) {
            this.move(2);
        }
        else if (event.getKeyCode() == LEFT) {
            this.move(3);
        } else if (event.getKeyCode() == 'r') {
            setup();
        }
    }

    /**
     * Receive key released signal from the keyboard.
     */
    @Override
    public void keyReleased(KeyEvent event) { }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == PConstants.LEFT) {
            this.board.addTileAt((e.getY() - App.TOP_SIZE) / App.CELLSIZE, e.getX() / App.CELLSIZE);
        }
    }

    public Vector getVector(int direction) {
        Vector[] map = {
            new Vector(0, -1), // UP
            new Vector(1, 0), // RIGHT
            new Vector(0, 1), // DOWN
            new Vector(-1, 0) // LEFT
        };
        return map[direction];
    }

    private Traversal buildTraversals(Vector vector) {
        Traversal traversal = new Traversal();

        for (int i = 0; i < GRID_SIZE; i++) {
            traversal.addX(i);
            traversal.addY(i);
        }

        // Always traverse from the farthest cell in the chosen direction
        if (vector.getX() == 1) traversal.reverseX();
        if (vector.getY() == 1) traversal.reverseY();

        return traversal;
    }

    private FarthestPosition findFarthestPosition(int x, int y, Vector vector) {
        int prevX, prevY;
        int nextX = x;
        int nextY = y;

        // Progress towards the vector direction until an obstacle is found
        do {
            prevX = nextX;
            prevY = nextY;
            nextX = prevX + vector.getX();
            nextY = prevY + vector.getY();
        } while (this.board.withinBounds(nextX, nextY) && this.board.tileAvailable(nextX, nextY));

        return new FarthestPosition(prevX, prevY, nextX, nextY);
    }

    private void move(int direction) {
        if (over) return;

        Vector vector = getVector(direction);
        Traversal traversal = buildTraversals(vector);
        boolean moved = false;

        this.board.prepareTiles();

        for (int x: traversal.getX()) {
            for (int y: traversal.getY()) {
                Tile tile = this.board.getTile(x, y);
                if (tile == null) continue;

                FarthestPosition farthestPosition = findFarthestPosition(x, y, vector);
                Tile next = this.board.getTile(farthestPosition.getNextX(), farthestPosition.getNextY());

                // On one row/column traversal, only one merger is possible
                if (next != null && next.getValue() == tile.getValue() && next.getMergedFrom() == null) {
                    Tile mergedTile = new Tile(farthestPosition.getFarthestX(), farthestPosition.getFarthestY(), tile.getValue() * 2);
                    mergedTile.setMergedFrom(new Tile[] {tile, next});
                    mergedTile.setIsMerged(true);

                    this.board.insertTile(mergedTile);
                    this.board.removeTile(tile);

                    tile.updatePosition(farthestPosition.getNextX(), farthestPosition.getNextY());

                    // Increase the score
                    this.score.addScore(mergedTile.getValue());
                }
                else { // Just move the tile
                    this.board.moveTile(tile, farthestPosition.getFarthestX(), farthestPosition.getFarthestY());
                }

                // Check if the tile moved
                if (tile.getX() != x || tile.getY() != y) {
                    moved = true;
                }
            }
        }

        if (moved) {
            this.board.addRandomTile();
            if (!this.board.movesAvailable(this)) {
                this.over = true;
            }

            if (over) {
                message = "Game Over";
                messageDisplayed = true;
            }
        }
    }

    /**
     * Draw all elements in the game by current frame.
     */
    @Override
    public void draw() {
        // Game background
        this.background(250, 248, 239);
        this.textSize(40);
        this.strokeWeight(15);

        // Draw score and timer at top
        this.score.draw(this);
        this.timer.draw(this);

        // Draw main board
        this.board.draw(this);

        // If message is displayed, draw it
        if (messageDisplayed) {
            fill(255, 255, 255, 180);
            rectMode(CORNER);
            rect(0, 0, width, height);
            fill(0, 0, 0);
            textSize(40);
            textAlign(CENTER, CENTER);
            text(message, width/2, height/2 - 30);
        }
    }

    public static void main(String[] args) {
        PApplet.main("TwentyFortyEight.App");
    }
}
