package main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Window extends JFrame implements Runnable {

    public Graphics2D g2;

    public World world;

    public Mouse mouse;

    public Gui gui;

    public KeyListener keyListener = new KeyListener();

    public double fps;
    public final int TICKS_PER_SECOND = Constants.TARGET_TICKS;
    public final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;

    public final int MAX_FRAME_SKIP = 5;

    public Point getScreenLocation() {
        return getLocationOnScreen();
    }

    public Window() {
        this.setSize(Constants.WIDTH(), Constants.HEIGHT());
        this.setTitle(Constants.TITLE);
        this.setResizable(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.addKeyListener(keyListener);

        Constants.INSET_LEFT = getInsets().left;
        Constants.INSET_RIGHT = getInsets().right;
        Constants.INSET_TOP = getInsets().top;
        Constants.INSET_BOTTOM = getInsets().bottom;

        g2 = (Graphics2D) this.getGraphics();
        world = new World(new Rectangle(
                100 + (int)Constants.INSET_LEFT,
                100 + (int)Constants.INSET_TOP,
                Constants.WIDTH() - 200 - (int)Constants.INSET_RIGHT - (int)Constants.INSET_LEFT,
                Constants.HEIGHT() - 200 - (int)Constants.INSET_BOTTOM - (int)Constants.INSET_TOP),
                Constants.CELL_SIZE,
                TICKS_PER_SECOND);

        Gui.world = world;
        Gui.window = this;
        gui = Gui.getInstance();

        mouse = new Mouse(world, keyListener);
        addMouseListener(mouse);
    }

    public void update() {
        world.update();
        mouse.update();
    }

    public void draw() {
    // todo use interpolation to predict placements
    // change the grain implementation
    // position = position + (speed * interpolation)
    // public void draw(float interpolation) {
        Image dbImage = createImage(getWidth(), getHeight());
        Graphics dbg = dbImage.getGraphics();
        Graphics2D g = (Graphics2D) dbg;

        g.setColor(Colors.BACKGROUND);
        g.fillRect(0, 0, Constants.WIDTH(), Constants.HEIGHT());

        world.draw(g);
        mouse.draw(g);

        // Hide the mouse cursor
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");

        this.getContentPane().setCursor(blankCursor);

        // show the fps
        g.setColor(Colors.TEXT);
        g.drawString(Math.round(fps) + " fps", 100, 100);

        // draw gui
        gui.draw(g);

        // draw material
        g.drawString(mouse.material.toString(), Constants.WIDTH() / 2, 100);

        // draw frame
        g2.drawImage(dbImage, 0, 0, this);
    }

    /**
     * Implement Constant game speed with Variable FPS
     * <p>
     * This way the game updates at a constant speed but fps doesnt suffer
     * <p>
     * <a href="https://dewitters.com/dewitters-gameloop/">https://dewitters.com/dewitters-gameloop/</a>
     */
    @Override
    public void run() {
        // how many milliseconds has elapsed since epoc time
        double nextGameTick = System.currentTimeMillis();
        int loops;
//        float interpolation;

        while (true) {
            loops = 0;
            while (System.currentTimeMillis() > nextGameTick && loops < MAX_FRAME_SKIP) {
                nextGameTick += SKIP_TICKS;
                loops++;

                update();
            }

//            interpolation = (float) ((System.currentTimeMillis() + SKIP_TICKS - nextGameTick) / SKIP_TICKS);
//            draw(interpolation);
            draw();
        }
    }
}
