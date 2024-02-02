package main;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Mouse extends MouseHandler {
    public int radius, size, x, y;

    public World world;

    public String material;

    public KeyListener keyListener;

    public Mouse(World world, KeyListener keyListener) {
        this.radius = Constants.MOUSE_RADIUS;
        this.size = Constants.CELL_SIZE;
        this.world = world;
        this.material = "sand";
        this.keyListener = keyListener;
    }

    public Mouse(int radius, int size, World world, KeyListener keyListener) {
        this.radius = radius;
        this.size = size;
        this.world = world;
        this.material = "sand";
        this.keyListener = keyListener;
    }

    public void update() {
        int[] pos = getMousePos(world);

        x = pos[0];
        y = pos[1];
    }

    public void draw(Graphics2D g2) {

        g2.setColor(Colors.MOUSE_COLOR_MAIN);
        g2.drawOval(x, y, radius, radius);

        g2.setColor(Colors.MOUSE_COLOR_BORDER);
        g2.drawOval(x - 1, y - 1, radius + 2, radius + 2);

        if (mouseDown) {
            addMaterial();
        }
    }

    public void addMaterial() {
        int[] pos = getMousePos(world);
        x = pos[0];
        y = pos[1];

        for (int i = 0; i < radius / size; i++) {
            for (int j = 0; j < radius / size; j++) {
                int[] index = world.GetIndexFromPos(x, y);

                world.addMaterial(material, i + index[0], j + index[1], world);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            mouseDown = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            mouseDown = false;
        }
    }
}
