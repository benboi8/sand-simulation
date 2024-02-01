package main;

import java.awt.*;

public class Constants {
    // todo get width and height dynamically
    public static int WIDTH() {
        return 1920;
    }
    public static int HEIGHT() {
        return 1080;
    }
    public static final String TITLE = "Sand Simulator";
    public static double INSET_LEFT;
    public static double INSET_TOP;
    public static double INSET_RIGHT;
    public static double INSET_BOTTOM;

    public static final int CELL_SIZE = 5;
    public static final int MOUSE_RADIUS = 30;
    public static final int TARGET_TICKS = 144;

    public static int Lerp(int v0, int v1, double t) {
        return (int) (v0 + t * (v1 - v0));
    }
}
