package TwentyFortyEight;

import java.util.List;

import processing.core.PApplet;

import java.util.ArrayList;

public class Board {
    private int size;
    public Tile[][] tiles;

    public Board(int size) {
        this.size = size;
        this.tiles = new Tile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.tiles[i][j] = null;
            }
        }
    }

    public int[] randomAvailableTile() {
        List<int[]> tiles = availableTiles();
        if (!tiles.isEmpty()) {
            return tiles.get((int) (Math.random() * tiles.size()));
        }
        return null;
    }

    public List<int[]> availableTiles() {
        List<int[]> availableTiles = new ArrayList<>();
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.tiles[i][j] == null) {
                    availableTiles.add(new int[]{i, j});
                }
            }
        }
        return availableTiles;
    }

    public boolean tilesAvailable() {
        boolean available = false;
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.tiles[i][j] == null) {
                    available = true;
                    break;
                }
            }
        }
        return available;
    }

    // Check if specified tile is available
    public boolean tileAvailable(int x, int y) {
        return this.tiles[x][y] == null;
    }

    public void insertTile(Tile tile) {
        tiles[tile.getX()][tile.getY()] = tile;
    }

    public void removeTile(Tile tile) {
        tiles[tile.getX()][tile.getY()] = null;
    }

    public boolean withinBounds(int x, int y) {
        return x >= 0 && x < this.size && y >= 0 && y < this.size;
    }

    public int getSize() { return size; }

    public void addRandomTile() {
        if (tilesAvailable()) {
            int value = (App.random.nextInt(2) + 1) * 2;
            int[] position = randomAvailableTile();
            Tile tile = new Tile(position[0], position[1], value);

            insertTile(tile);
        }
    }

    public void addTileAt(int x, int y) {
        if (tileAvailable(x, y)) {
            int value = (App.random.nextInt(2) + 1) * 2;
            Tile tile = new Tile(x, y, value);

            insertTile(tile);
        }
    }

    public void moveTile(Tile tile, int toX, int toY) {
        // Create an animation object here

        // Update the tile position
        tiles[tile.getX()][tile.getY()] = null;
        tiles[toX][toY] = tile;
        tile.updatePosition(toX, toY);
    }

    public void draw(App app) {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (tiles[i][j] != null) {
                    tiles[i][j].draw(app);
                }
                else {
                    // draw an empty tile here
                }
            }
        }
    }
}
