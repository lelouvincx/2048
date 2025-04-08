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
    public static final int TOP_SIZE=200;

    private Cell[][] board;
    private Score score;
    public static Random random = new Random();

    private PFont font;
    public PImage eight;

    // Feel free to add any additional methods or attributes you want. Please put
    // classes in different files.

    public App() {
        this.board = new Cell[4][4];
        this.score=new Score(0);

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
        for (int i = 0; i < board.length; i++) {
            for (int i2 = 0; i2 < board[i].length; i2++) {
                board[i][i2] = new Cell(i2, i);
            }
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
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == PConstants.LEFT) {
            Cell current = board[(e.getY()-App.TOP_SIZE)/App.CELLSIZE][e.getX()/App.CELLSIZE];
            current.place();
            this.score.addScore(current.getValue());
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
        
        for (int i = 0; i < board.length; i++) {
            for (int i2 = 0; i2 < board[i].length; i2++) {
                board[i][i2].draw(this);
            }
        }

        this.score.draw(this);

    }

    public static void main(String[] args) {
        PApplet.main("TwentyFortyEight.App");
    }

}
