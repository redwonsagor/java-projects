package drawingTool;

import java.awt.Color;
import java.awt.Graphics;

public class Stinger {
    private static final Color STINGER_COLOR = Color.BLACK;

    private int length;
    private int width;

    public Stinger(int length, int width) {
        this.length = length;
        this.width = width;
    }

    public void drawAt(Graphics g, int left, int bottom) {
        g.setColor(STINGER_COLOR);

        int stingerTop = bottom + length;
        int x1 = left - width / 2;
        int x2 = left + width / 2;

        int[] x = { x1, x2, left };
        int[] y = { bottom, bottom, stingerTop };

        g.fillPolygon(x, y, 3);
    }
    
    // Getters for private variables
    public int getLength() { return length; }
    public int getWidth() { return width; }
}