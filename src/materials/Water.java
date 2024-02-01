package materials;

import main.Constants;

import java.awt.*;

public class Water extends Grain {


    public Water(int x, int y) {
        super(x, y, Color.blue);
        this.mass = 6;
        this.ignitable = true;
        this.ignitionProduct = Materials.STEAM;
        this.lifeSpan = 1 / Constants.TARGET_TICKS;
    }

    public void update(double dt) {
        Liquid();
        Burn();
    }
}