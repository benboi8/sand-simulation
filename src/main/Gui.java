package main;

import materials.MaterialProperties;

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

    public void CreateMaterialButtons() {
        for (MaterialProperties materials : MaterialProperties.values()) {
            new GuiButton(world, new Rectangle(10, 100, 90, 50), materials.name());
        }
    }

    public void draw(Graphics2D g2) {
        for (GuiButton button : buttons) {
            button.draw(g2);
        }
    }
}
