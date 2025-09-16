package drawingTool;

import java.awt.Color;
import java.awt.Graphics;

public class Mouth {
    private static final Color MOUTH_COLOR = Color.RED;

    private int length;
    private int width;

    public Mouth(int length, int width) {
        this.length = length;
        this.width = width;
    }

    public void drawAt(int left, int bottom) {
        drawAt(left, bottom, true); // Default: smiling
    }

    // Overloaded method
    public void drawAt(int left, int bottom, boolean smile) {
        Graphics g = Drawing.pen();
        g.setColor(MOUTH_COLOR);

        int mouthX = left - width / 2;
        int mouthY = bottom - length / 2;

        if (smile) {
            g.fillArc(mouthX, mouthY, width, length, 0, -180); // smile
        } else {
            g.fillArc(mouthX, mouthY, width, length, 0, 180);  // frown
        }
    }
    
    // Getters for private variables
    public int getLength() { return length; }
    public int getWidth() { return width; }
}