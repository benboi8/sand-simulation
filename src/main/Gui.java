package main;

import materials.MaterialProperties;

import java.awt.*;
import java.util.ArrayList;

public class Gui {
    private static final Gui instance = new Gui();

    public static World world;

    private Gui() {}

    public static Gui getInstance() {
        return instance;
    }

    public ArrayList<Button> buttons = new ArrayList<Button>();

    public void CreateMaterialButtons() {
        for (MaterialProperties materials : MaterialProperties.values()) {
            new Button(world, new Rectangle(10, 100, 90, 50), materials.name());
        }
    }

    public void draw(Graphics2D g2) {
        for (Button button : buttons) {
            button.draw(g2);
        }
    }
}
