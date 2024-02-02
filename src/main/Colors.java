package main;

import java.awt.*;

public class Colors {
    public static Color FromRGBString(String color) {
        int r, g, b;

        String[] colors = color.split(",");

        r = Integer.parseInt(colors[0].strip());
        g = Integer.parseInt(colors[1].strip());
        b = Integer.parseInt(colors[2].strip());


        return new Color(r, g, b);
    }

    public static final Color BACKGROUND = Color.black;
    public static final Color TEXT = Color.white;
    public static final Color BORDER = Color.white;
    public static final Color MOUSE_COLOR_MAIN = Color.white;
    public static final Color MOUSE_COLOR_BORDER = Color.black;

    public static final Color BUTTON_BORDER = Color.white;
    public static final Color BUTTON_BACKGROUND = Color.gray;
    public static final Color BUTTON_TEXT = Color.white;

}
