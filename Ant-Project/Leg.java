package drawingTool;

import java.awt.Color;
import java.awt.Graphics;

public class Leg {
    private static final Color LEG_COLOR = Color.BLACK;

    private int length;
    private int width;

    public Leg(int length, int width) {
        this.length = length;
        this.width = width;
    }

    public void drawAt(int left, int bottom) {
        Graphics g = Drawing.pen();
        g.setColor(LEG_COLOR);

        int legX = left - width / 2;
        g.fillRect(legX, bottom, width, length);
    }
    
    // Getters for private variables
    public int getLength() { return length; }
    public int getWidth() { return width; }
}