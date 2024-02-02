package main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
    public MouseHandler(World world) {
        this.world = world;
    }

    public World world;

    public PointerInfo pointer;

    boolean mouseDown = false;

    public int[] getMousePos() {
        pointer = MouseInfo.getPointerInfo();
        Point point = pointer.getLocation();

        // Keeps the mouse inside the screen
        Point windowPosition = Main.window.getScreenLocation();
        point.translate(-windowPosition.x, -windowPosition.y);

        int[] index = world.GetIndexFromPos((int)point.getX(), (int)point.getY());

        return world.GetPosFromIndex(index[0], index[1]);
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
