package main;

import materials.MaterialManager;

import java.awt.*;

public class GuiButton {
    World world;

    Color borderColor = Colors.BUTTON_BORDER;
    Color backgroundColor = Colors.BUTTON_BACKGROUND;
    Color textColor = Colors.BUTTON_TEXT;

    Rectangle rect;

    String text;

    MouseHandler mouseHandler;

    int borderSize = 2;

    String material;

    public GuiButton(Rectangle rect, String text, Color  backgroundColor, String material, MouseHandler mouseHandler) {
        this.rect = rect;
        this.text = text;
        this.backgroundColor = backgroundColor;
        this.material = material;
        this.world = Gui.world;
        this.mouseHandler = mouseHandler;
        Gui.getInstance().buttons.add(this);
    }

    void draw(Graphics2D g2) {
        g2.setColor(backgroundColor);
        g2.drawRect(rect.x, rect.y, rect.width, rect.height);

        g2.setColor(borderColor);
        g2.drawRect(rect.x, rect.y, rect.width, rect.height);

        g2.setColor(textColor);
        g2.drawString(text, rect.x + 4, rect.y + rect.height / 2);
    }

    boolean clicked = false;

    void update() {
        if (mouseHandler.mouseDown) {
            int[] mousePos = mouseHandler.getMousePos();
            if (rect.contains(mousePos[0], mousePos[1])) {
                if (!clicked) {
                    clicked = true;
                    MaterialManager.selectedMaterial = material;
                }
            }
        } else {
            clicked = false;
        }
    }
}
