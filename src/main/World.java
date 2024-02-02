package main;

import materials.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class World {

    public Rectangle rect;

    public int size, target_fps;
    
    public Cell[][] grid;
    public int[][][] grid_indexes;

    public final ArrayList<String> burnables = new ArrayList<String>();

    public final ArrayList<String> nonCorrodibles = new ArrayList<String>();

    private int updateLoops = 0;

    public World(Rectangle rect, int size, int target_fps) {
        this.rect = rect;
        this.size = size;

        this.target_fps = target_fps;

        this.grid = new Cell[rect.width / size][rect.height / size];

        // create a 2D array where each element is an array of its own index
        grid_indexes = new int[rect.width / size][rect.height / size][2];
        for (int i = 0; i < rect.width / size; i++) {
            for (int j = 0; j < rect.height / size; j++) {
                grid_indexes[i][j][0] = i;
                grid_indexes[i][j][1] = j;
            }
        }

        CreateGrid();
    }

    private void CreateGrid() {
        for (int x = 0; x < rect.width / size; x++) {
            for (int y = 0; y < rect.height / size; y++) {
                int[] pos = GetPosFromIndex(x, y);

                Cell c = new Cell(pos, size, this);
                grid[x][y] = c;
            }
        }
    }
    
    // g2 is a canvas like object
    public void draw(Graphics2D g2) {
        // Border Color
        g2.setColor(Colors.BORDER);
        // Draw Border
         g2.drawRect(rect.x, rect.y, rect.width, rect.height);

        for (int i = 0; i < rect.width / size; i++) {
            for (int j = 0; j < rect.height / size; j++) {
                if (!Objects.equals(grid[i][j].grain.GetMaterialType(), MaterialManager.empty)) {
                    grid[i][j].grain.draw(g2);
                }
            }
        }
    }
    
    public void addMaterial(String material, int i, int j, World world) {
        int[] pos = GetPosFromIndex(i, j);
        if (CheckIfPosInBounds(pos[0], pos[1])) {
            grid[i][j].grain = MaterialManager.GetMaterial(material, pos[0], pos[1], world);

            if (!Objects.equals(grid[i][j].grain.GetMaterialType(), MaterialManager.empty)) {
                grid[i][j].grain.world = this;
            }
        }
        if (material.equals(MaterialManager.erase))
        {
            Erase(pos[0], pos[1]);
        }
    }

    
    public void update() {
        if (updateLoops >= 10) {
            // randomly shuffle the array so that each element in the array contains a unique index that points to another random index
            for (int i = 0; i < rect.width / size; i++) {
                for (int j = 0; j < rect.height / size; j++) {
                    int[] index = new int[]{(int) (Math.random() * (rect.width / size) - 1), (int) (Math.random() * (rect.height / size) - 1)};
                    int[] temp = grid_indexes[i][j];
                    grid_indexes[i][j] = grid_indexes[index[0]][index[1]];
                    grid_indexes[index[0]][index[1]] = temp;
                }
            }
            updateLoops = 0;
        }

        // loop through each element and get the randomly shuffled index's for each non-shuffled index's
        // this randomly updates every cell once every time
        for (int i = 0; i < rect.width / size; i++) {
            for (int j = 0; j < rect.height / size; j++) {
                int[] index = grid_indexes[i][j];
                if (!Objects.equals(grid[index[0]][index[1]].grain.materialType, MaterialManager.empty)) {
                    Grain grain = grid[index[0]][index[1]].grain;
                    grain.update();
                    grain.CheckMassPriority();
                }
            }
        }
        updateLoops ++;
    }
    
    public int[] GetPosFromIndex(int i, int j) {
        return new int[]{rect.x + (i * size), rect.y + (j * size)};
    }

    public int[] GetIndexFromPos(int x, int y) {
        return new int[]{(x - rect.x) / size, (y - rect.y) / size};
    }

    
    public ArrayList<Grain> GetAll(int x, int y) {
        ArrayList<Grain> surroundings = new ArrayList<>();
        int[] index = GetIndexFromPos(x, y);
        
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                 if (CheckIfIndexInBounds(index[0] + i, index[1] + j)) {
                    if (!Objects.equals(grid[index[0] + i][index[1] + j].grain.materialType, MaterialManager.empty)) {
                        surroundings.add(grid[index[0] + i][index[1] + j].grain);
                    }
                }
            }
        }
        
        return surroundings;
    }
    
    public boolean CheckIfPosInBounds(int x, int y) {
        return (rect.x <= x && x < (rect.x + rect.width) - (rect.width % size)) && (rect.y <= y && y < (rect.y + rect.height) - (rect.height % size));
    }
    
    public boolean CheckIfIndexInBounds(int i, int j) {
        return (0 <= i && i < (rect.width / size) && 0 <= j && j < (rect.height / size));
    }
    
    public boolean CheckIfPosCanMove(int x, int y) {
        int[] index = GetIndexFromPos(x, y);
        if (CheckIfPosInBounds(x, y)) {
            return Objects.equals(grid[index[0]][index[1]].grain.materialType, MaterialManager.empty);
        }
        return false;
    }
    
    public void MoveGrain(Grain grain, int[] direction) {
        if (!CheckIfPosInBounds(grain.x + (Constants.CELL_SIZE * direction[0]), grain.y + (Constants.CELL_SIZE * direction[1]))) {
            return;
        }

        // current pos
        int[] index = GetIndexFromPos(grain.x, grain.y) ;
        int i1 = index[0];
        int j1 = index[1];

        // future pos
        index = GetIndexFromPos(grain.x + (Constants.CELL_SIZE * direction[0]), grain.y + (Constants.CELL_SIZE * direction[1]));
        int i2 = index[0];
        int j2 = index[1];

        if (Objects.equals(grid[i2][j2].grain.materialType, MaterialManager.empty)) {
            Grain empty = grid[i2][j2].grain;
            grid[i2][j2].grain = grid[i1][j1].grain;
            grid[i1][j1].grain = empty;

            grain.x += (Constants.CELL_SIZE * direction[0]);
            grain.y += (Constants.CELL_SIZE * direction[1]);

        }
    }

    public void DisplaceGrain(Grain grain1, Grain grain2) {
        int[] index1 = GetIndexFromPos(grain1.x, grain1.y);
        int[] index2 = GetIndexFromPos(grain2.x, grain2.y);

        grid[index1[0]][index1[1]].grain = grain2;
        grid[index2[0]][index2[1]].grain = grain1;

        int tempY = grain1.y;
        grain1.y = grain2.y;
        grain2.y = tempY;
    }

    public void Erase(int x, int y) {
        if (!CheckIfPosInBounds(x, y)) {
            return;
        }
        int[] index = GetIndexFromPos(x, y);
        grid[index[0]][index[1]].grain = MaterialManager.empty(x, y, this);
    }
}
