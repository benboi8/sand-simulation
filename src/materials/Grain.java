package materials;

import main.Constants;
import main.World;

import java.awt.*;
import java.util.ArrayList;

public abstract class Grain {

    public int x, y;
    public int mass = 0;
    public boolean permanent = false;
    public Color color;
    public boolean ignitable = false;
    public boolean ignited = false;
    public World world;
    public int timeAlive = 0;
    public int lifeSpan = -1;

    public boolean isMovingLeft = false;
    public Materials ignitionProduct;

    public Grain(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;

        if (Math.round(Math.random()) == 0) {
            isMovingLeft = true;
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(color);
        g2.fillRect(x, y, Constants.CELL_SIZE, Constants.CELL_SIZE);
    }

    public void update() {

    }

    public void CheckMassPriority() {
        if (!permanent) {
            int[] index1 = world.GetIndexFromPos(x, y);
            Grain currentGrain = world.grid[index1[0]][index1[1]].grain;

            if (currentGrain != null && world.CheckIfPosInBounds(x, y)) {
                // Check down
                if (world.CheckIfPosInBounds(x, y + Constants.CELL_SIZE)) {
                    int[] index2 = world.GetIndexFromPos(x, y + Constants.CELL_SIZE);

                    if (CompareMass(index1, index2)) {
                        world.DisplaceGrain(this, world.grid[index2[0]][index2[1]].grain);
                    }
                }
            }
        }
    }

    public boolean CheckDown() {
        return world.CheckIfPosCanMove(x, y + Constants.CELL_SIZE);
    }
    public boolean CheckLeftDown() {
        return world.CheckIfPosCanMove(x - Constants.CELL_SIZE, y + Constants.CELL_SIZE);
    }
    public boolean CheckRightDown() {
        return world.CheckIfPosCanMove(x + Constants.CELL_SIZE, y + Constants.CELL_SIZE);
    }
    public boolean CheckLeft() {
        return world.CheckIfPosCanMove(x - Constants.CELL_SIZE, y);
    }
    public boolean CheckRight() {
        return world.CheckIfPosCanMove(x + Constants.CELL_SIZE, y);
    }
    public boolean CheckUp() {
        return world.CheckIfPosCanMove(x, y - Constants.CELL_SIZE);
    }
    public boolean CheckLeftUp() {
        return world.CheckIfPosCanMove(x - Constants.CELL_SIZE, y - Constants.CELL_SIZE);
    }
    public boolean CheckRightUp() {
        return world.CheckIfPosCanMove(x + Constants.CELL_SIZE, y - Constants.CELL_SIZE);
    }

    public ArrayList<Grain> CheckAllForType(Grain type) {
        int[] index = world.GetIndexFromPos(x, y);

        ArrayList<Grain> objects = new ArrayList<>();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (world.CheckIfIndexInBounds(index[0] + i, index[1] + j)) {
                    if (world.grid[index[0] + i][index[1] + j].grain != null){
                        if (world.grid[index[0] + i][index[1] + j].grain.getClass() == type.getClass()) {
                            objects.add(world.grid[index[0] + i][index[1] + j].grain);
                        }
                    }
                }
            }
        }

        return objects;
    }

    public void Ignite() {
        if (ignitable) {
            ignited = true;
        }
    }

    public void AddTime(Materials[] deathMats) {
        timeAlive += 1;

        if (lifeSpan != -1) {
            if (timeAlive >= lifeSpan) {
                Kill();
                int[] index = world.GetIndexFromPos(x, y);
                for (Materials deathMat: deathMats) {
                    world.addMaterial(deathMat, index[0], index[1]);
                    world.grid[index[0]][index[1]].grain.update();
                }
            }
        }
    }

    public void Kill() {
        world.Erase(x, y);
    }

    public void Burn() {
        if (!ignited) {
            if (!CheckAllForType(new Fire(0, 0)).isEmpty()) {
                Ignite();
                return;
            }
            for (materials.Grain burnable : world.burnables){
                ArrayList<materials.Grain> types = CheckAllForType(burnable);
                for (materials.Grain grain : types) {
                    if (grain.ignited) {
                        Ignite();
                        break;
                    }
                }
            }

        }else {
            AddTime(ignitionProduct);
        }
    }

    public void Corrode() {
        ArrayList<materials.Grain> surroundings = world.GetAll(x, y);
        for (materials.Grain grain : surroundings) {
            if (!In(grain, world.nonCorrodibles)) {
                grain.lifeSpan = 3 * Constants.TARGET_TICKS;
                grain.AddTime(Materials {});
            }
        }
    }

    public boolean In(Grain grain, Grain[] list) {
        for (Grain g : list) {
            if (g.getClass() == grain.getClass()) {
                return true;
            }
        }
        return false;
    }

    public void Powder() {
        if (CheckDown()) {
            world.MoveGrain(this, new int[]{0, 1});
        }
        else {
            if (CheckLeftDown() && (CheckLeft() || CheckDown())) {
                world.MoveGrain(this, new int[]{-1, 1});
            }
            else if (CheckRightDown() && (CheckRight() || CheckDown())) {
                world.MoveGrain(this, new int[]{1, 1});
            }

//            // Check down
//            else {
//                int[] index1 = world.GetIndexFromPos(x, y);
//                int[] index2 = world.GetIndexFromPos(x, y + main.Constants.CELL_SIZE);
//                if (CompareMass(index1, index2)) {
//                    world.DisplaceGrain(this, world.grid[index2[0]][index2[1]].grain);
//                }
//                // Check down and left
//                else {
//                    index2 = world.GetIndexFromPos(x - main.Constants.CELL_SIZE, y + main.Constants.CELL_SIZE);
//                    if (CompareMass(index1, index2)) {
////                        world.DisplaceGrain(world.grid[index1[0]][index1[1]].grain, world.grid[index2[0]][index2[1]].grain);
//                    }
//                    // Check down and right
//                    else {
//                        index2 = world.GetIndexFromPos(x + main.Constants.CELL_SIZE, y + main.Constants.CELL_SIZE);
//
//                        if (CompareMass(index1, index2)) {
////                            world.DisplaceGrain(this, world.grid[index2[0]][index2[1]].grain);
//                        }
//                    }
//                }
//            }
        }
    }

    public void Liquid() {
        if (CheckDown()) {
            world.MoveGrain(this, new int[]{0, 1});
        }
        else {
            if (CheckLeftDown() || CheckRightDown()) {
                if (CheckLeftDown() && (CheckLeft() || CheckDown())) {
                    world.MoveGrain(this, new int[]{-1, 1});
                }
                else if (CheckRightDown() && (CheckRight() || CheckDown())) {
                    world.MoveGrain(this, new int[]{1, 1});
                }
            } else {
                if (isMovingLeft) {
                    if (CheckLeft()) {
                        world.MoveGrain(this, new int[]{-1, 0});
                    } else {
                        isMovingLeft = false;
                    }
                } else {
                    if (CheckRight()) {
                        world.MoveGrain(this, new int[]{1, 0});
                    } else {
                        isMovingLeft = true;
                    }
                }
            }
        }
    }

    public void Gas() {
        if (CheckUp()) {
            world.MoveGrain(this, new int[]{0, -1});
        }
        else {
            if (CheckLeftUp() || CheckRightUp()) {
                if (CheckLeftUp() && (CheckLeft() || CheckDown())) {
                    world.MoveGrain(this, new int[]{-1, -1});
                }
                else if (CheckRightUp() && (CheckRight() || CheckDown())) {
                    world.MoveGrain(this, new int[]{1, -1});
                }
            }
            else {
                if (isMovingLeft) {
                    if (CheckLeft()) {
                        world.MoveGrain(this, new int[]{-1, 0});
                    }
                    else {
                        isMovingLeft = false;
                    }
                }
                else {
                    if (CheckRight()) {
                        world.MoveGrain(this, new int[]{1, 0});
                    }
                    else {
                        isMovingLeft = true;
                    }
                }
            }
        }
    }

    public boolean CompareMass(int[] index1, int[] index2) {
        if (world.CheckIfIndexInBounds(index1[0], index1[1])) {
            if (world.CheckIfIndexInBounds(index2[0], index2[1])) {
                Grain current_grain = world.grid[index1[0]][index1[1]].grain;
                Grain second_grain = world.grid[index2[0]][index2[1]].grain;

                if (current_grain != null && second_grain != null) {
                    if (current_grain.getClass() != second_grain.getClass()) {
                        if (!current_grain.permanent && !second_grain.permanent) {
                            if (current_grain.mass != 0 && second_grain.mass != 0) {
                                return current_grain.mass > second_grain.mass;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }
}
