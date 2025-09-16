package drawingTool;

import java.awt.Color;
import java.awt.Graphics;

public class Neck {
    
    private Color neckColor;
    private int length;
    private int width;

    public Neck(int length, int width) {
        this(length, width, Color.DARK_GRAY);
    }
    
    public Neck(int length, int width, Color neckColor) {
        this.length = length;
        this.width  = width;
        this.neckColor = neckColor;
    }

    public void drawAt(int left, int bottom) {
        Graphics g = Drawing.pen();
        g.setColor(neckColor);

        int neckX = left - width / 2;
        int neckY = bottom - length;
        g.fillRect(neckX, neckY, width, length);
    }
    
    // Getters for private variables (if needed)
    public int getLength() { return length; }
    public int getWidth() { return width; }
    public Color getNeckColor() { return neckColor; }
}