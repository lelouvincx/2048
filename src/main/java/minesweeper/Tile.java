package minesweeper;

import processing.core.PConstants;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

public class Tile {

    private boolean revealed;
    private int x;
    private int y;

    private boolean mine; //feel free to change this

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.revealed = false;
    }

    public void draw(App app) {
        PImage tile = app.getSprite("tile1");
        if (revealed) {
            tile = app.getSprite("tile");
        } else if (app.mouseX >= x*App.CELLSIZE && app.mouseX < (x+1)*App.CELLSIZE &&
                app.mouseY >= y*App.CELLSIZE+App.TOPBAR && app.mouseY < (y+1)*App.CELLSIZE+App.TOPBAR) {
            if (app.mousePressed && app.mouseButton == PConstants.LEFT) {
                tile = app.getSprite("tile");
            } else {
                tile = app.getSprite("tile2");
            }
        }
        app.image(tile, x*App.CELLSIZE, y*App.CELLSIZE+App.TOPBAR);
        if (this.hasAdjacentEmptyTile(app)/* && app.frameCount % 4 == 0*/) {
            this.revealed = true;
        }
    }

    public boolean hasMine() {
        return this.mine;
    }

    public void reveal() {
        this.revealed = true;
    }

    public boolean isRevealed() {
        return this.revealed;
    }

    public List<Tile> getAdjacentTiles(App app) {
        ArrayList<Tile> result = new ArrayList<>();
        if (x+1 < app.getBoard()[y].length) {
            result.add(app.getBoard()[y][x+1]);
        }
        if (y+1 < app.getBoard().length && x+1 < app.getBoard()[y].length) {
            result.add(app.getBoard()[y+1][x+1]);
        }
        if (y-1 >= 0 && x+1 < app.getBoard()[y].length) {
            result.add(app.getBoard()[y-1][x+1]);
        }
        if (y+1 < app.getBoard().length) {
            result.add(app.getBoard()[y+1][x]);
        }
        if (y-1 >= 0) {
            result.add(app.getBoard()[y-1][x]);
        }
        if (x-1 >= 0) {
            result.add(app.getBoard()[y][x-1]);
        }
        if (x-1 >= 0 && y+1 < app.getBoard().length) {
            result.add(app.getBoard()[y+1][x-1]);
        }
        if (x-1 >= 0 && y-1 >= 0) {
            result.add(app.getBoard()[y-1][x-1]);
        }
        return result;
    }

    public boolean hasAdjacentEmptyTile(App app) {
        for (Tile t : getAdjacentTiles(app)) {
            if (t.revealed && t.countAdjacentMines(app) == 0) { //ensure the cell has no adjacent mines here
                return true;
            }
        }
        return false;
    }

    public int countAdjacentMines(App app) {
        //TODO: return the number of adjacent mines to this cell
        return 0;
    }
}
