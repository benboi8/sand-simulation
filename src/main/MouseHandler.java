package main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class MouseHandler implements MouseListener {
    public PointerInfo pointer;

    boolean mouseDown = false;

    public int[] getMousePos(World world) {
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

    }

    @Override
    public void mouseReleased(MouseEvent e) {

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
