package TwentyFortyEight;

import java.util.ArrayList;

public class Board {
    private int size;
    public Cell[][] cells;

    public Board(int size) {
        this.size = size;
        this.cells = new Cell[size][size];
    }

    public Board() {
        this.size = 4;
        this.cells = new Cell[size][size];
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.cells[i][j] = null;
            }
        }
    }

    // get size
    public int getSize() { return size; }

    /*
    availableCells
    Get a list of position (including x, y) of cells that are null
    For example:
    List<[0, 0], [1, 1], [2, 2]>
    */
    public ArrayList<int[]> availableCells() {
        ArrayList<int[]> availableCells = new ArrayList<int[]>();

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.cells[i][j].getValue() == 0) {
                    availableCells.add(new int[]{i, j});
                }
            }
        }

        return availableCells;
    }

    // randomAvailableCell
    public int[] randomAvailableCell() { // [1, 1]
        ArrayList emptyCells = availableCells();
        if (emptyCells.size() > 0) {
            int[] pick = (int[]) emptyCells.get(App.random.nextInt(emptyCells.size()));
            return pick;
        }
        return null;
    }

    // isFull
    public boolean isFull() {
        ArrayList emptyCells = availableCells();
        if (emptyCells.size() > 0) {
            return false;
        }
        else {
            return true;
        }
    }

    // addRandomCell
    public void addRandomCell() {
        if (isFull() == false) {
            int[] position = randomAvailableCell();
            int value = (App.random.nextInt(2)+1)*2; // 0/1 ==> 1/2 ==> 2/4
            Cell cell = new Cell(position[0], position[1], value);

            insertCell(cell);
        }
    }

    // addCellAt
    public void addCellAt(int x, int y) {
        if (!inBounds(x, y)) return;

        // this.cells[x][y] must have 0 in value
        if (this.cells[x][y].getValue() != 0) return;

        int value = (App.random.nextInt(2)+1)*2;
        System.out.println("New cell at: " + x + " " + y + " with value " + value);
        Cell cell = new Cell(x, y, value);
        insertCell(cell);
    }

    // insertCell
    public void insertCell(Cell cell) {
        this.cells[cell.getX()][cell.getY()] = cell;
    }

    // removeCell
    public void removeCell(Cell cell) {
        this.cells[cell.getX()][cell.getY()].setValue(0);
    }

    public boolean inBounds(int x, int y) {
        return x >= 0 && x < this.size && y >= 0 && y < this.size;
    }

    // prepareTiles
    public void prepareCells() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                Cell cell = this.cells[i][j];
                if (cell.getValue() != 0) {
                    cell.setmergedFrom(null);
                    cell.savePosition();
                    cell.setisNew(false);
                }
            }
        }
    }

    // moveCell
    public void moveCell(Cell cell, int toX, int toY, Vector direction) {
        this.cells[cell.getX()][cell.getY()] = new Cell(cell.getX(), cell.getY(), 0);

        this.cells[toX][toY] = cell;
        cell.updatePosition(toX, toY);
        cell.setLastDirection(direction);
    }

    // draw
    public void draw(App app) {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                this.cells[i][j].draw(app);
            }
        }
    }
}
