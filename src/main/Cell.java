package main;

import materials.Grain;

import java.awt.*;

public class Cell {
    public int x, y, size;
    public Color color;
    public Grain grain = null;

    public Cell(int[] pos, int size) {
        this.x = pos[0];
        this.y = pos[1];
        this.size = size;
    }
}
