package materials;

import main.Constants;
import main.World;
import org.json.JSONArray;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class Grain {

    public int x, y;
    public String name;
    ArrayList<Object> movement = new ArrayList<Object>();
    public int mass = 0;
    public Color color;

    public boolean permanent = false;
    public boolean empty = false;
    public int fireStrength = 0;
    public void setFireStrength(int fireStrength) {
        this.fireStrength = Math.max(fireStrength, 0);
    }

    public int fireResistance = 0;
    public void setFireResistance(int fireResistance) {
        this.fireResistance = fireResistance;
        if (this.fireResistance != 0) {
            ignitable = true;
        }
    }
    public int corrosionStrength = 0;
    public void setCorrosionStrength(int corrosionStrength) {
        this.corrosionStrength = Math.max(corrosionStrength, 0);
    }
    public int corrosionResistance = 0;
    public void setCorrosionResistance(int corrosionResistance) {
        this.corrosionResistance = Math.max(corrosionResistance, 0);
        if (this.corrosionResistance != 0) {
            corrodible = true;
        }
    }
    public boolean ignitable = false;
    public boolean ignited = false;
    public boolean corrodible = false;
    public World world;
    public int timeAlive = 0;
    public double lifeSpan = -1;

    public boolean isMovingLeft = false;
    public JSONArray ignitionProduct;

    public JSONArray corrosionProduct;

    public Grain(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;

        if (Math.round(Math.random()) == 0) {
            isMovingLeft = true;
        }
    }

    public Grain(int x, int y, World world) {
        this.x = x;
        this.y = y;
        this.world = world;
        this.color = Color.black;
        this.materialType = MaterialManager.empty;

        if (Math.round(Math.random()) == 0) {
            isMovingLeft = true;
        }
    }

    public static Grain Empty(int x, int y, World world) {
        Grain g = new Grain(x, y, world);
        g.name = MaterialManager.empty;
        g.materialType = MaterialManager.empty;
        return g;
    }

    public void draw(Graphics2D g2) {
        if (!Objects.equals(materialType, MaterialManager.empty)) {
            g2.setColor(color);
            g2.fillRect(x, y, Constants.CELL_SIZE, Constants.CELL_SIZE);
        }
    }

    public void update() {
        for (Object m : movement) {
            String move = m.toString();
            if (move.equals(MaterialMovement.powder.name())) {
                Powder();
            }
            if (move.equals(MaterialMovement.liquid.name())) {
                Liquid();
            }
            if (move.equals(MaterialMovement.gas.name())) {
                Gas();
            }
            if (move.equals(MaterialMovement.random.name())) {
                RandomMove();
            }
        }

        if (fireResistance > 0) {
            Burn();
        }
    }

    public String materialType;

    public String GetMaterialType() {
        return materialType;
    }

    public boolean HasProperty(MaterialProperties property) {
        if (property == MaterialProperties.fire_resistance) {
            return fireResistance > 0;
        }
        else if (property == MaterialProperties.fire_strength) {
            return fireStrength > 0;
        }

        return false;
    }

    public void CheckMassPriority() {
        if (!permanent) {
            int[] index1 = world.GetIndexFromPos(x, y);
            Grain currentGrain = world.grid[index1[0]][index1[1]].grain;

            if (!Objects.equals(currentGrain.materialType, MaterialManager.empty) && world.CheckIfPosInBounds(x, y)) {
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

    public ArrayList<Grain> CheckAllForType(String targetMaterialType) {
        int[] index = world.GetIndexFromPos(x, y);

        ArrayList<Grain> objects = new ArrayList<>();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (world.CheckIfIndexInBounds(index[0] + i, index[1] + j)) {
                    if (Objects.equals(world.grid[index[0] + i][index[1] + j].grain.GetMaterialType(), MaterialManager.empty)){
                        if (Objects.equals(world.grid[index[0] + i][index[1] + j].grain.GetMaterialType(), targetMaterialType)) {
                            objects.add(world.grid[index[0] + i][index[1] + j].grain);
                        }
                    }
                }
            }
        }

        return objects;
    }

    public ArrayList<Grain> CheckAllForProperties(MaterialProperties targetProperty) {
        int[] index = world.GetIndexFromPos(x, y);

        ArrayList<Grain> objects = new ArrayList<>();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (world.CheckIfIndexInBounds(index[0] + i, index[1] + j)) {
                    if (!Objects.equals(world.grid[index[0] + i][index[1] + j].grain.GetMaterialType(), MaterialManager.empty)){
                        if (world.grid[index[0] + i][index[1] + j].grain.HasProperty(targetProperty)) {
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

    public void AddTime(JSONArray deathMaterials) {
        timeAlive += 1;

        if (lifeSpan != -1) {
            if (timeAlive >= lifeSpan) {
                Kill();
                int[] index = world.GetIndexFromPos(x, y);
                for (Object deathMat: deathMaterials) {
                    world.addMaterial(deathMat.toString(), index[0], index[1], world);
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
            if (!CheckAllForProperties(MaterialProperties.fire_strength).isEmpty()) {
                Ignite();
                return;
            }
            for (String burnable : world.burnables){
                ArrayList<Grain> burnableGrains = CheckAllForType(burnable);
                for (Grain grain : burnableGrains) {
                    if (grain.ignited) {
                        Ignite();
                        break;
                    }
                }
            }

        } else {
            AddTime(ignitionProduct);
        }
    }

    public void Corrode() {
        ArrayList<materials.Grain> surroundings = world.GetAll(x, y);
        for (materials.Grain grain : surroundings) {
            if (!grainInList(grain, world.nonCorrodibles)) {
                grain.lifeSpan = 3 * Constants.TARGET_TICKS;
                grain.AddTime(corrosionProduct);
            }
        }
    }

    public boolean grainInList(Grain grain, ArrayList<String> materialList) {
        return materialList.contains(grain.materialType);
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

    public void RandomMove() {
        int movement = (int) (Math.random() * 8);

        switch (movement) {
            case 0 -> {
                if (CheckDown()) {
                    world.MoveGrain(this, new int[]{0, 1});
                }
            }
            case 1 -> {
                if (CheckLeftDown()) {
                    world.MoveGrain(this, new int[]{-1, 1});
                }
            }
            case 2 -> {
                if (CheckRightDown()) {
                    world.MoveGrain(this, new int[]{1, 1});
                }
            }
            case 3 -> {
                if (CheckLeft()) {
                    world.MoveGrain(this, new int[]{-1, 0});
                }
            }
            case 4 -> {
                if (CheckRight()) {
                    world.MoveGrain(this, new int[]{1, 0});
                }
            }
            case 5 -> {
                if (CheckUp()) {
                    world.MoveGrain(this, new int[]{0, -1});
                }
            }
            case 6 -> {
                if (CheckLeftUp()) {
                    world.MoveGrain(this, new int[]{-1, -1});
                }
            }
            case 7 -> {
                if (CheckRightUp()) {
                    world.MoveGrain(this, new int[]{1, -1});
                }
            }
        }
    }

    public boolean CompareMass(int[] index1, int[] index2) {
        if (world.CheckIfIndexInBounds(index1[0], index1[1])) {
            if (world.CheckIfIndexInBounds(index2[0], index2[1])) {
                Grain current_grain = world.grid[index1[0]][index1[1]].grain;
                Grain second_grain = world.grid[index2[0]][index2[1]].grain;

                if (!Objects.equals(current_grain.materialType, MaterialManager.empty) && !Objects.equals(second_grain.materialType, MaterialManager.empty)) {
                    if (!Objects.equals(current_grain.materialType, second_grain.materialType)) {
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
