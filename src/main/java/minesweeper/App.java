package minesweeper;

import org.checkerframework.checker.units.qual.A;
import processing.core.PApplet;
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

    public static final int CELLSIZE = 32; //8;
    public static final int CELLHEIGHT = 32;

    public static final int CELLAVG = 32;
    public static final int TOPBAR = 64;
    public static int WIDTH = 864; //CELLSIZE*BOARD_WIDTH;
    public static int HEIGHT = 640; //BOARD_HEIGHT*CELLSIZE+TOPBAR;
    public static final int BOARD_WIDTH = WIDTH/CELLSIZE;
    public static final int BOARD_HEIGHT = 20;

    public static final int FPS = 30;

    public String configPath;

    public static Random random = new Random();
	
	public static int[][] mineCountColour = new int[][] {
            {0,0,0}, // 0 is not shown
            {0,0,255},
            {0,133,0},
            {255,0,0},
            {0,0,132},
            {132,0,0},
            {0,132,132},
            {132,0,132},
            {32,32,32}
    };
	
	// Feel free to add any additional methods or attributes you want. Please put classes in different files.

    public App() {
        this.configPath = "config.json";
    }

    /**
     * Initialise the setting of the window size.
     */
	@Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    private Tile[][] board;
    private HashMap<String, PImage> sprites = new HashMap<>();

    public PImage getSprite(String s) {
        PImage result = sprites.get(s);
        if (result == null) {
            result = loadImage(this.getClass().getResource(s+".png").getPath().toLowerCase(Locale.ROOT).replace("%20", " "));
            sprites.put(s, result);
        }
        return result;
    }

    public Tile[][] getBoard() {
        return this.board;
    }

    /**
     * Load all resources such as images. Initialise the elements such as the player and map elements.
     */
	@Override
    public void setup() {
        frameRate(FPS);
		//See PApplet javadoc:
		//loadJSONObject(configPath)
		//loadImage(this.getClass().getResource(filename).getPath().toLowerCase(Locale.ROOT).replace("%20", " "));
        String[] sprites = new String[] {
                "tile1",
                "tile2",
                "flag",
                "tile"
        };
        for (int i = 0; i < sprites.length; i++) {
            getSprite(sprites[i]);
        }
        for (int i = 0; i < 10; i++) {
            getSprite("mine"+String.valueOf(i));
        }

        //create attributes for data storage, eg board
        this.board = new Tile[(HEIGHT-TOPBAR)/CELLSIZE][WIDTH/CELLSIZE];

        for (int i = 0; i < this.board.length; i++) {
            for (int i2 = 0; i2 < this.board[i].length; i2++) {
                this.board[i][i2] = new Tile(i2, i);
            }
        }

        this.board[3][5].mine = true;
    }

    /**
     * Receive key pressed signal from the keyboard.
     */
	@Override
    public void keyPressed(KeyEvent event){
        
    }

    /**
     * Receive key released signal from the keyboard.
     */
	@Override
    public void keyReleased(){
        
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //set tile status to revealed
        int mouseX = e.getX();
        int mouseY = e.getY()-App.TOPBAR;
        if (mouseX >= 0 && mouseX < WIDTH && mouseY >= TOPBAR && mouseY < HEIGHT) {
            Tile t = board[mouseY/App.CELLSIZE][mouseX/App.CELLSIZE];
            t.reveal();
        }
    }

    /**
     * Draw all elements in the game by current frame.
     */
	@Override
    public void draw() {
        //draw game board
        background(200,200,200);
        int nonRevealedNonMines = 0;
        for (int i = 0; i < this.board.length; i++) {
            for (int i2 = 0; i2 < this.board[i].length; i2++) {
                this.board[i][i2].draw(this);
                if (!this.board[i][i2].isRevealed() && !this.board[i][i2].hasMine()) {
                    nonRevealedNonMines += 1;
                }
            }
        }

        if (nonRevealedNonMines == 0) {
            textSize(30);
            fill(0);
            text("You win!", 150, App.TOPBAR-10);
        }
    }


    public static void main(String[] args) {
        PApplet.main("minesweeper.App");
    }

}
