package main;

import materials.Grain;
import materials.MaterialManager;

import java.awt.*;

public class Cell {
    public int x, y, size;
    public Color color;
    public Grain grain;

    public Cell(int[] pos, int size, World world) {
        this.x = pos[0];
        this.y = pos[1];
        this.size = size;
        grain = MaterialManager.empty(x, y, world);
    }
}
