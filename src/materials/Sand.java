package materials;

import java.awt.*;

public class Sand extends Grain {


    public Sand(int x, int y) {
        super(x, y, Color.yellow);
        this.mass = 10;
    }

    public void update() {
        Powder();
    }
}
