package main;

import java.awt.*;

public class Constants {
    private static final int[][] resolutions =  new int[][] {
        {640, 360},
        {854, 480},
        {960, 540},
        {1024, 576},
        {1280, 720},
        {1366, 768},
        {1600, 900},
        {1920, 1080},
        {2560, 1440},
        {3200, 1800},
        {3840, 2160},
        {5120, 2880},
        {7680, 4320}
    };

    public static int selectedResolution = 7;

    public static int WIDTH() {
        return resolutions[selectedResolution][0];
    }
    public static int HEIGHT() {
        return resolutions[selectedResolution][1];
    }
    public static final String TITLE = "Sand Simulator";
    public static double INSET_LEFT;
    public static double INSET_TOP;
    public static double INSET_RIGHT;
    public static double INSET_BOTTOM;

    public static final int CELL_SIZE = 5;
    public static final int MOUSE_RADIUS = 30;
    public static final int TARGET_TICKS = 60;

    public static int Lerp(int v0, int v1, double t) {
        return (int) (v0 + t * (v1 - v0));
    }
}
