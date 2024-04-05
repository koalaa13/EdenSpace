package org.example.model.tetris;

public class Grid {
    private String[][] grid;
    private int capacityX;
    private int capacityY;

    public Grid(int capacityX, int capacityY) {
        this.capacityX = capacityX;
        this.capacityY = capacityY;
        grid = new String[capacityX][capacityY];
    }

    public boolean isFree(int x, int y) {
        return x >= 0 && x < capacityX && y >= 0 && y < capacityY && grid[x][y] == null;
    }

    public void setCell(int x, int y, String value) {
        grid[x][y] = value;
    }
}
