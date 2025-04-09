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
    private boolean won = false;
    private boolean over = false;
    private boolean keepPlaying = false;
    private int numStartTiles = 2;
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
        this.won = false;
        this.over = false;
        this.keepPlaying = false;

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

    }

    /**
     * Receive key released signal from the keyboard.
     */
    @Override
    public void keyReleased() {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == PConstants.LEFT) {
            this.board.addTileAt((e.getY() - App.TOP_SIZE) / App.CELLSIZE, e.getX() / App.CELLSIZE);
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

        // Draw score at top
        this.score.draw(this);

        // Draw main board
        this.board.draw(this);

    }

    public static void main(String[] args) {
        PApplet.main("TwentyFortyEight.App");
    }
}
