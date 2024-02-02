package main;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Button extends MouseHandler {
    World world;

    Color borderColor = Colors.BUTTON_BORDER;
    Color backgroundColor = Colors.BUTTON_BACKGROUND;
    Color textColor = Colors.BUTTON_TEXT;

    Rectangle rect;

    String text;

    int borderSize = 2;

    Button(World world, Rectangle rect, String text) {
        this.rect = rect;
        this.text = text;
        Gui.getInstance().buttons.add(this);
    }

    Button(World world, Rectangle rect, String text, Color textColor, Color borderColor, Color backgroundColor) {
        this.rect = rect;
        this.text = text;
        this.textColor = textColor;
        this.borderColor = borderColor;
        this.backgroundColor = backgroundColor;
        Gui.getInstance().buttons.add(this);
    }

    void draw(Graphics2D g2) {
        g2.setColor(backgroundColor);
        g2.drawRect(rect.x, rect.y, rect.width, rect.height);

        g2.setColor(borderColor);
        g2.drawRect(rect.x, rect.y, rect.width, rect.height);

        g2.setColor(textColor);
        g2.drawString(text, rect.x + rect.width / 2, rect.y + rect.height / 2);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // click button
        if (e.getButton() == MouseEvent.BUTTON1) {
            int[] pos = getMousePos(world);
            if (rect.contains(pos[0], pos[1])) {
                System.out.println("Clicked");
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // start hold
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // release hold
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // change draw style
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // change draw style
    }
}
