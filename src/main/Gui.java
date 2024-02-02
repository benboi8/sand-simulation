package main;

import java.awt.*;
import java.util.ArrayList;

public class Gui {
    private static final Gui instance = new Gui();

    public static World world;
    public static Window window;

    private Gui() {}

    public static Gui getInstance() {
        return instance;
    }

    public ArrayList<GuiButton> buttons = new ArrayList<GuiButton>();

    public void draw(Graphics2D g2) {
        for (GuiButton button : buttons) {
            button.draw(g2);
        }
    }

    public void update() {
        for (GuiButton button : buttons) {
            button.update();
        }
    }
}
