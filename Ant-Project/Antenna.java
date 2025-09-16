package drawingTool;

import java.awt.Color;
import java.awt.Graphics;

public class Antenna {
    private static final Color ANTENNA_COLOR = Color.BLACK;

    private int length;
    private int width;

    public Antenna(int length, int width) {
        this.length = length;
        this.width = width;
    }

    public void drawAt(int left, int bottom) {
        Graphics g = Drawing.pen();
        g.setColor(ANTENNA_COLOR);

        g.drawLine(left, bottom, left - width, bottom - length);
    }
    
    // Getters for private variables
    public int getLength() { return length; }
    public int getWidth() { return width; }
}