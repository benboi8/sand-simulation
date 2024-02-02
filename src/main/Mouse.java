package main;

import materials.MaterialManager;

import java.awt.*;

public class Mouse {
    public int radius, size, x, y;

    public World world;

    public KeyListener keyListener;
    public MouseHandler mouseHandler;

    public Mouse(World world, KeyListener keyListener, MouseHandler mouseHandler) {
        this.radius = Constants.MOUSE_RADIUS;
        this.size = Constants.CELL_SIZE;
        this.world = world;
        this.keyListener = keyListener;
        this.mouseHandler = mouseHandler;
    }

    public Mouse(int radius, int size, World world, KeyListener keyListener) {
        this.radius = radius;
        this.size = size;
        this.world = world;
        this.keyListener = keyListener;
    }

    public void update() {
        int[] pos = mouseHandler.getMousePos();

        x = pos[0];
        y = pos[1];
    }

    public void draw(Graphics2D g2) {

        g2.setColor(Colors.MOUSE_COLOR_MAIN);
        g2.drawOval(x, y, radius, radius);

        g2.setColor(Colors.MOUSE_COLOR_BORDER);
        g2.drawOval(x - 1, y - 1, radius + 2, radius + 2);

        if (mouseHandler.mouseDown) {
            addMaterial();
        }
    }

    public void addMaterial() {
        int[] pos = mouseHandler.getMousePos();
        x = pos[0];
        y = pos[1];

        for (int i = 0; i < radius / size; i++) {
            for (int j = 0; j < radius / size; j++) {
                int[] index = world.GetIndexFromPos(x, y);

                world.addMaterial(MaterialManager.selectedMaterial, i + index[0], j + index[1], world);
            }
        }
    }
}
