package main;

import main.Colors;
import main.World;
import materials.Materials;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Mouse implements MouseListener {
    public int radius, size, x, y;

    public World world;

    public Materials material;

    public PointerInfo pointer;

    public KeyListener keyListener;

    private boolean mouseDown = false;

    public Mouse(World world, KeyListener keyListener) {
        this.radius = Constants.MOUSE_RADIUS;
        this.size = Constants.CELL_SIZE;
        this.world = world;
        this.material = Materials.SAND;
        this.keyListener = keyListener;
    }

    public Mouse(int radius, int size, World world, KeyListener keyListener) {
        this.radius = radius;
        this.size = size;
        this.world = world;
        this.material = Materials.SAND;
        this.keyListener = keyListener;
    }

    private int[] getMousePos() {
        pointer = MouseInfo.getPointerInfo();
        Point point = pointer.getLocation();

        // Keeps the mouse inside the screen
        Point windowPosition = Main.window.getScreenLocation();
        point.translate(-windowPosition.x, -windowPosition.y);

        int[] index = world.GetIndexFromPos((int)point.getX(), (int)point.getY());

        return world.GetPosFromIndex(index[0], index[1]);
    }

    public void update() {
        int[] pos = getMousePos();

        x = pos[0];
        y = pos[1];

        // todo replace with maybe a switch case or a hash table maybe
        if (keyListener.isKeyPressed(KeyEvent.VK_1)) {
            material = Materials.SAND;
        }
        else if (keyListener.isKeyPressed(KeyEvent.VK_2)) {
            material = Materials.WATER;
        }
        else if (keyListener.isKeyPressed(KeyEvent.VK_3)) {
            material = Materials.WOOD;
        } else if (keyListener.isKeyPressed(KeyEvent.VK_4)) {
            material = Materials.FIRE;
        } else if (keyListener.isKeyPressed(KeyEvent.VK_5)) {
            material = Materials.SMOKE;
        } else if (keyListener.isKeyPressed(KeyEvent.VK_6)) {
            material = Materials.ASH;
        }else if (keyListener.isKeyPressed(KeyEvent.VK_7)) {
            material = Materials.OIL;
        }else if (keyListener.isKeyPressed(KeyEvent.VK_8)) {
            material = Materials.STEAM;
        }else if (keyListener.isKeyPressed(KeyEvent.VK_9)) {
            material = Materials.ACID;
        }else if (keyListener.isKeyPressed(KeyEvent.VK_Q)) {
            material = Materials.WAX;
        }else if (keyListener.isKeyPressed(KeyEvent.VK_W)) {
            material = Materials.BARRIER;
        }

        else if (keyListener.isKeyPressed(KeyEvent.VK_0)) {
            material = Materials.ERASE;
        }
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
        int[] pos = getMousePos();
        x = pos[0];
        y = pos[1];

        for (int i = 0; i < radius / size; i++) {
            for (int j = 0; j < radius / size; j++) {
                int[] index = world.GetIndexFromPos(x, y);

                world.addMaterial(material, i + index[0], j + index[1]);
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

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
