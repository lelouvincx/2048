package TwentyFortyEight;

import java.util.List;
import java.util.ArrayList;

public class Board {
    private int n;
    public Tile[][] tiles;

    public Board(int n) {
        this.n = n;
        tiles = new Tile[n][n];
    }

    // Create an empty board
    private Tile[][] empty() {
        Tile[][] tiles = new Tile[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = null;
            }
        }
        return tiles;
    }

    // Find a random available tile
    public Position randomAvailableTile() {
        List<Position> availableTiles = getAvailableTiles();
        if (!availableTiles.isEmpty()) {
            return availableTiles.get((int) (Math.random() * availableTiles.size()));
        }
        return null;
    }

    // Get the list of available tiles
    public List<Position> getAvailableTiles() {
        List<Position> availableTiles = new ArrayList<Position>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == null) {
                    availableTiles.add(new Position(i, j));
                }
            }
        }
        return availableTiles;
    }

    // Insert a tile at a specific position
    public void insertTile(Tile tile, Position position) {
        tiles[position.getX()][position.getY()] = tile;
    }

    // Remove a tile at a specific position
    public void removeTile(Position position) {
        tiles[position.getX()][position.getY()] = null;
    }

    // Check if a tile is present at a specific position
    public boolean isAvailable(Position position) {
        if (tiles[position.getX()][position.getY()] == null) {
            return true;
        }
        return false;
    }

    public boolean anyLeft() {
        // If any tiles present, return true
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getSize() { return n; }
}


